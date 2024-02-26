package com.vssoft.vspace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, updated.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdDate", "updatedDate"}, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "updated_date")
    private Instant updatedDate = Instant.now();

    public abstract T getId();
}
