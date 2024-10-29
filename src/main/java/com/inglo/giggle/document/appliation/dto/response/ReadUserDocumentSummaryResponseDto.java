package com.inglo.giggle.document.appliation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserDocumentSummaryResponseDto extends SelfValidating<ReadUserDocumentSummaryResponseDto> {

    @JsonProperty("part_time_employment_permits")
    private final DocumentDetailDto partTimeEmploymentPermits;

    @JsonProperty("standard_labor_contract")
    private final DocumentDetailDto standardLaborContract;

    @JsonProperty("integrated_application")
    private final SimpleDocumentDto integratedApplication;

    @Builder
    public ReadUserDocumentSummaryResponseDto(
            DocumentDetailDto partTimeEmploymentPermits,
            DocumentDetailDto standardLaborContract,
            SimpleDocumentDto integratedApplication
    ) {
        this.partTimeEmploymentPermits = partTimeEmploymentPermits;
        this.standardLaborContract = standardLaborContract;
        this.integratedApplication = integratedApplication;

        this.validateSelf();
    }

    @Getter
    public static class DocumentDetailDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("hwp_url")
        private final String hwpUrl;

        @JsonProperty("word_url")
        private final String wordUrl;

        @JsonProperty("status")
        private final String status;

        @Builder
        public DocumentDetailDto(Long id, String hwpUrl, String wordUrl, EEmployeeStatus status) {
            this.id = id;
            this.hwpUrl = hwpUrl;
            this.wordUrl = wordUrl;
            this.status = status.getEnName();
        }

        public static DocumentDetailDto fromEntity(Document document, EEmployeeStatus status) {
            return DocumentDetailDto.builder()
                    .id(document.getId())
                    .hwpUrl(document.getHwpUrl())
                    .wordUrl(document.getWordUrl())
                    .status(status)
                    .build();
        }
    }

    @Getter
    public static class SimpleDocumentDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("hwp_url")
        private final String hwpUrl;

        @JsonProperty("word_url")
        private final String wordUrl;

        @Builder
        public SimpleDocumentDto(Long id, String hwpUrl, String wordUrl) {
            this.id = id;
            this.hwpUrl = hwpUrl;
            this.wordUrl = wordUrl;
        }

        public static SimpleDocumentDto fromEntity(Document document) {
            return SimpleDocumentDto.builder()
                    .id(document.getId())
                    .hwpUrl(document.getHwpUrl())
                    .wordUrl(document.getWordUrl())
                    .build();
        }
    }

    public static ReadUserDocumentSummaryResponseDto of(
            PartTimeEmploymentPermit partTimeEmploymentPermits,
            StandardLaborContract standardLaborContract,
            IntegratedApplication integratedApplication
    ) {
        return ReadUserDocumentSummaryResponseDto.builder()
                .partTimeEmploymentPermits(partTimeEmploymentPermits != null ? DocumentDetailDto.fromEntity(partTimeEmploymentPermits, partTimeEmploymentPermits.getEmployeeStatus()) : null)
                .standardLaborContract(standardLaborContract != null ? DocumentDetailDto.fromEntity(standardLaborContract, standardLaborContract.getEmployeeStatus()) : null)
                .integratedApplication(integratedApplication != null ? SimpleDocumentDto.fromEntity(integratedApplication) : null)
                .build();
    }
}
