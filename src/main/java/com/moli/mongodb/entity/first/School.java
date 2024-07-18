package com.moli.mongodb.entity.first;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-18 16:19:13
 */
@Data
@Document(collation = "school")
@NoArgsConstructor
@AllArgsConstructor
public class School implements Serializable {
    @Id
    private Long id;
    private String schoolName;
    /**关联城市ID*/
    private Long cityId;
}

