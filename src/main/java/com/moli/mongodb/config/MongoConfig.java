package com.moli.mongodb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author moli
 * @time 2024-07-18 14:08:51
 * @description mongodb 配置类 - 连接
 */
@Configuration
public class MongoConfig {

    @Bean(name = "firstMongoProperties")
    @Primary  //必须设一个主库 不然会报错
    @ConfigurationProperties(prefix = "spring.data.mongodb.first")
    public MongoProperties statisMongoProperties() {
        System.out.println("-------------------- firstMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "secondMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.second")
    public MongoProperties twoMongoProperties() {
        System.out.println("-------------------- twoMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "thirdMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.third")
    public MongoProperties threeMongoProperties() {
        System.out.println("-------------------- threeMongoProperties init ---------------------");
        return new MongoProperties();
    }

    @Bean(name = "chainedTransactionManager") // 配置统一的事务管理器
    public ChainedTransactionManager transactionManager(
            @Qualifier("firstTransactionManager") PlatformTransactionManager ds1,
            @Qualifier("secondTransactionManager") PlatformTransactionManager ds2,
            @Qualifier("thirdTransactionManager") PlatformTransactionManager ds3
    ) {
        return new ChainedTransactionManager(ds1, ds2, ds3);
    }
}
