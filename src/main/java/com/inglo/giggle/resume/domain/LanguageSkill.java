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

    private UUID id;
    private Integer topikLevel;
    private Integer socialIntegrationLevel;
    private Integer sejongInstituteLevel;
    private List<AdditionalLanguage> additionalLanguages;

    @Builder
    public LanguageSkill(UUID id, Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstituteLevel,
                         List<AdditionalLanguage> additionalLanguages) {
        this.id = id;
        this.topikLevel = topikLevel;
        this.socialIntegrationLevel = socialIntegrationLevel;
        this.sejongInstituteLevel = sejongInstituteLevel;
        this.additionalLanguages = additionalLanguages;
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        add                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void addAdditionalLanguage(String languageName, Integer level) {
        AdditionalLanguage additionalLanguage = AdditionalLanguage.builder()
                .languageName(languageName)
                .level(level)
                .build();
        this.additionalLanguages.add(additionalLanguage);
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
