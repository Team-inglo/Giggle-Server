package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.presentation.dto.response.IssueAuthenticationCodeResponseDto;
import com.inglo.giggle.security.application.usecase.SignUpDefaultTemporaryUseCase;
import com.inglo.giggle.security.persistence.entity.redis.AuthenticationCodeHistoryEntity;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.domain.service.AuthenticationCodeHistoryService;
import com.inglo.giggle.security.domain.service.AuthenticationCodeService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import com.inglo.giggle.security.persistence.repository.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.persistence.repository.AuthenticationCodeRepository;
import com.inglo.giggle.security.persistence.repository.TemporaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpDefaultTemporaryService implements SignUpDefaultTemporaryUseCase {
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AuthenticationCodeService authenticationCodeService;
    private final AuthenticationCodeHistoryService authenticationCodeHistoryService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public IssueAuthenticationCodeResponseDto execute(SignUpDefaultTemporaryRequestDto requestDto) {
        // 중복된 이메일인지 확인
        if (isDuplicatedEmail(requestDto.email())) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        // 인증코드 발급 이력 조회
        AuthenticationCodeHistoryEntity history = authenticationCodeHistoryRepository.findByIdOrElseNull(requestDto.email());

        // 인증코드 발급 제한, 발급 속도 제한 유효성 검사
        authenticationCodeHistoryService.validateAuthenticationCodeHistory(history);

        // 임시 계정 생성
        temporaryAccountRepository.save(accountService.createTemporaryAccount(requestDto));

        // 새로운 인증코드 생성
        String code = PasswordUtil.generateAuthCode(6);

        // 새로운 인증코드 저장
        authenticationCodeRepository.save(
                authenticationCodeService.createAuthenticationCode(
                        requestDto.email(),
                        bCryptPasswordEncoder.encode(code)
                )
        );

        // 인증코드 발급 이력 업데이트
        if (history == null) {
            history = authenticationCodeHistoryRepository.saveAndReturn(authenticationCodeHistoryService.createAuthenticationCodeHistory(requestDto.email()));
        } else {
            history = authenticationCodeHistoryRepository.saveAndReturn(authenticationCodeHistoryService.incrementAuthenticationCodeCount(history));
        }

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(CompleteEmailValidationEvent.of(requestDto.email(), code));

        return IssueAuthenticationCodeResponseDto.fromEntity(history);
    }

    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isDuplicatedEmail(String email) {
        return accountRepository.findByEmailAndProviderOrElseNull(email, ESecurityProvider.DEFAULT) != null;
    }
}
