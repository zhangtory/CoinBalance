package com.zhangtory.coinbalance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huobi.client.AccountClient;
import com.huobi.client.MarketClient;
import com.huobi.client.SubUserClient;
import com.huobi.client.req.account.AccountBalanceRequest;
import com.huobi.client.req.market.MarketTradeRequest;
import com.huobi.exception.SDKException;
import com.huobi.model.account.AccountBalance;
import com.huobi.model.account.Balance;
import com.huobi.model.account.SubuserAggregateBalance;
import com.huobi.model.market.MarketTrade;
import com.zhangtory.coinbalance.model.entity.LockBalance;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.service.GetBalanceService;
import com.zhangtory.coinbalance.service.LockBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Created by Scott on 2021/6/4
 **/
@Service
public class HuobiBalanceServiceImpl implements GetBalanceService {

    private static final String EXCHANGE_CODE = "huobi";

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private SubUserClient subUserClient;

    @Autowired
    private MarketClient marketClient;

    @Autowired
    private LockBalanceService lockBalanceService;

    @Override
    public List<Record> getBalance() throws IOException {
        Map<String, Record> map = getAccountBalance();
        // load lock balance
        lockBalanceService.list(new QueryWrapper<LockBalance>().lambda()
                .eq(LockBalance::getExchange, EXCHANGE_CODE))
                .forEach(lockBalance -> {
                    String key = buildMapKey(lockBalance.getAccount(), lockBalance.getCurrency());
                    map.put(key, balanceToRecord(lockBalance));
                });
        // request balance
        getSubAccountBalance(map);
        return new ArrayList<>(map.values());
    }

    /**
     * 获取主账户余额
     * @return
     */
    private Map<String, Record> getAccountBalance() {
        Map<String, Record> map = new HashMap<>();
        accountClient.getAccounts().forEach(account -> {
            AccountBalance accountBalance = accountClient.getAccountBalance(AccountBalanceRequest.builder()
                    .accountId(account.getId()).build());
            accountBalance.getList().forEach(balance -> {
                if (!balance.getBalance().equals(BigDecimal.ZERO)) {
                    String key = buildMapKey(accountBalance.getType(), balance.getCurrency());
                    Record record = map.get(key);
                    if (record == null) {
                        map.put(key, balanceToRecord(accountBalance.getType(), balance));
                    } else {
                        record.setAmount(record.getAmount().add(balance.getBalance()));
                        record.setUsd(record.getAmount().multiply(getQuotation(balance.getCurrency())));
                    }
                }
            });
        });
        return map;
    }

    /**
     * 获取子账户余额
     * @param map
     */
    private void getSubAccountBalance(Map<String, Record> map) {
        List<SubuserAggregateBalance> balanceList = subUserClient.getSubuserAggregateBalance();
        balanceList.forEach(balance -> {
            if (!balance.getBalance().equals(BigDecimal.ZERO)) {
                String key = buildMapKey(balance.getType(), balance.getCurrency());
                Record record = map.get(key);
                if (record == null) {
                    map.put(key, balanceToRecord(balance.getType(), balance));
                } else {
                    record.setAmount(record.getAmount().add(balance.getBalance()));
                    record.setUsd(record.getAmount().multiply(getQuotation(balance.getCurrency())));
                }
            }
        });
    }

    private String buildMapKey(String type, String currency) {
        return new StringBuilder(type)
                .append("-").append(currency).toString();
    }

    private Record balanceToRecord(String accountType, Balance balance) {
        return Record.builder()
                .exchange(EXCHANGE_CODE)
                .account(accountType)
                .currency(balance.getCurrency())
                .amount(balance.getBalance())
                .usd(balance.getBalance().multiply(getQuotation(balance.getCurrency())))
                .createTime(LocalDateTime.now().withMinute(0).withSecond(0).withNano(0))
                .build();
    }

    private Record balanceToRecord(String accountType, SubuserAggregateBalance balance) {
        return Record.builder()
                .exchange(EXCHANGE_CODE)
                .account(accountType)
                .currency(balance.getCurrency())
                .amount(balance.getBalance())
                .usd(balance.getBalance().multiply(getQuotation(balance.getCurrency())))
                .createTime(LocalDateTime.now().withMinute(0).withSecond(0).withNano(0))
                .build();
    }

    private Record balanceToRecord(LockBalance lockBalance) {
        return Record.builder()
                .exchange(EXCHANGE_CODE)
                .account(lockBalance.getAccount())
                .currency(lockBalance.getCurrency())
                .amount(lockBalance.getAmount())
                .usd(lockBalance.getAmount().multiply(getQuotation(lockBalance.getCurrency())))
                .createTime(LocalDateTime.now().withMinute(0).withSecond(0).withNano(0))
                .build();
    }

    private BigDecimal getQuotation(String currency) {
        if ("usdt".equalsIgnoreCase(currency)
                || "husd".equalsIgnoreCase(currency)
                || "busd".equalsIgnoreCase(currency)) {
            return BigDecimal.ONE;
        }
        try {
            List<MarketTrade> marketTrade = marketClient.getMarketTrade(
                    MarketTradeRequest.builder().symbol(currency.toLowerCase() + "usdt").build());
            if (CollectionUtils.isEmpty(marketTrade)) {
                return BigDecimal.ZERO;
            }
            return marketTrade.get(0).getPrice();
        } catch (SDKException e) {
            return BigDecimal.ZERO;
        }
    }

}
