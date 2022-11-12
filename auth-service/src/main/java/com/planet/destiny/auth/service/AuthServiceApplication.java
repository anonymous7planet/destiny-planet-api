package com.planet.destiny.auth.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableEurekaClient
@EnableDiscoveryClient
@ComponentScan(value = "com.planet.destiny")
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String args[]) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
