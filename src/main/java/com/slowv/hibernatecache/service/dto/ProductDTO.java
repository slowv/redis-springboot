package com.slowv.hibernatecache.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.slowv.hibernatecache.domain.Product} entity.
 */
@Data
public class ProductDTO implements Serializable {
    private String id;
    private String name;
    private String content;
}
