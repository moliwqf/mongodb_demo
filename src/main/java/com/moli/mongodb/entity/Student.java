package com.moli.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author moli
 * @time 2024-07-18 08:45:10
 */
@Data
@Document(collection = "student")  //指定要对应的文档名(表名）
public class Student implements Serializable {

    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id (本文实体类中字段为为id，意思查询字段为_id，但查询结果_id会映射到实体对象id字段中）
     */
    @Id
    private String id;

    private String username;

    private LocalDateTime timer;
}
