package com.zhangtory.coinbalance.controller;

import com.zhangtory.coinbalance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Scott on 2021/6/4
 **/
@Component
public class RecordScheduler {

    @Autowired
    private RecordService recordService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void process() {
        recordService.logBalanceRecord();
    }

}
