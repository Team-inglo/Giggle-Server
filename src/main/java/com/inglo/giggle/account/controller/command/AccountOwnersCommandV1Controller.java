package com.inglo.giggle.account.controller.command;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateOwnerUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class AccountOwnersCommandV1Controller {
    private final UpdateOwnerUseCase updateOwnerUseCase;

    /**
     * 3.6 (고용주) 회사 정보 수정하기
     */
    @PatchMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateOwner(
            @AccountID UUID accountId,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "body") UpdateOwnerRequestDto requestDto
            ) {
        updateOwnerUseCase.execute(accountId, requestDto, image);
        return ResponseDto.ok(null);
    }
}
