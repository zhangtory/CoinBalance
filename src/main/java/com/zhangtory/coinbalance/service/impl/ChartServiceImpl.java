package com.zhangtory.coinbalance.service.impl;

import com.zhangtory.coinbalance.controller.Request.BalanceHistoryRequest;
import com.zhangtory.coinbalance.controller.response.ChartResponse;
import com.zhangtory.coinbalance.model.vo.AccountBalanceVO;
import com.zhangtory.coinbalance.service.ChartService;
import com.zhangtory.coinbalance.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        balanceHistory.forEach(balanceList -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (AccountBalanceVO accountBalanceVO : balanceList) {
                sum = sum.add(accountBalanceVO.getUsd());
            }
            amountList.add(sum.toPlainString());
            if (!balanceList.isEmpty()) {
                timeList.add(balanceList.get(0).getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            } else {
                timeList.add("no_record");
            }
        });
        response.setTimeList(timeList);
        response.setAmountList(amountList);
        return response;
    }

}
