package com.moli.mongodb.entity.first;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-18 16:19:00
 */
@Data
@Document(collation = "tStudent")
@NoArgsConstructor
@AllArgsConstructor
public class TStudent implements Serializable {
    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id 。不必细究(本文实体类中为id）
     */
    @Id
    private Long id;
    private String username;
    /**
     * 关联班级ID
     */
    private Long classId;
}

