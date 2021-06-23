package com.zhangtory.coinbalance.config;

import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.BinanceApiWebSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Scott on 2021/6/23
 **/
@Configuration
public class BinanceClient {

    @Value("${binance.api-key}")
    private String apiKey;

    @Value("${binance.secret-key}")
    private String secretKey;

    @Bean
    public BinanceApiClientFactory binanceApiClientFactory() {
        return BinanceApiClientFactory.newInstance(apiKey, secretKey);
    }

    @Bean
    public BinanceApiRestClient binanceApiRestClient(BinanceApiClientFactory factory) {
        return factory.newRestClient();
    }

    @Bean
    public BinanceApiAsyncRestClient binanceApiAsyncRestClient(BinanceApiClientFactory factory) {
        return factory.newAsyncRestClient();
    }

    @Bean
    public BinanceApiWebSocketClient binanceApiWebSocketClient(BinanceApiClientFactory factory) {
        return factory.newWebSocketClient();
    }

}
