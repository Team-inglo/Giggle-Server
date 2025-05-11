package com.inglo.giggle.security.authenticationcode.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.security.authenticationcode.adapter.in.web.dto.ReissueAuthenticationCodeRequestDto;
import com.inglo.giggle.security.authenticationcode.adapter.in.web.dto.ValidateAuthenticationCodeRequestDto;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ReissueAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ValidateAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ReissueAuthenticationCodeResult;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ValidateAuthenticationCodeResult;
import com.inglo.giggle.security.authenticationcode.application.port.in.usecase.ReissueAuthenticationCodeUseCase;
import com.inglo.giggle.security.authenticationcode.application.port.in.usecase.ValidateAuthenticationCodeUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Hidden
public class AuthenticationCodeCommandV1Controller {

    private final ValidateAuthenticationCodeUseCase validateAuthenticationCodeUseCase;
    private final ReissueAuthenticationCodeUseCase reissueAuthenticationCodeUseCase;

    /**
     * 2.7 이메일 인증 코드 검증
     */
    @PatchMapping("/validations/authentication-code")
    public ResponseDto<ValidateAuthenticationCodeResult> validateAuthenticationCode(
            @RequestBody ValidateAuthenticationCodeRequestDto requestDto
    ) {

        ValidateAuthenticationCodeCommand command = new ValidateAuthenticationCodeCommand(
                requestDto.email(),
                requestDto.authenticationCode()
        );
        return ResponseDto.created(validateAuthenticationCodeUseCase.execute(command));
    }

    /**
     * 2.8 인증 코드 발급(재발급)
     */
    @PatchMapping("/reissue/authentication-code")
    public ResponseDto<ReissueAuthenticationCodeResult> reissueAuthenticationCode(
            @RequestBody ReissueAuthenticationCodeRequestDto requestDto
    ) {

        ReissueAuthenticationCodeCommand command = new ReissueAuthenticationCodeCommand(requestDto.email());

        return ResponseDto.created(reissueAuthenticationCodeUseCase.execute(command));
    }
}
