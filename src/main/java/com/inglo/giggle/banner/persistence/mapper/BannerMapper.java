package com.inglo.giggle.banner.persistence.mapper;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.persistence.entity.BannerEntity;

import java.util.List;

public class BannerMapper {
    public static Banner toDomain(BannerEntity bannerEntity) {
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

    public static BannerEntity toEntity(Banner banner) {
        return BannerEntity.builder()
                .title(banner.getTitle())
                .imgUrl(banner.getImgUrl())
                .content(banner.getContent())
                .role(banner.getRole())
                .build();
    }

    public static List<Banner> toDomains(List<BannerEntity> bannerEntities) {
        return bannerEntities.stream()
                .map(BannerMapper::toDomain)
                .toList();
    }

    public static List<BannerEntity> toEntities(List<Banner> banners) {
        return banners.stream()
                .map(BannerMapper::toEntity)
                .toList();
    }
}
