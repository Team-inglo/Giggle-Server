package com.inglo.giggle.term.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TermAccount extends BaseDomain {
    private Long id;
    private Boolean isAccepted;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID accountId;
    private Long termId;

    @Builder
    public TermAccount(Long id, Boolean isAccepted,
                       UUID accountId, Long termId,
                       LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.isAccepted = isAccepted;
        this.accountId = accountId;
        this.termId = termId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
