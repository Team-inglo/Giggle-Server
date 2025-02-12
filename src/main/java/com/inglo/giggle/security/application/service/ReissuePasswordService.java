package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.application.usecase.ReissuePasswordUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.event.ChangePasswordBySystemEvent;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissuePasswordService implements ReissuePasswordUseCase {

    private final AccountRepository accountRepository;

    private final TemporaryTokenRepository temporaryTokenRepository;

    private final AccountService accountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(String temporaryTokenValue) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인 -> id, email 추출
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(temporaryTokenValue)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
        String id = temporaryToken.getId();
        String email = temporaryToken.getEmail();

        // 계정 조회
        Account account = accountRepository.findBySerialIdAndProvider(id, ESecurityProvider.DEFAULT)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 임시 비밀번호 생성 및 저장
        String temporaryPassword = PasswordUtil.generatePassword(8);
        accountService.changePassword(account, bCryptPasswordEncoder.encode(temporaryPassword));

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(ChangePasswordBySystemEvent.of(email, temporaryPassword));

        // 임시 토큰 삭제
        temporaryTokenRepository.delete(temporaryToken);
    }
}