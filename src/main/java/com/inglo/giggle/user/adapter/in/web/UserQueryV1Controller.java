package com.inglo.giggle.user.adapter.in.web;

import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.user.application.port.in.query.ReadUserSelfDetailQuery;
import com.inglo.giggle.user.application.port.in.query.ReadUserSelfSummaryQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfDetailResult;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfSummaryResult;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserQueryV1Controller {

    private final ReadUserSelfDetailQuery readUserSelfDetailQuery;
    private final ReadUserSelfSummaryQuery readUserSelfSummaryQuery;

    /**
     * 3.1 (유학생) 유저 프로필 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserSelfDetailResult> readUserDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserSelfDetailQuery.execute(accountId));
    }

    /**
     * 3.3 (유학생) 유저 요약 정보 조회하기
     */
    @GetMapping("/summaries")
    public ResponseDto<ReadUserSelfSummaryResult> readUserSummary(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserSelfSummaryQuery.execute(principal, accountId));
    }
}
