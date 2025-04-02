package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Reject extends BaseDomain {
    private Long id;
    private String reason;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private Long documentId;

    @Builder
    public Reject(Long id, String reason, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Long documentId) {
        this.id = id;
        this.reason = reason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.documentId = documentId;
    }
}

