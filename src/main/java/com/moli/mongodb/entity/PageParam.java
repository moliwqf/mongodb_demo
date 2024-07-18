package com.moli.mongodb.entity;

/**
 * @author moli
 * @time 2024-07-18 09:07:09
 */

import lombok.Data;

@Data
public class PageParam {

    private Integer pageIndex;

    private Integer pageSize;
}
