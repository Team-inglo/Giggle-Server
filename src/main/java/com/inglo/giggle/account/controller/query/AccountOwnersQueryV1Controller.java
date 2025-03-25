package com.inglo.giggle.account.controller.query;

import com.inglo.giggle.account.application.dto.response.ReadOwnerBriefResponseDto;
import com.inglo.giggle.account.application.dto.response.ReadOwnerDetailResponseDto;
import com.inglo.giggle.account.application.usecase.ReadOwnerBriefUseCase;
import com.inglo.giggle.account.application.usecase.ReadOwnerDetailUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class AccountOwnersQueryV1Controller {
    private final ReadOwnerDetailUseCase readOwnerDetailUseCase;
    private final ReadOwnerBriefUseCase readOwnerBriefUseCase;

    /**
     * 3.2 (고용주) 회사 정보 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadOwnerDetailResponseDto> readOwnerDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOwnerDetailUseCase.execute(accountId));
    }

    /**
     * 3.4 (고용주) 고용주 간단 정보 조회하기
     */
    @GetMapping("/briefs")
    public ResponseDto<ReadOwnerBriefResponseDto> readOwnerBrief(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOwnerBriefUseCase.execute(accountId));
    }
}
