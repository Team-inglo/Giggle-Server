package com.inglo.giggle.document.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.usecase.ConfirmUserDocumentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentUsersCommandV1Controller {

    private final ConfirmUserDocumentUseCase confirmUserDocumentUseCase;

    @PatchMapping("/v1/users/documents/{id}/status/confirmation")
    public ResponseDto<Void> confirmUserDocument(
            @AccountID UUID accountId,
            @PathVariable Long id) {
        confirmUserDocumentUseCase.confirmUserDocument(accountId, id);
        return ResponseDto.ok(null);
    }
}
