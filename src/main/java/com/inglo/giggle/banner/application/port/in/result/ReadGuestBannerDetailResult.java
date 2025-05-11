package com.inglo.giggle.banner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadGuestBannerDetailResult extends SelfValidating<ReadGuestBannerDetailResult> {

    @JsonProperty("id")
    private final Long id;

    @JsonProperty("img_url")
    private final String imgUrl;

    @JsonProperty("content")
    private final String content;

    @Builder
    public ReadGuestBannerDetailResult(Long id, String imgUrl, String content) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.content = content;
        this.validateSelf();
    }

    public static ReadGuestBannerDetailResult from(Banner banner) {
        return ReadGuestBannerDetailResult.builder()
                .id(banner.getId())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .build();
    }
}
