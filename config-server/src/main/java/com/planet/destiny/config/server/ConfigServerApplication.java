package com.planet.destiny.config.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
    /**
     * endpoint 예시
     * GET /{application}/{profile}[/{label}]
     * GET /{application}-{profile}.yml
     * GET /{label}/{application}-{profile}.yml
     * GET /{application}-{profile}.properties
     * GET /{label}/{application}-{profile}.properties
     *
     * application : 애플리케이션 이름
     * profile : 환경
     * label : git저장소의 브랜치 정보(기본값 : master)
     * @param args
     */
    public static void main(String args[]) {
        SpringApplication.run(ConfigServerApplication.class);
    }
}
