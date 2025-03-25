package com.inglo.giggle.account.controller.command;

import com.inglo.giggle.account.application.dto.request.UpdateUserLanguageRequestDto;
import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateUserLanguageUseCase;
import com.inglo.giggle.account.application.usecase.UpdateUserUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class AccountUsersCommandV1Controller {
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdateUserLanguageUseCase updateUserLanguageUseCase;

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

    /**
     * 3.8 (유학생) 앱 내 언어 수정
     */
    @PatchMapping(value = "/languages")
    public ResponseDto<Void> updateUserLanguage(
            @AccountID UUID accountId,
            @RequestBody UpdateUserLanguageRequestDto requestDto
    ) {
        updateUserLanguageUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }
}
