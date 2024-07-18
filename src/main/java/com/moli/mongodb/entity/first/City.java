package com.moli.mongodb.entity.first;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-18 16:19:20
 */
@Data
@Document(collation = "city")
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {
    @Id
    private Long id;
    private String cityName;

}
