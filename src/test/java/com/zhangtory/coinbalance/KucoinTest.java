package com.zhangtory.coinbalance;

import com.kucoin.sdk.KucoinRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Scott on 2021/6/5
 **/
@SpringBootTest
public class KucoinTest {

    @Autowired
    private KucoinRestClient kucoinRestClient;

    @Test
    void price() throws IOException {
        Map<String, BigDecimal> fiatPrice = kucoinRestClient.currencyAPI().getFiatPrice("USD", "btc");
        fiatPrice.forEach((k, v) -> {
            System.out.println(k+" "+v);
        });
    }

}
