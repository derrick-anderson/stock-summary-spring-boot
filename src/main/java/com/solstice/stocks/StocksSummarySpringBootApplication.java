package com.solstice.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableDiscoveryClient
public class StocksSummarySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksSummarySpringBootApplication.class, args);
    }
}
