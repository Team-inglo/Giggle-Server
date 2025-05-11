package com.inglo.giggle.user.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.user.adapter.in.web.dto.UpdateUserSelfLanguageRequestDto;
import com.inglo.giggle.user.adapter.in.web.dto.UpdateUserSelfNotificationAllowedRequestDto;
import com.inglo.giggle.user.adapter.in.web.dto.UpdateUserSelfRequestDto;
import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfCommand;
import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfLanguageCommand;
import com.inglo.giggle.user.application.port.in.command.UpdateUserSelfNotificationAllowedCommand;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfLanguageUseCase;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfNotificationAllowedUseCase;
import com.inglo.giggle.user.application.port.in.usecase.UpdateUserSelfUseCase;
import jakarta.validation.Valid;
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
public class UserCommandV1Controller {

    private final UpdateUserSelfUseCase updateUserSelfUseCase;
    private final UpdateUserSelfNotificationAllowedUseCase updateUserNotificationAllowedUseCase;
    private final UpdateUserSelfLanguageUseCase updateUserSelfLanguageUseCase;

    /**
     * 3.5 (유학생) 프로필 수정
     */
    @PatchMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateUser(
            @AccountID UUID accountId,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "body") UpdateUserSelfRequestDto requestDto
    ) {

        UpdateUserSelfCommand command = new UpdateUserSelfCommand(
                accountId,
                image,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.birth(),
                requestDto.gender(),
                requestDto.nationality(),
                requestDto.visa(),
                requestDto.phoneNumber(),
                requestDto.isProfileImgChanged(),
                requestDto.address()
        );
        updateUserSelfUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 3.7 (유학생/고용주) 알람 설정 변경하기
     */
    @PatchMapping("/notification-allowed")
    public ResponseDto<Void> updateNotificationAllowed(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserSelfNotificationAllowedRequestDto requestDto
    ) {
        UpdateUserSelfNotificationAllowedCommand command = new UpdateUserSelfNotificationAllowedCommand(accountId, requestDto.isNotificationAllowed());
        updateUserNotificationAllowedUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 3.8 (유학생) 앱 내 언어 수정
     */
    @PatchMapping(value = "/languages")
    public ResponseDto<Void> updateUserLanguage(
            @AccountID UUID accountId,
            @RequestBody UpdateUserSelfLanguageRequestDto requestDto
    ) {
        UpdateUserSelfLanguageCommand command = new UpdateUserSelfLanguageCommand(
                accountId,
                requestDto.language()
        );
        updateUserSelfLanguageUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}

