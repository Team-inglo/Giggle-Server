package com.inglo.giggle.resume.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;

import java.util.List;

public class ReadUserLanguageSummaryResult extends SelfValidating<ReadUserLanguageSummaryResult> {
    @JsonProperty("topik_level")
    private final Integer topikLevel;

    @JsonProperty("social_integration_level")
    private final Integer socialIntegrationLevel;

    @JsonProperty("sejong_institute")
    private final Integer sejongInstitute;

    @JsonProperty("additional_language")
    private final List<AdditionalLanguageDto> additionalLanguageDto;

    @Builder
    public ReadUserLanguageSummaryResult(Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstitute, List<AdditionalLanguageDto> additionalLanguageDto) {
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

        public static AdditionalLanguageDto of(
                Long id,
                String languageName,
                Integer level
        ) {
            return AdditionalLanguageDto.builder()
                    .id(id)
                    .languageName(languageName)
                    .level(level)
                    .build();
        }
    }

    public static ReadUserLanguageSummaryResult of(
            Integer topikLevel,
            Integer socialIntegrationLevel,
            Integer sejongInstitute,
            List<AdditionalLanguageDto> additionalLanguageDto
    ) {
        return ReadUserLanguageSummaryResult.builder()
                .topikLevel(topikLevel)
                .socialIntegrationLevel(socialIntegrationLevel)
                .sejongInstitute(sejongInstitute)
                .additionalLanguageDto(additionalLanguageDto)
                .build();
    }
}
