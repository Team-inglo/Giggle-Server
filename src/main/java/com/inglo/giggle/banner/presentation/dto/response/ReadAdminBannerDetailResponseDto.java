package com.inglo.giggle.banner.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.persistence.entity.BannerEntity;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminBannerDetailResponseDto extends SelfValidating<ReadAdminBannerDetailResponseDto> {

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
    public ReadAdminBannerDetailResponseDto(Long id, String title, ESecurityRole role, String imgUrl, String content) {
        this.id = id;
        this.title = title;
        this.role = role;
        this.imgUrl = imgUrl;
        this.content = content;
        this.validateSelf();
    }

    public static ReadAdminBannerDetailResponseDto from(Banner banner) {
        return ReadAdminBannerDetailResponseDto.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .role(banner.getRole())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .build();
    }
}
