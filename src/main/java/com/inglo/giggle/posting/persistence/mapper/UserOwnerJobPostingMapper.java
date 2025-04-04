package com.inglo.giggle.posting.persistence.mapper;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;

import java.util.List;

public class UserOwnerJobPostingMapper {
    public static UserOwnerJobPosting toDomain(UserOwnerJobPostingEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserOwnerJobPosting.builder()
                .id(entity.getId())
                .step(entity.getStep())
                .lastStepUpdated(entity.getLastStepUpdated())
                .result(entity.getResult())
                .feedback(entity.getFeedback())
                .memo(entity.getMemo())
                .userId(entity.getUserId())
                .ownerId(entity.getOwnerId())
                .jobPostingId(entity.getJobPostingId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public static UserOwnerJobPostingEntity toEntity(UserOwnerJobPosting domain) {
        if (domain == null) {
            return null;
        }
        return UserOwnerJobPostingEntity.builder()
                .id(domain.getId())
                .step(domain.getStep())
                .lastStepUpdated(domain.getLastStepUpdated())
                .result(domain.getResult())
                .feedback(domain.getFeedback())
                .memo(domain.getMemo())
                .userId(domain.getUserId())
                .ownerId(domain.getOwnerId())
                .jobPostingId(domain.getJobPostingId())
                .build();
    }

    public static List<UserOwnerJobPosting> toDomains(List<UserOwnerJobPostingEntity> entities) {
        return entities.stream()
                .map(UserOwnerJobPostingMapper::toDomain)
                .toList();
    }

    public static List<UserOwnerJobPostingEntity> toEntities(List<UserOwnerJobPosting> domains) {
        return domains.stream()
                .map(UserOwnerJobPostingMapper::toEntity)
                .toList();
    }
}