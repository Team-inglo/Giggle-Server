package com.inglo.giggle.banner.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadBannerDetailResponseDto extends SelfValidating<ReadBannerDetailResponseDto> {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("img_url")
    private final String imgUrl;

    @JsonProperty("content")
    private final String content;

    @Builder
    public ReadBannerDetailResponseDto(Long id, String imgUrl, String content) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.content = content;
        this.validateSelf();
    }

    public static ReadBannerDetailResponseDto fromEntity(Banner banner) {
        return ReadBannerDetailResponseDto.builder()
                .id(banner.getId())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .build();
    }


}
