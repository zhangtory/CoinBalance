package com.zhangtory.coinbalance.service.impl;

import com.zhangtory.coinbalance.controller.Request.BalanceHistoryRequest;
import com.zhangtory.coinbalance.controller.response.ChartResponse;
import com.zhangtory.coinbalance.model.vo.AccountBalanceVO;
import com.zhangtory.coinbalance.model.vo.DetailsVo;
import com.zhangtory.coinbalance.model.vo.HoldRateVo;
import com.zhangtory.coinbalance.service.ChartService;
import com.zhangtory.coinbalance.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Scott on 2021/6/21
 **/
@Service
@Slf4j
public class ChartServiceImpl implements ChartService {

    @Autowired
    private RecordService recordService;

    @Override
    public ChartResponse sumChart() {
        ChartResponse response = new ChartResponse();
        List<List<AccountBalanceVO>> balanceHistory = recordService.getBalanceHistory(new BalanceHistoryRequest());
        List<String> timeList = new ArrayList<>();
        List<String> amountList = new ArrayList<>();
        Map<String, DetailsVo> detailMap = new HashMap();
        balanceHistory.forEach(balanceList -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (AccountBalanceVO accountBalanceVO : balanceList) {
                sum = sum.add(accountBalanceVO.getUsd());
            }
            if (!balanceList.isEmpty()) {
                amountList.add(sum.toPlainString());
                timeList.add(balanceList.get(0).getCreateTime().plusHours(8).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

                Map<String, BigDecimal> currencyDetailMap = new HashMap<>();
                for (AccountBalanceVO accountBalanceVO : balanceList) {
                    BigDecimal currencyDetail = currencyDetailMap.getOrDefault(accountBalanceVO.getCurrency().toUpperCase(), BigDecimal.ZERO);
                    currencyDetail = currencyDetail.add(accountBalanceVO.getUsd());
                    currencyDetailMap.put(accountBalanceVO.getCurrency().toUpperCase(), currencyDetail);
                }
                currencyDetailMap.forEach((name, usd) -> {
                    DetailsVo details = detailMap.getOrDefault(name, new DetailsVo(name));
                    details.getData().add(usd.toPlainString());
                    detailMap.put(name, details);
                });
            }
        });
        response.setTimeList(timeList);
        response.setAmountList(amountList);
        List<DetailsVo> detailsVoList = new ArrayList<>();
        List<String> detailLegendList = new ArrayList<>();
        detailMap.forEach((k, v) -> {
            detailsVoList.add(v);
            detailLegendList.add(k);
        });
        response.setDetailList(detailsVoList);
        response.setDetailLegendList(detailLegendList);

        List<AccountBalanceVO> newBalanceList = balanceHistory.get(balanceHistory.size() - 1);
        Map<String, BigDecimal> newBalanceMap = new HashMap<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (AccountBalanceVO accountBalanceVO : newBalanceList) {
            BigDecimal currencyDetail = newBalanceMap.getOrDefault(accountBalanceVO.getCurrency().toUpperCase(), BigDecimal.ZERO);
            currencyDetail = currencyDetail.add(accountBalanceVO.getUsd());
            sum = sum.add(accountBalanceVO.getUsd());
            newBalanceMap.put(accountBalanceVO.getCurrency().toUpperCase(), currencyDetail);
        }
        List<HoldRateVo> holdRateVoList = new ArrayList<>();
        BigDecimal finalSum = sum;
        newBalanceMap.forEach((name, usd) -> {
            BigDecimal rate = usd.divide(finalSum, 3, BigDecimal.ROUND_HALF_UP);
            if (rate.compareTo(BigDecimal.ZERO) == 1) {
                holdRateVoList.add(HoldRateVo.builder()
                        .name(name)
                        .value(rate.toPlainString())
                        .build());
            }
        });
        Collections.sort(holdRateVoList, new Comparator<HoldRateVo>() {
            @Override
            public int compare(HoldRateVo o1, HoldRateVo o2) {
                BigDecimal d1 = new BigDecimal(o1.getValue());
                BigDecimal d2 = new BigDecimal(o2.getValue());
                return d1.compareTo(d2);
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        response.setHoldRateList(holdRateVoList);
        return response;
    }

}
