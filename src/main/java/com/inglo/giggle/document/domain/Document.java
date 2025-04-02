package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public abstract class Document extends BaseDomain {
    protected Long id;
    protected String wordUrl;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    protected List<Reject> rejects;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    protected Long userOwnerJobPostingId;

    protected Document(Long id, String wordUrl, List<Reject> rejects, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Long userOwnerJobPostingId) {
        this.id = id;
        this.wordUrl = wordUrl;
        this.rejects = rejects;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.userOwnerJobPostingId = userOwnerJobPostingId;
    }

    public void updateWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }
}
