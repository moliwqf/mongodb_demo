package com.moli.mongodb.entity.first;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-18 16:19:06
 */
@Data
@Document(collation = "studentClass")
@NoArgsConstructor
@AllArgsConstructor
public class StudentClass implements Serializable {
    @Id
    private Long id;
    private String className;
    /**关联学校*/
    private Long schoolId;
}


