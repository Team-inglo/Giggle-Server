package com.inglo.giggle.resume.persistence.mapper;

import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;

public class ResumeMapper {
    public static Resume toDomain(ResumeEntity entity) {
        if (entity == null) {
            return null;
        }
        return Resume.builder()
                .accountId(entity.getAccountId())
                .introduction(entity.getIntroduction())
                .build();
    }

    public static ResumeEntity toEntity(Resume domain) {
        if (domain == null) {
            return null;
        }
        return ResumeEntity.builder()
                .accountId(domain.getAccountId())
                .introduction(domain.getIntroduction())
                .build();
    }
}
