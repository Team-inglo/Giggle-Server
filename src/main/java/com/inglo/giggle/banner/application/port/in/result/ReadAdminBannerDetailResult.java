package com.inglo.giggle.banner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminBannerDetailResult extends SelfValidating<ReadAdminBannerDetailResult> {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("role")
    private final ESecurityRole role;

    @JsonProperty("img_url")
    private final String imgUrl;

    @JsonProperty("content")
    private final String content;

    @Builder
    public ReadAdminBannerDetailResult(Long id, String title, ESecurityRole role, String imgUrl, String content) {
        this.id = id;
        this.title = title;
        this.role = role;
        this.imgUrl = imgUrl;
        this.content = content;
        this.validateSelf();
    }

    public static ReadAdminBannerDetailResult from(Banner banner) {
        return ReadAdminBannerDetailResult.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .role(banner.getRole())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .build();
    }
}
