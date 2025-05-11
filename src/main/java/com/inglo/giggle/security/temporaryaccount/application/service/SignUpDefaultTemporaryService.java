package com.inglo.giggle.security.temporaryaccount.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.application.port.in.query.ValidateEmailQuery;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ReissueAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ReissueAuthenticationCodeResult;
import com.inglo.giggle.security.authenticationcode.application.port.in.usecase.ReissueAuthenticationCodeUseCase;
import com.inglo.giggle.security.temporaryaccount.application.port.in.command.SignUpDefaultTemporaryCommand;
import com.inglo.giggle.security.temporaryaccount.application.port.in.result.SignUpDefaultTemporaryResult;
import com.inglo.giggle.security.temporaryaccount.application.port.in.usecase.SignUpDefaultTemporaryUseCase;
import com.inglo.giggle.security.temporaryaccount.application.port.out.CreateTemporaryAccountPort;
import com.inglo.giggle.security.temporaryaccount.domain.TemporaryAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpDefaultTemporaryService implements SignUpDefaultTemporaryUseCase {

    private final CreateTemporaryAccountPort createTemporaryAccountPort;

    private final ValidateEmailQuery validateEmailQuery;
    private final ReissueAuthenticationCodeUseCase reissueAuthenticationCodeUseCase;

    @Override
    @Transactional
    public SignUpDefaultTemporaryResult execute(SignUpDefaultTemporaryCommand command) {

        // 중복된 이메일인지 확인
        if (!validateEmailQuery.execute(command.getEmail()).getIsValid()) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        // 임시 계정 생성
        TemporaryAccount temporaryAccount = new TemporaryAccount(
                command.getEmail(),
                command.getPassword(),
                ESecurityRole.fromString(command.getAccountType())
        );
        createTemporaryAccountPort.createTemporaryAccount(temporaryAccount);


        // 인증코드 재발급
        ReissueAuthenticationCodeCommand reissueAuthenticationCodeCommand = new ReissueAuthenticationCodeCommand(
                command.getEmail()
        );

        ReissueAuthenticationCodeResult result = reissueAuthenticationCodeUseCase.execute(reissueAuthenticationCodeCommand);

        return SignUpDefaultTemporaryResult.of(result.getTryCnt());
    }
}
