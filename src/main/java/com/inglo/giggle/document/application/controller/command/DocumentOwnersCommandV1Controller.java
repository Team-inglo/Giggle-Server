package com.inglo.giggle.document.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.application.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.usecase.UpdateOwnerStandardLaborContractUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class DocumentOwnersCommandV1Controller {

    private final UpdateOwnerPartTimeEmploymentPermitUseCase updateOwnerPartTimeEmploymentPermitUseCase;
    private final UpdateOwnerStandardLaborContractUseCase updateOwnerStandardLaborContractUseCase;
    private final UpdateOwnerDocumentStatusSubmissionUseCase updateOwnerDocumentStatusSubmissionUseCase;
    /**
     * 8.11 (고용주) 시간제 취업허가서 수정하기
     */
    @PutMapping("/documents/{id}/part-time-employment-permits")
    public ResponseDto<Void> updateOwnerPartTimeEmploymentPermit(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto
    ) {
        updateOwnerPartTimeEmploymentPermitUseCase.execute(accountId, id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.12 (고용주) 근로계약서 수정하기
     */
    @PutMapping("/documents/{id}/standard-labor-contracts")
    public ResponseDto<Void> updateOwnerStandardLaborContract(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateOwnerStandardLaborContractRequestDto requestDto
    ) {
        updateOwnerStandardLaborContractUseCase.execute(accountId, id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.16 (고용주) 서류 제출하기
     */
    @PatchMapping("/documents/{id}/status/submission")
    public ResponseDto<Void> updateOwnerDocumentStatusSubmission(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        updateOwnerDocumentStatusSubmissionUseCase.execute(accountId, id);
        return ResponseDto.ok(null);
    }
}
