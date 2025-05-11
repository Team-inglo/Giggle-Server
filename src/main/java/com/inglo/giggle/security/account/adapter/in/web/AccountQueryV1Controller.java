package com.inglo.giggle.security.account.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.security.account.adapter.in.web.dto.ValidatePasswordRequestDto;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountBriefQuery;
import com.inglo.giggle.security.account.application.port.in.query.ValidateEmailQuery;
import com.inglo.giggle.security.account.application.port.in.query.ValidatePasswordQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountBriefResult;
import com.inglo.giggle.security.account.application.port.in.result.ValidationResult;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Hidden
public class AccountQueryV1Controller {

    private final ValidateEmailQuery validateEmailQuery;
    private final ReadAccountBriefQuery readAccountBriefQuery;
    private final ValidatePasswordQuery validatePasswordQuery;

    /**
     * 2.2 이메일 중복 검사
     */
    @GetMapping("/validations/email")
    public ResponseDto<ValidationResult> validateEmail(
            @RequestParam(name = "email") String email
    ) {
        return ResponseDto.ok(validateEmailQuery.execute(email));
    }

    /**
     * 2.3 유학생 or 고용주 간단 정보 조회(판단)
     */
    @GetMapping("/briefs")
    public ResponseDto<ReadAccountBriefResult> readUserBriefInfo(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readAccountBriefQuery.execute(accountId));
    }

    /**
     * 2.12 현재 비밀번호 확인
     */
    @PostMapping("/validations/password")
    public ResponseDto<?> validatePassword(
            @RequestBody @Valid ValidatePasswordRequestDto requestDto,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(validatePasswordQuery.execute(accountId, requestDto));
    }
}
