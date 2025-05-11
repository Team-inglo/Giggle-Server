package com.inglo.giggle.banner.adapter.out.persistence.mapper;

import com.inglo.giggle.banner.adapter.out.persistence.entity.BannerEntity;
import com.inglo.giggle.banner.domain.Banner;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {
    public Banner toDomain(BannerEntity bannerEntity) {
        return Banner.builder()
                .id(bannerEntity.getId())
                .title(bannerEntity.getTitle())
                .imgUrl(bannerEntity.getImgUrl())
                .content(bannerEntity.getContent())
                .role(bannerEntity.getRole())
                .createdAt(bannerEntity.getCreatedAt())
                .updatedAt(bannerEntity.getUpdatedAt())
                .deletedAt(bannerEntity.getDeletedAt())
                .build();
    }

    public BannerEntity toEntity(Banner banner) {
        return BannerEntity.builder()
                .id(banner.getId())
                .title(banner.getTitle())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .role(banner.getRole())
                .build();
    }
}
