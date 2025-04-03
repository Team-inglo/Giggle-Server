package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.persistence.entity.PostingWorkDayTimeEntity;
import org.hibernate.collection.spi.PersistentCollection;

import java.util.Collection;
import java.util.List;

public class PostingWorkDayTimeMapper {

    public static PostingWorkDayTime toDomain(PostingWorkDayTimeEntity entity) {
        if (entity == null) {
            return null;
        }
        return PostingWorkDayTime.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .workStartTime(entity.getWorkStartTime())
                .workEndTime(entity.getWorkEndTime())
                .jobPostingId(isInitialized(entity.getJobPostingEntity()) ? entity.getJobPostingEntity().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static PostingWorkDayTimeEntity toEntity(PostingWorkDayTime domain) {
        if (domain == null) {
            return null;
        }
        return PostingWorkDayTimeEntity.builder()
                .dayOfWeek(domain.getDayOfWeek())
                .workStartTime(domain.getWorkStartTime())
                .workEndTime(domain.getWorkEndTime())
                .build();
    }

    public static List<PostingWorkDayTime> toDomains(List<PostingWorkDayTimeEntity> entities) {
        return entities.stream()
                .map(PostingWorkDayTimeMapper::toDomain)
                .toList();
    }

    public static List<PostingWorkDayTimeEntity> toEntities(List<PostingWorkDayTime> domains) {
        return domains.stream()
                .map(PostingWorkDayTimeMapper::toEntity)
                .toList();
    }

    private static boolean isInitialized(Object object) {
        return object instanceof org.hibernate.collection.spi.PersistentCollection &&
                ((PersistentCollection<?>) object).wasInitialized();
    }
}
