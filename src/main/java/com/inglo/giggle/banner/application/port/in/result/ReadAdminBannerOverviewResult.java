package com.inglo.giggle.banner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadAdminBannerOverviewResult extends SelfValidating<ReadAdminBannerOverviewResult> {

    @JsonProperty("banners")
    private final List<BannerOverviewDto> banners;

    @JsonProperty("page_info")
    private final PageInfoDto pageInfo;

    @Builder
    public ReadAdminBannerOverviewResult(List<BannerOverviewDto> banners, PageInfoDto pageInfo) {
        this.banners = banners;
        this.pageInfo = pageInfo;
        this.validateSelf();
    }

    public static class BannerOverviewDto extends SelfValidating<BannerOverviewDto> {
        private static Banner bannerEntity;
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("title")
        private final String title;

        @JsonProperty("role")
        private final ESecurityRole role;

        @JsonProperty("registered_at")
        private final String registeredAt;

        @JsonProperty("img_url")
        private final String imgUrl;

        @Builder
        public BannerOverviewDto(Long id, String title, ESecurityRole role, String registeredAt, String imgUrl) {
            this.id = id;
            this.title = title;
            this.role = role;
            this.registeredAt = registeredAt;
            this.imgUrl = imgUrl;
            this.validateSelf();
        }

        public static BannerOverviewDto from(Banner banner) {
            BannerOverviewDto.bannerEntity = banner;
            return BannerOverviewDto.builder()
                    .id(banner.getId())
                    .title(banner.getTitle())
                    .role(banner.getRole())
                    .registeredAt(banner.getCreatedAt().toLocalDate().toString())
                    .imgUrl(banner.getImgUrl())
                    .build();
        }
    }

    public static ReadAdminBannerOverviewResult of(List<Banner> banners, PageInfoDto pageInfo) {
        return ReadAdminBannerOverviewResult.builder()
                .banners(banners.stream()
                        .map(BannerOverviewDto::from)
                        .toList())
                .pageInfo(pageInfo)
                .build();
    }
}
