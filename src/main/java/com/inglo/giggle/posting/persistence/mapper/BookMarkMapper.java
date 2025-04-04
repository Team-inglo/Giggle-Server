package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.persistence.entity.BookMarkEntity;

import java.util.List;

public class BookMarkMapper {
    public static BookMark toDomain(BookMarkEntity entity) {
        if (entity == null) {
            return null;
        }
        return BookMark.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .jobPostingId(entity.getJobPostingId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static BookMarkEntity toEntity(BookMark domain) {
        if (domain == null) {
            return null;
        }
        return BookMarkEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .jobPostingId(domain.getJobPostingId())
                .build();
    }

    public static List<BookMark> toDomains(List<BookMarkEntity> entities) {
        return entities.stream()
                .map(BookMarkMapper::toDomain)
                .toList();
    }

    public static List<BookMarkEntity> toEntities(List<BookMark> domains) {
        return domains.stream()
                .map(BookMarkMapper::toEntity)
                .toList();
    }
}
