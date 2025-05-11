package com.inglo.giggle.notification.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.notification.application.port.in.command.UpdateNotificationIsReadCommand;
import com.inglo.giggle.notification.application.port.in.result.ReadNotificationOverviewResponseDto;
import com.inglo.giggle.notification.application.port.in.usecase.UpdateNotificationIsReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @PathVariable("notification-id") Long notificationId
    ) {
        UpdateNotificationIsReadCommand command = new UpdateNotificationIsReadCommand(
                notificationId
        );
        updateNotificationIsReadUseCase.execute(command);

        return ResponseDto.ok(null);
    }


}
