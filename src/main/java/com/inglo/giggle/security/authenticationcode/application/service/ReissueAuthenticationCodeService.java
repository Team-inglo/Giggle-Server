package com.inglo.giggle.security.authenticationcode.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.authenticationcode.application.port.in.command.ReissueAuthenticationCodeCommand;
import com.inglo.giggle.security.authenticationcode.application.port.in.result.ReissueAuthenticationCodeResult;
import com.inglo.giggle.security.authenticationcode.application.port.in.usecase.ReissueAuthenticationCodeUseCase;
import com.inglo.giggle.security.authenticationcode.application.port.out.CreateAuthenticationCodePort;
import com.inglo.giggle.security.authenticationcode.domain.AuthenticationCode;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.CreateAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.UpdateAuthenticationCodeHistoryCommand;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.query.ReadAuthenticationCodeHistoryQuery;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.result.ReadAuthenticationCodeHistoryResult;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.CreateAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase.UpdateAuthenticationCodeHistoryUseCase;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReissueAuthenticationCodeService implements ReissueAuthenticationCodeUseCase {

    private final CreateAuthenticationCodePort createAuthenticationCodePort;

    private final ReadAuthenticationCodeHistoryQuery readAuthenticationCodeHistoryQuery;
    private final CreateAuthenticationCodeHistoryUseCase createAuthenticationCodeHistoryUseCase;
    private final UpdateAuthenticationCodeHistoryUseCase updateAuthenticationCodeHistoryUseCase;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    @Transactional
    public ReissueAuthenticationCodeResult execute(ReissueAuthenticationCodeCommand command) {

        // 인증코드 발급 이력 조회
        ReadAuthenticationCodeHistoryResult readAuthenticationCodeHistoryResult = readAuthenticationCodeHistoryQuery.execute(command.getEmail());

        // 인증코드 발급 제한, 발급 속도 제한 유효성 검사
        isBlockedIssuingAuthenticationCode(readAuthenticationCodeHistoryResult);
        isTooFastIssuingAuthenticationCode(readAuthenticationCodeHistoryResult);

        // 새로운 인증코드 생성
        String code = PasswordUtil.generateAuthCode(6);

        // 새로운 인증코드 저장
        AuthenticationCode authenticationCode = AuthenticationCode.builder()
                .email(command.getEmail())
                .value(bCryptPasswordEncoder.encode(code))
                .build();

        createAuthenticationCodePort.createAuthenticationCode(authenticationCode);

        // 인증코드 발급 이력 업데이트
        if (readAuthenticationCodeHistoryResult.getCount() == 0) {
            CreateAuthenticationCodeHistoryCommand createAuthenticationCodeHistoryCommand = new CreateAuthenticationCodeHistoryCommand(
                    command.getEmail()
            );
            createAuthenticationCodeHistoryUseCase.execute(createAuthenticationCodeHistoryCommand);
        } else {
            UpdateAuthenticationCodeHistoryCommand updateAuthenticationCodeHistoryCommand = new UpdateAuthenticationCodeHistoryCommand(
                    command.getEmail()
            );
            updateAuthenticationCodeHistoryUseCase.execute(updateAuthenticationCodeHistoryCommand);
        }

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(CompleteEmailValidationEvent.of(command.getEmail(), code));

        return ReissueAuthenticationCodeResult.fromEntity(readAuthenticationCodeHistoryResult.getCount() + 1);
    }

    // ----------------------------------- private methods ----------------------------------- //
    private void isBlockedIssuingAuthenticationCode(ReadAuthenticationCodeHistoryResult history) {
        if (history == null) {
            return;
        }
        if (history.getCount() >= 5) {
            throw new CommonException(ErrorCode.TOO_MANY_AUTHENTICATION_CODE_REQUESTS);
        }
    }

    private void isTooFastIssuingAuthenticationCode(ReadAuthenticationCodeHistoryResult history) {
        if (history == null) {
            return;
        }
        if (history.getLastSentAt().isAfter(LocalDateTime.now().minusSeconds(10))) {
            throw new CommonException(ErrorCode.TOO_FAST_AUTHENTICATION_CODE_REQUESTS);
        }
    }
}
