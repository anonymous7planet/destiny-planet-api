package com.planet.destiny.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
//@ComponentScan(value = {
//        "com.planet.anonymous.gateway"
//})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayServerApplication {

    public static void main(String args[]) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
