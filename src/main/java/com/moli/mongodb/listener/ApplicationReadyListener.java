package com.moli.mongodb.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author moli
 * @time 2024-07-18 08:42:20
 * @description 程序启动监听器
 */
@Component
public class ApplicationReadyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private MongoTemplate mongoTemplate;

    private static final String TYPEKEY = "_class";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        MongoConverter converter = mongoTemplate.getConverter();
        if (converter.getTypeMapper().isTypeKey(TYPEKEY)) {
            ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));
        }
    }
}
