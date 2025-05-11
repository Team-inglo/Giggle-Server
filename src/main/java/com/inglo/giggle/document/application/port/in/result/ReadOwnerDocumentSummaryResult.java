package com.inglo.giggle.document.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerDocumentSummaryResult extends SelfValidating<ReadOwnerDocumentSummaryResult> {

    @JsonProperty("part_time_employment_permits")
    private final DocumentDetailDto partTimeEmploymentPermits;

    @JsonProperty("standard_labor_contract")
    private final DocumentDetailDto standardLaborContract;

    @JsonProperty("is_completed")
    private final Boolean isCompleted;

    @Builder
    public ReadOwnerDocumentSummaryResult(
            DocumentDetailDto partTimeEmploymentPermits,
            DocumentDetailDto standardLaborContract,
            Boolean isCompleted

    ) {
        this.partTimeEmploymentPermits = partTimeEmploymentPermits;
        this.standardLaborContract = standardLaborContract;
        this.isCompleted = isCompleted;

        this.validateSelf();
    }

    @Getter
    public static class DocumentDetailDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

//        @JsonProperty("hwp_url")
//        private final String hwpUrl;

        @JsonProperty("word_url")
        private final String wordUrl;

        @JsonProperty("status")
        private final String status;

        @JsonProperty("reason")
        private final String reason;

        @Builder
        public DocumentDetailDto(Long id, String wordUrl, EEmployerStatus status, String reason) {
            this.id = id;
            this.wordUrl = wordUrl;
            this.status = status != null ? status.getEnName() : null;
            this.reason = reason;
        }

        public static DocumentDetailDto of(Long documentId, String wordUrl, EEmployerStatus status, String reason) {
            return DocumentDetailDto.builder()
                    .id(documentId)
                    .wordUrl(wordUrl)
                    .status(status)
                    .reason(reason)
                    .build();
        }
    }

    public static ReadOwnerDocumentSummaryResult of(
            DocumentDetailDto partTimeEmploymentPermit,
            DocumentDetailDto standardLaborContract,
            Boolean isCompleted
    ) {
        return ReadOwnerDocumentSummaryResult.builder()
                .partTimeEmploymentPermits(partTimeEmploymentPermit)
                .standardLaborContract(standardLaborContract)
                .isCompleted(isCompleted)
                .build();
    }
}
