package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class LanguageSkill extends BaseDomain {

    private Integer topikLevel;
    private Integer socialIntegrationLevel;
    private Integer sejongInstituteLevel;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    private List<AdditionalLanguage> additionalLanguages;

    /* -------------------------------------------- */
    /* One To One Child Mapping-------------------- */
    /* -------------------------------------------- */
    private UUID resumeId;

    @Builder
    public LanguageSkill(Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstituteLevel, List<AdditionalLanguage> additionalLanguages, UUID resumeId) {
        this.topikLevel = topikLevel;
        this.socialIntegrationLevel = socialIntegrationLevel;
        this.sejongInstituteLevel = sejongInstituteLevel;
        this.additionalLanguages = additionalLanguages;
        this.resumeId = resumeId;
    }

    public void checkValidation(UUID accountId) {
        if (!resumeId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSejongInstituteLevel(Integer level) {
        this.sejongInstituteLevel = level;
    }

    public void updateSocialIntegrationLevel(Integer level) {
        this.socialIntegrationLevel = level;
    }

    public void updateTopikLevel(Integer level) {
        this.topikLevel = level;
    }
}
