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

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID languageSkillId;


    @Builder
    public AdditionalLanguage(Long id, String languageName, Integer level, UUID languageSkillId,
                              LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt)
    {
        this.id = id;
        this.languageName = languageName;
        this.level = level;
        this.languageSkillId = languageSkillId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void checkValidation(UUID accountId) {
        if (!languageSkillId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSelf(String languageName, Integer level) {
        this.languageName = languageName;
        this.level = level;
    }
}
