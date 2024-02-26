package com.vssoft.vspace.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vssoft.vspace.domain.Product} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {
    private String id;

    @NotNull
    private String name;

    @Lob
    private String content;
}
