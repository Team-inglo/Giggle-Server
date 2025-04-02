package com.inglo.giggle.notification.presentation.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.notification.presentation.dto.ReadNotificationOverviewResponseDto;
import com.inglo.giggle.notification.application.usecase.UpdateNotificationIsReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class NotificationCommandV1Controller {


    private final UpdateNotificationIsReadUseCase updateNotificationIsReadUseCase;

    /**
     * 10.2(유학생/고용주) 알림 읽음 상태 변경
     */
    @PatchMapping("/notifications/{notification-id}/is-read")
    public ResponseDto<ReadNotificationOverviewResponseDto> updateNotificationIsRead(
            @AccountID UUID accountId,
            @PathVariable("notification-id") Long notificationId
    ) {

        updateNotificationIsReadUseCase.execute(
                accountId,
                notificationId
        );

        return ResponseDto.ok(null);
    }


}
