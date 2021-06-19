package com.zhangtory.coinbalance;

import com.alibaba.fastjson.JSONObject;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.service.GetBalanceService;
import com.zhangtory.coinbalance.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class CoinBalanceApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RecordService recordService;

    @Test
    void logTest() {
        recordService.logBalanceRecord();
    }

    @Test
    void contextLoads() {
        Map<String, GetBalanceService> beansMap = applicationContext.getBeansOfType(GetBalanceService.class);
        beansMap.values().forEach(balance -> {
            try {
                List<Record> list = balance.getBalance();
                log.info(JSONObject.toJSONString(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
