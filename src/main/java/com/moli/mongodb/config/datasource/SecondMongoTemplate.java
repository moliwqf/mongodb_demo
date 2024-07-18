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
 * @time 2024-07-18 14:16:25
 * @description mongo第二个数据源 basePackages 指向第二个数据源中所以有实体类对应的包路径，那么才操作该书体类时会直接影响Mongo对应库 例如 新增
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.moli.mongodb.entity.second",
        mongoTemplateRef = "secondMongo"
)
public class SecondMongoTemplate {

    @Autowired
    @Qualifier("secondMongoProperties")
    private MongoProperties mongoProperties;

    @Bean(name = "secondMongo")
    public MongoTemplate buildMongoTemplate() throws Exception {
        return new MongoTemplate(secondFactory(this.mongoProperties));
    }

    @Bean
    public MongoDatabaseFactory secondFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongoProperties.getUri());
    }

    @Bean(name = "secondTransactionManager")
    public MongoTransactionManager firstTransactionManager() throws Exception {
        MongoDatabaseFactory mongoDbFactory = secondFactory(this.mongoProperties);
        return new MongoTransactionManager(mongoDbFactory);
    }
}
