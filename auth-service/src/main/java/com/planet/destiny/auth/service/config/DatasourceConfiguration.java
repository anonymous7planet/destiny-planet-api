package com.planet.destiny.auth.service.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "planetEntityManager",
        transactionManagerRef = "planetTransactionManager",
        basePackages = {"com.planet.destiny.*.service.module.*.repository", "com.planet.destiny.*.service.module.*.*.repository"}
)
@MapperScan(basePackages = {"com.planet.destiny.*.module.*.dao"})
public class DatasourceConfiguration {

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private Long connectionTimeout;

    @Value("${spring.datasource.hikari.validation-timeout}")
    private Long validationTimeout;

    @Value("${spring.datasource.hikari.max-lifetime}")
    private Long maxLifetime;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private Integer minimumPoolSize;

    @Value("${spring.datasource.hikari.auto-commit}")
    private Boolean autoCommit;

    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;

    @Primary
    @Bean(name = "planetDataSource")
    @ConfigurationProperties(prefix = "planet.datasource")
    public DataSource planetDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setValidationTimeout(validationTimeout);
        hikariDataSource.setMaxLifetime(maxLifetime);
        hikariDataSource.setMinimumIdle(minimumPoolSize);
        hikariDataSource.setAutoCommit(autoCommit);
        hikariDataSource.setConnectionTestQuery(connectionTestQuery);

        return hikariDataSource;
    }

    @Primary
    @Bean(name = "planetEntityManager")
    public LocalContainerEntityManagerFactoryBean planetEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("planetDataSource") DataSource planetDataSource) {
        return builder
                .dataSource(planetDataSource)
                .packages("com.planet.destiny.*.service.module.*.model", "com.planet.destiny.*.service.module.*.*.model")
                .persistenceUnit("destiny-planet")
                .build();
    }

    @Primary
    @Bean(name = "planetTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("planetEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
