package com.inglo.giggle.banner.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Banner extends BaseDomain {
    private Long id;
    private String title;
    private String imgUrl;
    private String content;
    private ESecurityRole role;

    @Builder
    public Banner(
            Long id,
            String title,
            String imgUrl,
            String content,
            ESecurityRole role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateWithImgUrl(String title, String imgUrl, String content, ESecurityRole role) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.role = role;
    }

    public void updateWithoutImgUrl(String title, String content, ESecurityRole role) {
        this.title = title;
        this.content = content;
        this.role = role;
    }
}
