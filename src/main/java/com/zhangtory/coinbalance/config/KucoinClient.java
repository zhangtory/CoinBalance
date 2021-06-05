package com.zhangtory.coinbalance.config;

import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinPrivateWSClient;
import com.kucoin.sdk.KucoinPublicWSClient;
import com.kucoin.sdk.KucoinRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Scott on 2021/4/21
 **/
@Configuration
public class KucoinClient {

    @Value("${kucoin.api-key}")
    private String apiKey;

    @Value("${kucoin.api-secret}")
    private String apiSecret;

    @Value("${kucoin.api-password}")
    private String apiPassword;

    @Bean
    public KucoinRestClient kucoinRestClient() {
        return new KucoinClientBuilder()
                .withApiKey(apiKey, apiSecret, apiPassword)
                .buildRestClient();
    }

    @Bean
    public KucoinPublicWSClient kucoinPublicWSClient() throws IOException {
        return new KucoinClientBuilder()
                .withApiKey(apiKey, apiSecret, apiPassword)
                .buildPublicWSClient();
    }

    @Bean
    public KucoinPrivateWSClient kucoinPrivateWSClient() throws IOException {
        return new KucoinClientBuilder()
                .withApiKey(apiKey, apiSecret, apiPassword)
                .buildPrivateWSClient();
    }

}
