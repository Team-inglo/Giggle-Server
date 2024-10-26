package com.inglo.giggle.account.application.controller.command;

import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateUserUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class AccountUsersCommandV1Controller {
    private final UpdateUserUseCase updateUserUseCase;

    /**
     * 3.5 (유학생) 프로필 수정
     */
    @PatchMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateUser(
            @AccountID UUID accountId,
            @RequestPart(value = "image", required = false)MultipartFile image,
            @RequestPart(value = "body") UpdateUserRequestDto requestDto
            ) {
        updateUserUseCase.execute(accountId, requestDto, image);
        return ResponseDto.ok(null);
    }
}
