package com.inglo.giggle.resume.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.ResumeAggregate;
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

        public static AdditionalLanguageDto from(AdditionalLanguage additionalLanguage) {
            return AdditionalLanguageDto.builder()
                    .id(additionalLanguage.getId())
                    .languageName(additionalLanguage.getLanguageName())
                    .level(additionalLanguage.getLevel())
                    .build();
        }
    }

    public static ReadUserLanguageSummaryResponseDto from(ResumeAggregate resumeAggregate) {
        return ReadUserLanguageSummaryResponseDto.builder()
                .topikLevel(resumeAggregate.getLanguageSkill().getTopikLevel())
                .socialIntegrationLevel(resumeAggregate.getLanguageSkill().getSocialIntegrationLevel())
                .sejongInstitute(resumeAggregate.getLanguageSkill().getSejongInstituteLevel())
                .additionalLanguageDto(!resumeAggregate.getAdditionalLanguages().isEmpty() ? resumeAggregate.getAdditionalLanguages().stream().map(AdditionalLanguageDto::from).toList() : null)
                .build();
    }
}
