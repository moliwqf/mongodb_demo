package com.moli.mongodb.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author moli
 * @time 2024-07-18 14:17:35
 * @description mongo第三个数据源
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.moli.mongodb.entity.third",
        mongoTemplateRef = "thirdMongo"
)
public class ThirdMongoTemplate {

    @Autowired
    @Qualifier("thirdMongoProperties")
    private MongoProperties mongoProperties;

    @Bean(name = "thirdMongo")
    public MongoTemplate buildMongoTemplate() throws Exception {
        return new MongoTemplate(thirdFactory(this.mongoProperties));
    }

    @Bean
    public MongoDatabaseFactory thirdFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongoProperties.getUri());
    }

    @Bean(name = "thirdTransactionManager")
    public MongoTransactionManager thirdTransactionManager() throws Exception {
        MongoDatabaseFactory mongoDbFactory = thirdFactory(this.mongoProperties);
        return new MongoTransactionManager(mongoDbFactory);
    }
}
