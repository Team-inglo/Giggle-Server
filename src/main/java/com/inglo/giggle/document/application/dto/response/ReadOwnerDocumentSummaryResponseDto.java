package com.inglo.giggle.document.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerDocumentSummaryResponseDto extends SelfValidating<ReadOwnerDocumentSummaryResponseDto> {

    @JsonProperty("part_time_employment_permits")
    private final DocumentDetailDto partTimeEmploymentPermits;

    @JsonProperty("standard_labor_contract")
    private final DocumentDetailDto standardLaborContract;

    @JsonProperty("is_completed")
    private final Boolean isCompleted;

    @Builder
    public ReadOwnerDocumentSummaryResponseDto(
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
//            this.hwpUrl = hwpUrl;
            this.wordUrl = wordUrl;
            this.status = status != null ? status.getEnName() : null;
            this.reason = reason;
        }

        public static DocumentDetailDto fromEntity(Document document, EEmployerStatus status, String reason) {
            return DocumentDetailDto.builder()
                    .id(document.getId())
//                    .hwpUrl(document.getHwpUrl())
                    .wordUrl(document.getWordUrl())
                    .status(status)
                    .reason(reason)
                    .build();
        }
    }

    public static ReadOwnerDocumentSummaryResponseDto of(
            PartTimeEmploymentPermit partTimeEmploymentPermits,
            StandardLaborContract standardLaborContract,
            Reject partTimeEmploymentPermitReject,
            Reject standardLaborContractReject,
            Boolean isCompleted
    ) {
        return ReadOwnerDocumentSummaryResponseDto.builder()
                .partTimeEmploymentPermits(
                        partTimeEmploymentPermits != null ?
                                DocumentDetailDto.fromEntity(
                                        partTimeEmploymentPermits,
                                        partTimeEmploymentPermits.getEmployerStatus() != null ? partTimeEmploymentPermits.getEmployerStatus() : null,
                                        partTimeEmploymentPermitReject != null ? partTimeEmploymentPermitReject.getReason() : null)
                                : null)
                .standardLaborContract(
                        standardLaborContract != null ?
                                DocumentDetailDto.fromEntity(
                                        standardLaborContract,
                                        standardLaborContract.getEmployerStatus() != null ? standardLaborContract.getEmployerStatus() : null,
                                        standardLaborContractReject != null ? standardLaborContractReject.getReason() : null)
                                : null)
                .isCompleted(isCompleted)
                .build();
    }
}
