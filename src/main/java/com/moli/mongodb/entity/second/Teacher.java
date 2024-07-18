package com.moli.mongodb.entity.second;

/**
 * @author moli
 * @time 2024-07-18 14:30:06
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(collection = "teacher")
public class Teacher implements Serializable {

    @Id
    private String id;

    private String username;

    private LocalDateTime timer;
}
