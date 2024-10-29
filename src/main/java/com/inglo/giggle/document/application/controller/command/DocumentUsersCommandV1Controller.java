package com.inglo.giggle.document.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.ConfirmUserDocumentUseCase;
import com.inglo.giggle.document.application.usecase.CreateUserPartTimeEmploymentPermitUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class DocumentUsersCommandV1Controller {

    private final ConfirmUserDocumentUseCase confirmUserDocumentUseCase;
    private final CreateUserPartTimeEmploymentPermitUseCase createUserPartTimeEmploymentPermitUseCase;

    /**
     * 8.6 (유학생) 시간제 취업허가서 생성하기
     */
    @PostMapping("/user-owner-job-postings/{id}/documents/part-time-employment-permits")
    public ResponseDto<Void> createUserPartTimeEmploymentPermit(
            @PathVariable Long id,
            @RequestBody CreateUserPartTimeEmploymentPermitRequestDto requestDto
    ) {
        createUserPartTimeEmploymentPermitUseCase.execute(id, requestDto);
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
