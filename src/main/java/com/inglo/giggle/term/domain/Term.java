package com.inglo.giggle.term.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.term.domain.type.ETermType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Term extends BaseDomain {
    private Long id;
    private String content;
    private ETermType termType;
    private Double version;

    @Builder
    public Term(
            Long id, String content, ETermType termType, Double version,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.content = content;
        this.termType = termType;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
