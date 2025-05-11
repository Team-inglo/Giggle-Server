package com.inglo.giggle.document.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.adapter.in.web.dto.CreateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.CreateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.CreateUserStandardLaborContractRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateDocumentStatusRequestRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateUserIntegratedApplicationRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateUserStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.port.in.command.ConfirmUserDocumentCommand;
import com.inglo.giggle.document.application.port.in.command.CreateUserIntegratedApplicationCommand;
import com.inglo.giggle.document.application.port.in.command.CreateUserPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.command.CreateUserStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateDocumentStatusRequestCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateUserDocumentStatusSubmissionCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateUserIntegratedApplicationCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateUserPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateUserStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.in.usecase.CreateUserStandardLaborContractUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserDocumentStatusRequestUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserIntegratedApplicationUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateUserStandardLaborContractUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class DocumentUsersCommandV1Controller {

    private final ConfirmUserDocumentUseCase confirmUserDocumentUseCase;
    private final CreateUserPartTimeEmploymentPermitUseCase createUserPartTimeEmploymentPermitUseCase;
    private final CreateUserStandardLaborContractUseCase createUserStandardLaborContractUseCase;
    private final CreateUserIntegratedApplicationUseCase createUserIntegratedApplicationUseCase;
    private final UpdateUserDocumentStatusRequestUseCase updateUserDocumentStatusRequestUseCase;
    private final UpdateUserPartTimeEmploymentPermitUseCase updateUserPartTimeEmploymentPermitUseCase;
    private final UpdateUserStandardLaborContractUseCase updateUserStandardLaborContractUseCase;
    private final UpdateUserIntegratedApplicationUseCase updateUserIntegratedApplicationUseCase;
    private final UpdateUserDocumentStatusSubmissionUseCase updateUserDocumentStatusSubmissionUseCase;

    /**
     * 8.6 (유학생) 시간제 취업허가서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/part-time-employment-permits")
    public ResponseDto<Void> createUserPartTimeEmploymentPermit(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateUserPartTimeEmploymentPermitRequestDto requestDto
    ) {
        CreateUserPartTimeEmploymentPermitCommand command = new CreateUserPartTimeEmploymentPermitCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );
        createUserPartTimeEmploymentPermitUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.7 (유학생) 근로계약서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/standard-labor-contracts")
    public ResponseDto<Void> createUserStandardLaborContract(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateUserStandardLaborContractRequestDto requestDto
    ) {
        CreateUserStandardLaborContractCommand command = new CreateUserStandardLaborContractCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.address(),
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );
        createUserStandardLaborContractUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.8 (유학생) 통합신청서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/integrated-applications")
    public ResponseDto<Void> createUserIntegratedApplication(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateUserIntegratedApplicationRequestDto requestDto
    ) {

        CreateUserIntegratedApplicationCommand command = new CreateUserIntegratedApplicationCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.birth(),
                requestDto.gender(),
                requestDto.nationality(),
                requestDto.telePhoneNumber(),
                requestDto.cellPhoneNumber(),
                requestDto.isAccredited(),
                requestDto.schoolName(),
                requestDto.schoolPhoneNumber(),
                requestDto.newWorkPlaceName(),
                requestDto.newWorkPlaceRegistrationNumber(),
                requestDto.newWorkPlacePhoneNumber(),
                requestDto.annualIncomeAmount(),
                requestDto.occupation(),
                requestDto.email(),
                requestDto.signatureBase64(),
                requestDto.address()
        );
       createUserIntegratedApplicationUseCase.execute(command);
         return ResponseDto.ok(null);
    }

    /**
     * 8.9 (유학생) 서류 재검토 요청하기
     */
    @PostMapping("/documents/{id}/status/requestion")
    public ResponseDto<Void> requestUserDocument(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateDocumentStatusRequestRequestDto requestDto
    ) {
        UpdateDocumentStatusRequestCommand command = new UpdateDocumentStatusRequestCommand(
                accountId,
                id,
                requestDto.reason()
        );
        updateUserDocumentStatusRequestUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.10 (유학생) 시간제 취업허가서 수정하기
     */
    @PutMapping("/documents/{id}/part-time-employment-permits")
    public ResponseDto<Void> updateUserPartTimeEmploymentPermit(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserPartTimeEmploymentPermitRequestDto requestDto
    ) {
        UpdateUserPartTimeEmploymentPermitCommand command = new UpdateUserPartTimeEmploymentPermitCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );
        updateUserPartTimeEmploymentPermitUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.12 (유학생) 근로계약서 수정하기
     */
    @PutMapping("/documents/{id}/standard-labor-contracts")
    public ResponseDto<Void> updateUserStandardLaborContract(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserStandardLaborContractRequestDto requestDto
    ) {
        UpdateUserStandardLaborContractCommand command = new UpdateUserStandardLaborContractCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.address(),
                requestDto.phoneNumber(),
                requestDto.signatureBase64()
        );
        updateUserStandardLaborContractUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.14 (유학생) 통합신청서 수정하기
     */
    @PutMapping("/documents/{id}/integrated-applications")
    public ResponseDto<Void> updateUserIntegratedApplication(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserIntegratedApplicationRequestDto requestDto
    ) {
        UpdateUserIntegratedApplicationCommand command = new UpdateUserIntegratedApplicationCommand(
                accountId,
                id,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.birth(),
                EGender.fromString(requestDto.gender()),
                requestDto.nationality(),
                requestDto.telePhoneNumber(),
                requestDto.cellPhoneNumber(),
                requestDto.isAccredited(),
                requestDto.schoolName(),
                requestDto.schoolPhoneNumber(),
                requestDto.newWorkPlaceName(),
                requestDto.newWorkPlaceRegistrationNumber(),
                requestDto.newWorkPlacePhoneNumber(),
                requestDto.annualIncomeAmount(),
                requestDto.occupation(),
                requestDto.email(),
                requestDto.signatureBase64(),
                requestDto.address()
        );
        updateUserIntegratedApplicationUseCase.execute(command);
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
        UpdateUserDocumentStatusSubmissionCommand command = new UpdateUserDocumentStatusSubmissionCommand(
                accountId,
                id
        );
        updateUserDocumentStatusSubmissionUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.17 (유학생) 서류 컨펌하기
     */
    @PatchMapping("/documents/{id}/status/confirmation")
    public ResponseDto<Void> confirmUserDocument(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        ConfirmUserDocumentCommand command = new ConfirmUserDocumentCommand(
                accountId,
                id
        );
        confirmUserDocumentUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
