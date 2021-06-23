package com.zhangtory.coinbalance.service.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.market.TickerPrice;
import com.binance.api.client.exception.BinanceApiException;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.service.GetBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott on 2021/6/23
 **/
@Service
public class BinanceBalanceServiceImpl implements GetBalanceService  {

    private static final String EXCHANGE_CODE = "binance";

    @Autowired
    private BinanceApiRestClient restClient;

    @Override
    public List<Record> getBalance() throws IOException {
        List<Record> list = new ArrayList<>();
        List<AssetBalance> balances = restClient.getAccount().getBalances();
        balances.forEach(balance -> {
            BigDecimal amount = new BigDecimal(balance.getFree()).add(new BigDecimal(balance.getLocked()));
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                list.add(balanceToRecord(balance));
            }
        });
        return list;
    }

    private Record balanceToRecord(AssetBalance balance) {
        BigDecimal amount = new BigDecimal(balance.getFree()).add(new BigDecimal(balance.getLocked()));
        return Record.builder()
                .exchange(EXCHANGE_CODE)
                .account("spot")
                .currency(balance.getAsset())
                .amount(amount)
                .usd(amount.multiply(getQuotation(balance.getAsset())))
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
            TickerPrice price = restClient.getPrice(currency.toUpperCase() + "USDT");
            if (price == null) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(price.getPrice());
        } catch (BinanceApiException e) {
            return BigDecimal.ZERO;
        }
    }

}
