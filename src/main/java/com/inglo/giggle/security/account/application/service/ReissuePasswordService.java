package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.account.application.port.in.command.ReissuePasswordCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.ReissuePasswordUseCase;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.event.ChangePasswordBySystemEvent;
import com.inglo.giggle.security.temporarytoken.application.port.in.command.DeleteTemporaryTokenCommand;
import com.inglo.giggle.security.temporarytoken.application.port.in.query.ReadTemporaryTokenQuery;
import com.inglo.giggle.security.temporarytoken.application.port.in.result.ReadTemporaryTokenResult;
import com.inglo.giggle.security.temporarytoken.application.port.in.usecase.DeleteTemporaryTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissuePasswordService implements ReissuePasswordUseCase {

    private final LoadAccountPort loadAccountPort;

    private final ReadTemporaryTokenQuery readTemporaryTokenQuery;
    private final DeleteTemporaryTokenUseCase deleteTemporaryTokenUseCase;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(ReissuePasswordCommand command) {
        // temporary Token 검증. Redis 에 있는 토큰인지 확인 -> email 추출
        ReadTemporaryTokenResult readTemporaryTokenResult = readTemporaryTokenQuery.execute(command.getTemporaryToken());
        String email = readTemporaryTokenResult.getEmail();

        // 계정 조회
        Account account = loadAccountPort.loadAccountOrElseThrow(email, ESecurityProvider.DEFAULT);

        // 임시 비밀번호 생성 및 저장
        String temporaryPassword = PasswordUtil.generatePassword(8);
        account.updatePassword(bCryptPasswordEncoder.encode(temporaryPassword));

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(ChangePasswordBySystemEvent.of(email, temporaryPassword));

        // 임시 토큰 삭제
        DeleteTemporaryTokenCommand deleteTemporaryTokenCommand = new DeleteTemporaryTokenCommand(email);
        deleteTemporaryTokenUseCase.execute(deleteTemporaryTokenCommand);
    }
}