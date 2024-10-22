package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.application.usecase.SignUpDefaultTemporaryUseCase;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.domain.service.AccountDomainService;
import com.inglo.giggle.security.domain.service.AuthenticationCodeDomainService;
import com.inglo.giggle.security.domain.service.AuthenticationCodeHistoryDomainService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.application.dto.response.IssueAuthenticationCodeResponseDto;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRepository;
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
    private final AccountDomainService accountDomainService;
    private final AuthenticationCodeDomainService authenticationCodeDomainService;
    private final AuthenticationCodeHistoryDomainService authenticationCodeHistoryDomainService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    @Transactional
    public IssueAuthenticationCodeResponseDto execute(SignUpDefaultTemporaryRequestDto requestDto) {
        // 중복된 아이디인지 확인
        if (isDuplicatedId(requestDto.id())) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_ID);
        }
        // 중복된 이메일인지 확인
        if (isDuplicatedEmail(requestDto.email())) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        // 인증코드 발급 이력 조회
        AuthenticationCodeHistory history = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElse(null);

        // 인증코드 발급 제한, 발급 속도 제한 유효성 검사
        authenticationCodeHistoryDomainService.validateAuthenticationCodeHistory(history);

        // 임시 계정 생성
        temporaryAccountRepository.save(accountDomainService.createTemporaryAccount(requestDto));

        // 새로운 인증코드 생성
        String code = PasswordUtil.generateAuthCode(6);

        // 새로운 인증코드 저장
        authenticationCodeRepository.save(authenticationCodeDomainService.createAuthenticationCode(requestDto.email(), bCryptPasswordEncoder.encode(code)));

        // 인증코드 발급 이력 업데이트
        if (history == null) {
            history = authenticationCodeHistoryRepository.save(authenticationCodeHistoryDomainService.createAuthenticationCodeHistory(requestDto.email()));
        } else {
            history = authenticationCodeHistoryRepository.save(authenticationCodeHistoryDomainService.incrementAuthenticationCodeCount(history));
        }

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(CompleteEmailValidationEvent.of(requestDto.email(), code));

        return IssueAuthenticationCodeResponseDto.fromEntity(history);
    }

    /**
     * 중복된 아이디인지 확인
     * @param serialId 아이디
     * @return 중복된 아이디인지 여부
     */
    private Boolean isDuplicatedId(String serialId) {
        return accountRepository.findBySerialId(serialId).isPresent();
    }

    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isDuplicatedEmail(String email) {
        return accountRepository.findByEmailAndProvider(email, ESecurityProvider.DEFAULT).isPresent();
    }
}
