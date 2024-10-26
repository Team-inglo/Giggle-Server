package com.inglo.giggle.account.application.controller.query;

import com.inglo.giggle.account.application.dto.response.ReadUserDetailResponseDto;
import com.inglo.giggle.account.application.usecase.ReadUserDetailUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class AccountUsersQueryV1Controller {
    private final ReadUserDetailUseCase readUserDetailUseCase;

    /**
     * 3.1 (유학생) 유저 프로필 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserDetailResponseDto> readUserDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserDetailUseCase.execute(accountId));
    }
}
