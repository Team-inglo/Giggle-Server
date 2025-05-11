package com.inglo.giggle.security.authenticationcode.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ValidateAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ValidateAuthenticationCodeResult;
import com.inglo.giggle.security.authenticationcode.application.port.in.usecase.ValidateAuthenticationCodeUseCase;
import com.inglo.giggle.security.authenticationcode.application.port.out.DeleteAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.application.port.out.LoadAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.domain.AuthenticationCode;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.DeleteAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.DeleteAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.temporarytoken.application.port.in.command.CreateTemporaryTokenCommand;
import com.inglo.giggle.security.temporarytoken.application.port.in.usecase.CreateTemporaryTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidateAuthenticationCodeService implements ValidateAuthenticationCodeUseCase {

    private final LoadAuthenticationCodePort loadAuthenticationCodePort;
    private final DeleteAuthenticationCodePort deleteAuthenticationCodePort;

    private final DeleteAuthenticationCodeHistoryUseCase deleteAuthenticationCodeHistoryUseCase;
    private final CreateTemporaryTokenUseCase createTemporaryTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public ValidateAuthenticationCodeResult execute(ValidateAuthenticationCodeCommand command) {

        // 해당 이메일로 발급된 인증코드 조회
        AuthenticationCode authenticationCode = loadAuthenticationCodePort.loadAuthenticationCode(command.getEmail());

        // 인증코드 일치 여부 확인
        if (!bCryptPasswordEncoder.matches(command.getAuthenticationCode(), authenticationCode.getValue())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // 인증코드 삭제
        deleteAuthenticationCodePort.deleteAuthenticationCode(authenticationCode);

        // 인증코드 이력 삭제
        DeleteAuthenticationCodeHistoryCommand deleteHistoryCommand = new DeleteAuthenticationCodeHistoryCommand(command.getEmail());
        deleteAuthenticationCodeHistoryUseCase.execute(deleteHistoryCommand);

        // 임시 토큰 생성
        ValidateAuthenticationCodeResult validateAuthenticationCodeResult = new ValidateAuthenticationCodeResult(jsonWebTokenUtil.generateTemporaryJsonWebToken(command.getEmail()).getTemporaryToken());

        // 임시 토큰 생성
        CreateTemporaryTokenCommand createTemporaryTokenCommand = new CreateTemporaryTokenCommand(
                command.getEmail(),
                validateAuthenticationCodeResult.getTemporaryToken()
        );
        createTemporaryTokenUseCase.execute(createTemporaryTokenCommand);

        return validateAuthenticationCodeResult;
    }
}
