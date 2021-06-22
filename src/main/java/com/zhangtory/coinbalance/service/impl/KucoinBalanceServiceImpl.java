package com.zhangtory.coinbalance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kucoin.sdk.KucoinRestClient;
import com.kucoin.sdk.exception.KucoinApiException;
import com.kucoin.sdk.rest.response.AccountBalancesResponse;
import com.kucoin.sdk.rest.response.SubAccountBalanceResponse;
import com.zhangtory.coinbalance.model.entity.LockBalance;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.service.GetBalanceService;
import com.zhangtory.coinbalance.service.LockBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Scott on 2021/6/4
 **/
@Service
public class KucoinBalanceServiceImpl implements GetBalanceService {

    private static final String EXCHANGE_CODE = "kucoin";

    @Autowired
    private KucoinRestClient restClient;

    @Autowired
    private LockBalanceService lockBalanceService;

    @Override
    public List<Record> getBalance() throws IOException {
        try {
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
        } catch (KucoinApiException e) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }
            return getBalance();
        }
    }

    /**
     * 获取主账号持仓
     */
    private Map<String, Record> getAccountBalance() throws IOException {
        List<AccountBalancesResponse> accountBalancesResponses = restClient.accountAPI().listAccounts(null, null);
        Map<String, Record> map = new HashMap<>();
        accountBalancesResponses.forEach(account -> {
            addToMap(map, account);
        });
        return map;
    }

    /**
     * 获取子账号持仓
     * @throws IOException
     */
    private void getSubAccountBalance(Map<String, Record> map) throws IOException {
        List<SubAccountBalanceResponse> subAccountBalanceResponses = restClient.accountAPI().listSubAccounts();
        subAccountBalanceResponses.forEach(account -> {
            List<AccountBalancesResponse> tradeAccounts = account.getTradeAccounts();
            tradeAccounts.forEach(subAccount -> {
                addToMap(map, subAccount);
            });

            List<AccountBalancesResponse> mainAccounts = account.getMainAccounts();
            mainAccounts.forEach(subAccount -> {
                addToMap(map, subAccount);
            });
        });
    }

    /**
     * 构造map的key
     * @param account
     * @return
     */
    private String buildMapKey(AccountBalancesResponse account) {
        return new StringBuilder(account.getCurrency())
                .append("-")
                .append(account.getType() == null ? "sub" : account.getType())
                .toString();
    }

    private String buildMapKey(String type, String currency) {
        return new StringBuilder(type)
                .append("-").append(currency).toString();
    }

    /**
     * 返回信息构造为record
     * @param account
     * @return
     */
    private Record accountBalanceToRecord(AccountBalancesResponse account) {
        return Record.builder()
                .exchange(EXCHANGE_CODE)
                .account(account.getType() == null ? "sub" : account.getType())
                .currency(account.getCurrency())
                .amount(account.getBalance())
                .usd(account.getBalance().multiply(getQuotation(account.getCurrency())))
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

    /**
     * 将record正确的放入map
     * @param map
     * @param account
     */
    private void addToMap(Map<String, Record> map, AccountBalancesResponse account) {
        if (!account.getBalance().equals(BigDecimal.ZERO)) {
            String key = buildMapKey(account);
            Record record = map.get(key);
            if (record == null) {
                map.put(key, accountBalanceToRecord(account));
            } else {
                record.setAmount(record.getAmount().add(account.getBalance()));
                record.setUsd(record.getAmount().multiply(getQuotation(account.getCurrency())));
            }
        }
    }

    /**
     * 获取行情价格
     * @param currency
     * @return
     */
    private BigDecimal getQuotation(String currency) {
        if ("usdt".equalsIgnoreCase(currency)
                || "husd".equalsIgnoreCase(currency)
                || "busd".equalsIgnoreCase(currency)) {
            return BigDecimal.ONE;
        }
        try {
            return restClient.currencyAPI().getFiatPrice("USD", currency.toUpperCase())
                    .getOrDefault(currency.toUpperCase(), BigDecimal.ZERO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

}
