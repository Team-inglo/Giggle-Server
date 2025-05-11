package com.inglo.giggle.notification.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.notification.application.port.in.query.ReadNotificationOverviewUseCase;
import com.inglo.giggle.notification.application.port.in.result.ReadNotificationOverviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class NotificationQueryV1Controller {

    private final ReadNotificationOverviewUseCase readNotificationOverviewUseCase;

    /**
     * 10.1(유학생/고용주) 알림 조회
     */
    @GetMapping("/notifications/overviews")
    public ResponseDto<ReadNotificationOverviewResponseDto> readNotificationOverview(
            @AccountID UUID accountId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseDto.ok(readNotificationOverviewUseCase.execute(
                accountId,
                page,
                size)
        );
    }


}
