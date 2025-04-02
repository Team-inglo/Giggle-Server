package com.inglo.giggle.core.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseDomain {

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;
}
