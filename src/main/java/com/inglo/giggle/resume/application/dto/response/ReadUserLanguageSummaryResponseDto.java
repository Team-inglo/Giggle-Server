package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.LanguageSkill;
import lombok.Builder;

import java.util.List;

public class ReadUserLanguageSummaryResponseDto extends SelfValidating<ReadUserLanguageSummaryResponseDto> {
    @JsonProperty("topik_level")
    private final Integer topikLevel;

    @JsonProperty("social_integration_level")
    private final Integer socialIntegrationLevel;

    @JsonProperty("sejong_institute")
    private final Integer sejongInstitute;

    @JsonProperty("additional_language")
    private final List<AdditionalLanguageDto> additionalLanguageDto;

    @Builder
    public ReadUserLanguageSummaryResponseDto(Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstitute, List<AdditionalLanguageDto> additionalLanguageDto) {
        this.topikLevel = topikLevel;
        this.socialIntegrationLevel = socialIntegrationLevel;
        this.sejongInstitute = sejongInstitute;
        this.additionalLanguageDto = additionalLanguageDto;
        this.validateSelf();
    }

    public static class AdditionalLanguageDto {
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("language_name")
        private final String languageName;

        @JsonProperty("level")
        private final Integer level;

        @Builder
        public AdditionalLanguageDto(Long id, String languageName, Integer level) {
            this.id = id;
            this.languageName = languageName;
            this.level = level;
        }

        public static AdditionalLanguageDto fromEntity(AdditionalLanguage additionalLanguage) {
            return AdditionalLanguageDto.builder()
                    .id(additionalLanguage.getId())
                    .languageName(additionalLanguage.getLanguageName())
                    .level(additionalLanguage.getLevel())
                    .build();
        }
    }

    public static ReadUserLanguageSummaryResponseDto fromEntity(LanguageSkill languageSkill) {
        return ReadUserLanguageSummaryResponseDto.builder()
                .topikLevel(languageSkill.getTopikLevel())
                .socialIntegrationLevel(languageSkill.getSocialIntegrationLevel())
                .sejongInstitute(languageSkill.getSejongInstituteLevel())
                .additionalLanguageDto(!languageSkill.getAdditionalLanguages().isEmpty() ? languageSkill.getAdditionalLanguages().stream().map(AdditionalLanguageDto::fromEntity).toList() : null)
                .build();
    }
}
