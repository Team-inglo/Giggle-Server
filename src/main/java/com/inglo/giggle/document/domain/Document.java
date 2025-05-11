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
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    protected Long userOwnerJobPostingId;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    protected List<Reject> rejects;

    protected Document(
            Long id,
            String wordUrl,
            Long userOwnerJobPostingId,
            List<Reject> rejects,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.wordUrl = wordUrl;
        this.userOwnerJobPostingId = userOwnerJobPostingId;
        this.rejects = rejects;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }

    public void deleteAllRejects() {
        this.rejects.clear();
    }
}
