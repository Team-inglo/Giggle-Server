package com.inglo.giggle.document.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserDocumentSummaryResult extends SelfValidating<ReadUserDocumentSummaryResult> {

    @JsonProperty("part_time_employment_permits")
    private final DocumentDetailDto partTimeEmploymentPermits;

    @JsonProperty("standard_labor_contract")
    private final DocumentDetailDto standardLaborContract;

    @JsonProperty("integrated_application")
    private final SimpleDocumentDto integratedApplication;

    @JsonProperty("is_completed")
    private final Boolean isCompleted;

    @Builder
    public ReadUserDocumentSummaryResult(
            DocumentDetailDto partTimeEmploymentPermits,
            DocumentDetailDto standardLaborContract,
            SimpleDocumentDto integratedApplication,
            Boolean isCompleted

    ) {
        this.partTimeEmploymentPermits = partTimeEmploymentPermits;
        this.standardLaborContract = standardLaborContract;
        this.integratedApplication = integratedApplication;
        this.isCompleted = isCompleted;

        this.validateSelf();
    }

    @Getter
    public static class DocumentDetailDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("word_url")
        private final String wordUrl;

        @JsonProperty("status")
        private final String status;

        @Builder
        public DocumentDetailDto(Long id, String wordUrl, EEmployeeStatus status) {
            this.id = id;
            this.wordUrl = wordUrl;
            this.status = status.getEnName();
        }

        public static DocumentDetailDto of(
                Long documentId,
                String wordUrl,
                EEmployeeStatus status
        ) {
            return DocumentDetailDto.builder()
                    .id(documentId)
                    .wordUrl(wordUrl)
                    .status(status)
                    .build();
        }
    }

    @Getter
    public static class SimpleDocumentDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("word_url")
        private final String wordUrl;

        @Builder
        public SimpleDocumentDto(Long id, String wordUrl) {
            this.id = id;
            this.wordUrl = wordUrl;
        }

        public static SimpleDocumentDto of(Long documentId, String wordUrl) {
            return SimpleDocumentDto.builder()
                    .id(documentId)
                    .wordUrl(wordUrl)
                    .build();
        }
    }

    public static ReadUserDocumentSummaryResult of(
            DocumentDetailDto partTimeEmploymentPermits,
            DocumentDetailDto standardLaborContract,
            SimpleDocumentDto integratedApplication,
            Boolean isCompleted
    ) {
        return ReadUserDocumentSummaryResult.builder()
                .partTimeEmploymentPermits(partTimeEmploymentPermits)
                .standardLaborContract(standardLaborContract)
                .integratedApplication(integratedApplication)
                .isCompleted(isCompleted)
                .build();
    }
}
