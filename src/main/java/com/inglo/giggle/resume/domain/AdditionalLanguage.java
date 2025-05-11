package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AdditionalLanguage extends BaseDomain {

    private Long id;
    private String languageName;
    private Integer level;

    @Builder
    public AdditionalLanguage(Long id, String languageName, Integer level,
                              LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt)
    {
        this.id = id;
        this.languageName = languageName;
        this.level = level;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void updateSelf(String languageName, Integer level) {
        this.languageName = languageName;
        this.level = level;
    }
}
