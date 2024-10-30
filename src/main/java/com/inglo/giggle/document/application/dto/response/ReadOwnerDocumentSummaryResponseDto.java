package com.inglo.giggle.document.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerDocumentSummaryResponseDto extends SelfValidating<ReadOwnerDocumentSummaryResponseDto> {

    @JsonProperty("part_time_employment_permits")
    private final DocumentDetailDto partTimeEmploymentPermits;

    @JsonProperty("standard_labor_contract")
    private final DocumentDetailDto standardLaborContract;

    @Builder
    public ReadOwnerDocumentSummaryResponseDto(
            DocumentDetailDto partTimeEmploymentPermits,
            DocumentDetailDto standardLaborContract
    ) {
        this.partTimeEmploymentPermits = partTimeEmploymentPermits;
        this.standardLaborContract = standardLaborContract;

        this.validateSelf();
    }

    @Getter
    public static class DocumentDetailDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("pdf_url")
        private final String pdfUrl;

        @JsonProperty("hwp_url")
        private final String hwpUrl;

        @JsonProperty("word_url")
        private final String wordUrl;

        @JsonProperty("status")
        private final String status;

        @Builder
        public DocumentDetailDto(Long id, String pdfUrl, String hwpUrl, String wordUrl, EEmployeeStatus status) {
            this.id = id;
            this.pdfUrl = pdfUrl;
            this.hwpUrl = hwpUrl;
            this.wordUrl = wordUrl;
            this.status = status != null ? status.getEnName() : null;
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

    public static ReadOwnerDocumentSummaryResponseDto of(
            PartTimeEmploymentPermit partTimeEmploymentPermits,
            StandardLaborContract standardLaborContract
    ) {
        return ReadOwnerDocumentSummaryResponseDto.builder()
                .partTimeEmploymentPermits(partTimeEmploymentPermits != null ? DocumentDetailDto.fromEntity(partTimeEmploymentPermits, partTimeEmploymentPermits.getEmployeeStatus()) : null)
                .standardLaborContract(standardLaborContract != null ? DocumentDetailDto.fromEntity(standardLaborContract, standardLaborContract.getEmployeeStatus()) : null)
                .build();
    }
}
