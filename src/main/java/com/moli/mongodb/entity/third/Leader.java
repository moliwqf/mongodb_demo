package com.moli.mongodb.entity.third;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author moli
 * @time 2024-07-18 14:31:23
 */

@Data
@Document(collection = "leader")
public class Leader {

    @Id
    private String id;

    private String username;

    private LocalDateTime timer;
}
