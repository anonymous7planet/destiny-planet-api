package com.planet.destiny.core.api.config;


import com.planet.destiny.core.api.handler.AsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);            // 기본적으로 실행 대기 중인 Thread 갯수
        executor.setMaxPoolSize(10);            // 동시에 동작하는 최대 Thread 갯수
        executor.setQueueCapacity(500);         // CorePool이 초과 될 때 Queue에 저장했다가 꺼내서 실행된다.(500개까지 저장함)
        // 단, MaxPoolSize가 초과되면 Thread 생성에 실패할 수 있음
        // 참고: https://medium.com/trendyol-tech/spring-boot-async-executor-management-with-threadpooltaskexecutor-f493903617d
        executor.setThreadNamePrefix("async-"); // Spring에서 생성하는 Thread 이름의 접두사
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
