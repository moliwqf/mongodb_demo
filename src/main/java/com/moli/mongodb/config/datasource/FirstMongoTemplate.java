package com.moli.mongodb.config.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author moli
 * @time 2024-07-18 14:11:29
 * @description mongo第一个数据源 basePackages 指向第二个数据源中所以有实体类对应的包路径，那么才操作该书体类时会直接影响Mongo对应库 例如 新增
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.moli.mongodb.entity.first",
        mongoTemplateRef = "firstMongo"
)
public class FirstMongoTemplate {

    @Autowired
    @Qualifier("firstMongoProperties")
    private MongoProperties mongoProperties;

    @Primary
    @Bean(name = "firstMongo")
    public MongoTemplate buildMongoTemplate() throws Exception {
        return new MongoTemplate(firstFactory(this.mongoProperties));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory firstFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongoProperties.getUri());
    }

    @Bean(name = "firstTransactionManager")
    public MongoTransactionManager firstTransactionManager() throws Exception {
        MongoDatabaseFactory mongoDbFactory = firstFactory(this.mongoProperties);
        return new MongoTransactionManager(mongoDbFactory);
    }
}
