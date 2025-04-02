package com.inglo.giggle.posting.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyImage extends BaseDomain {
    private Long id;
    private String imgUrl;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private Long jobPostingId;

    @Builder
    public CompanyImage(Long id, String imgUrl, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Long jobPostingId) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.jobPostingId = jobPostingId;
    }
}

