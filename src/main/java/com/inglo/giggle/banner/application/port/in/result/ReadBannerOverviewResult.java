package com.inglo.giggle.banner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadBannerOverviewResult extends SelfValidating<ReadBannerOverviewResult> {

    @JsonProperty("banner_list")
    private final List<BannerListDto> schoolList;

    @Builder
    public ReadBannerOverviewResult(List<BannerListDto> schoolList) {
        this.schoolList = schoolList;
        this.validateSelf();
    }

    @Getter
    public static class BannerListDto extends SelfValidating<BannerListDto> {
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("img_url")
        private final String imgUrl;

        @Builder
        public BannerListDto(Long id, String imgUrl) {
            this.id = id;
            this.imgUrl = imgUrl;
            this.validateSelf();
        }

        public static BannerListDto from(Banner banner) {
            return BannerListDto.builder()
                    .id(banner.getId())
                    .imgUrl(banner.getImgUrl())
                    .build();
        }
    }

    public static ReadBannerOverviewResult from(List<Banner> banners) {
        return ReadBannerOverviewResult.builder()
                .schoolList(banners.stream().map(BannerListDto::from).toList())
                .build();
    }
}
