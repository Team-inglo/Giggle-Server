package com.inglo.giggle.security.temporaryaccount.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.security.temporaryaccount.adapter.in.web.dto.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.temporaryaccount.application.port.in.command.SignUpDefaultTemporaryCommand;
import com.inglo.giggle.security.temporaryaccount.application.port.in.result.SignUpDefaultTemporaryResult;
import com.inglo.giggle.security.temporaryaccount.application.port.in.usecase.SignUpDefaultTemporaryUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Hidden
public class TemporaryAccountCommandV1Controller {

    private final SignUpDefaultTemporaryUseCase signUpDefaultTemporaryUseCase;

    /**
     * 2.4 기본 임시 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseDto<SignUpDefaultTemporaryResult> signUpDefaultTemporary(
            @RequestBody @Valid SignUpDefaultTemporaryRequestDto requestDto
    ) {
        SignUpDefaultTemporaryCommand command = new SignUpDefaultTemporaryCommand(
                requestDto.password(),
                requestDto.email(),
                requestDto.accountType()
        );
        return ResponseDto.created(signUpDefaultTemporaryUseCase.execute(command));
    }
}
