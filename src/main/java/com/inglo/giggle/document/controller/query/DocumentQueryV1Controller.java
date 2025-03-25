package com.inglo.giggle.document.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadIntegratedApplicationDetailResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadStandardLaborContractDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadIntegratedApplicationDetailUseCase;
import com.inglo.giggle.document.application.usecase.ReadPartTimeEmploymentPermitDetailUseCase;
import com.inglo.giggle.document.application.usecase.ReadStandardLaborContractDetailUseCase;
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
    private final ReadPartTimeEmploymentPermitDetailUseCase readPartTimeEmploymentPermitDetailUseCase;
    private final ReadStandardLaborContractDetailUseCase readStandardLaborContractDetailUseCase;
    private final ReadIntegratedApplicationDetailUseCase readIntegratedApplicationDetailUseCase;

    /**
     * 8.3 (유학생/고용주) 시간제 취업 허가서 조회하기
     */
    @GetMapping("{id}/part-time-employment-permit/details")
    public ResponseDto<ReadPartTimeEmploymentPermitDetailResponseDto> readDocumentPartTimeEmploymentPermitDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readPartTimeEmploymentPermitDetailUseCase.execute(accountId, id));
    }

    /**
     * 8.4 (유학생/고용주) 근로계약서 조회하기
     */
    @GetMapping("{id}/standard-labor-contract/details")
    public ResponseDto<ReadStandardLaborContractDetailResponseDto> readDocumentStandardLaborContractDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readStandardLaborContractDetailUseCase.execute(accountId, id));
    }

    /**
     * 8.5 (유학생/고용주) 통합신청서 조회하기
     */
    @GetMapping("{id}/integrated-application/details")
    public ResponseDto<ReadIntegratedApplicationDetailResponseDto> readDocumentIntegratedApplicationDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readIntegratedApplicationDetailUseCase.execute(accountId, id));
    }
}
