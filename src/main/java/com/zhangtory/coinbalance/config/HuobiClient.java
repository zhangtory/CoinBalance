package com.zhangtory.coinbalance.config;

import com.huobi.client.AccountClient;
import com.huobi.client.SubUserClient;
import com.huobi.constant.HuobiOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Scott on 2021/6/4
 **/
@Configuration
public class HuobiClient {

    @Value("${huobi.api-key}")
    private String apiKey;

    @Value("${huobi.secret-key}")
    private String secretKey;

    @Bean
    public HuobiOptions huobiOptions() {
        return HuobiOptions.builder()
                .apiKey(apiKey)
                .secretKey(secretKey)
                .build();
    }

    @Bean
    public AccountClient huobiAccountClient(HuobiOptions huobiOptions) {
        return AccountClient.create(huobiOptions);
    }

    @Bean
    public SubUserClient huobiSubUserClient(HuobiOptions huobiOptions) {
        return SubUserClient.create(huobiOptions);
    }

}
