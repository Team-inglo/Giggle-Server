package com.inglo.giggle.document.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.request.*;
import com.inglo.giggle.document.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class DocumentUsersCommandV1Controller {

    private final ConfirmUserDocumentUseCase confirmUserDocumentUseCase;
    private final CreateUserPartTimeEmploymentPermitUseCase createUserPartTimeEmploymentPermitUseCase;
    private final CreateUserStandardLaborContractUseCase createUserStandardLaborContractUseCase;
    private final CreateUserIntegratedApplicationUseCase createUserIntegratedApplicationUseCase;
    private final UpdateDocumentStatusRequestionUseCase updateDocumentStatusRequestionUseCase;
    private final UpdateUserPartTimeEmploymentPermitUseCase updateUserPartTimeEmploymentPermitUseCase;
    private final UpdateUserStandardLaborContractUseCase updateUserStandardLaborContractUseCase;
    private final UpdateUserIntegratedApplicationUseCase updateUserIntegratedApplicationUseCase;
    private final UpdateUserDocumentStatusSubmissionUseCase updateUserDocumentStatusSubmissionUseCase;

    /**
     * 8.6 (유학생) 시간제 취업허가서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/part-time-employment-permits")
    public ResponseDto<Void> createUserPartTimeEmploymentPermit(
            @PathVariable Long id,
            @RequestBody @Valid CreateUserPartTimeEmploymentPermitRequestDto requestDto
    ) {
        createUserPartTimeEmploymentPermitUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.7 (유학생) 근로계약서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/standard-labor-contracts")
    public ResponseDto<Void> createUserStandardLaborContract(
            @PathVariable Long id,
            @RequestBody @Valid CreateUserStandardLaborContractRequestDto requestDto
    ) {
        createUserStandardLaborContractUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.8 (유학생) 통합신청서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/integrated-applications")
    public ResponseDto<Void> createUserIntegratedApplication(
            @PathVariable Long id,
            @RequestBody @Valid CreateUserIntegratedApplicationRequestDto requestDto
    ) {
       createUserIntegratedApplicationUseCase.execute(id, requestDto);
         return ResponseDto.ok(null);
    }

    /**
     * 8.9 (유학생) 서류 재검토 요청하기
     */
    @PostMapping("/documents/{id}/status/requestion")
    public ResponseDto<Void> requestUserDocument(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDocumentStatusReqeustionRequestDto requestDto
    ) {
        updateDocumentStatusRequestionUseCase.updateDocumentStatusRequestion(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.10 (유학생) 시간제 취업허가서 수정하기
     */
    @PutMapping("/documents/{id}/part-time-employment-permits")
    public ResponseDto<Void> updateUserPartTimeEmploymentPermit(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserPartTimeEmploymentPermitRequestDto requestDto
    ) {
        updateUserPartTimeEmploymentPermitUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.12 (유학생) 근로계약서 수정하기
     */
    @PutMapping("/documents/{id}/standard-labor-contracts")
    public ResponseDto<Void> updateUserStandardLaborContract(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserStandardLaborContractRequestDto requestDto
    ) {
        updateUserStandardLaborContractUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.14 (유학생) 통합신청서 수정하기
     */
    @PutMapping("/documents/{id}/integrated-applications")
    public ResponseDto<Void> updateUserIntegratedApplication(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserIntegratedApplicationRequestDto requestDto
    ) {
        updateUserIntegratedApplicationUseCase.execute(id, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 8.15 (유학생) 서류 제출하기
     */
    @PatchMapping("/documents/{id}/status/submission")
    public ResponseDto<Void> updateUserDocumentStatusSubmission(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        updateUserDocumentStatusSubmissionUseCase.execute(accountId, id);
        return ResponseDto.ok(null);
    }

    /**
     * 8.17 (유학생) 서류 컨펌하기
     */
    @PatchMapping("/documents/{id}/status/confirmation")
    public ResponseDto<Void> confirmUserDocument(
            @AccountID UUID accountId,
            @PathVariable Long id) {
        confirmUserDocumentUseCase.confirmUserDocument(accountId, id);
        return ResponseDto.ok(null);
    }
}
