package com.zhangtory.coinbalance;

import com.alibaba.fastjson.JSONObject;
import com.zhangtory.coinbalance.model.entity.Record;
import com.zhangtory.coinbalance.service.impl.BinanceBalanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * Created by Scott on 2021/6/23
 **/
@SpringBootTest
@Slf4j
public class BinanceTest {

    @Autowired
    private BinanceBalanceServiceImpl binanceBalanceService;

    @Test
    void getBalanceTest() throws IOException {
        List<Record> balance = binanceBalanceService.getBalance();
        log.info(JSONObject.toJSONString(balance));
    }

}
