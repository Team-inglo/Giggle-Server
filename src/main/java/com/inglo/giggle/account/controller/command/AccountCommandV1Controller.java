package com.inglo.giggle.account.controller.command;

import com.inglo.giggle.account.application.dto.request.UpdateNotificationAllowedRequestDto;
import com.inglo.giggle.account.application.usecase.UpdateNotificationAllowedUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountCommandV1Controller {

    private final UpdateNotificationAllowedUseCase updateNotificationAllowedUseCase;

    /**
     * 3.7 (유학생/고용주) 알람 설정 변경하기
     */
    @PatchMapping("/notification-allowed")
    public ResponseDto<Void> updateNotificationAllowed(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateNotificationAllowedRequestDto requestDto
    ) {
        updateNotificationAllowedUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }
}
