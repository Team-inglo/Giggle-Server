package com.inglo.giggle.document.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.port.in.query.ReadIntegratedApplicationDetailQuery;
import com.inglo.giggle.document.application.port.in.query.ReadPartTimeEmploymentPermitDetailQuery;
import com.inglo.giggle.document.application.port.in.query.ReadStandardLaborContractDetailQuery;
import com.inglo.giggle.document.application.port.in.result.ReadIntegratedApplicationDetailResult;
import com.inglo.giggle.document.application.port.in.result.ReadPartTimeEmploymentPermitDetailResult;
import com.inglo.giggle.document.application.port.in.result.ReadStandardLaborContractDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documents")
public class DocumentQueryV1Controller {
    private final ReadPartTimeEmploymentPermitDetailQuery readPartTimeEmploymentPermitDetailQuery;
    private final ReadStandardLaborContractDetailQuery readStandardLaborContractDetailQuery;
    private final ReadIntegratedApplicationDetailQuery readIntegratedApplicationDetailQuery;

    /**
     * 8.3 (유학생/고용주) 시간제 취업 허가서 조회하기
     */
    @GetMapping("{id}/part-time-employment-permit/details")
    public ResponseDto<ReadPartTimeEmploymentPermitDetailResult> readDocumentPartTimeEmploymentPermitDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readPartTimeEmploymentPermitDetailQuery.execute(accountId, id));
    }

    /**
     * 8.4 (유학생/고용주) 근로계약서 조회하기
     */
    @GetMapping("{id}/standard-labor-contract/details")
    public ResponseDto<ReadStandardLaborContractDetailResult> readDocumentStandardLaborContractDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readStandardLaborContractDetailQuery.execute(accountId, id));
    }

    /**
     * 8.5 (유학생/고용주) 통합신청서 조회하기
     */
    @GetMapping("{id}/integrated-application/details")
    public ResponseDto<ReadIntegratedApplicationDetailResult> readDocumentIntegratedApplicationDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readIntegratedApplicationDetailQuery.execute(accountId, id));
    }
}
