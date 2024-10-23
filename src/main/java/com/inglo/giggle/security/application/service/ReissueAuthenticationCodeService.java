package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.application.usecase.ReissueAuthenticationCodeUseCase;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.domain.service.AuthenticationCodeService;
import com.inglo.giggle.security.domain.service.AuthenticationCodeHistoryService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.application.dto.request.IssueAuthenticationCodeRequestDto;
import com.inglo.giggle.security.application.dto.response.IssueAuthenticationCodeResponseDto;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueAuthenticationCodeService implements ReissueAuthenticationCodeUseCase {
    private final AccountRepository accountRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;

    private final AuthenticationCodeHistoryService authenticationCodeHistoryService;
    private final AuthenticationCodeService authenticationCodeService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    @Transactional
    public IssueAuthenticationCodeResponseDto execute(IssueAuthenticationCodeRequestDto requestDto) {

        // 이메일 중복 확인
        if (isDuplicatedEmail(requestDto.email())) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        // 인증코드 발급 이력 조회
        AuthenticationCodeHistory history = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElse(null);

        // 인증코드 발급 제한, 발급 속도 제한 유효성 검사
        authenticationCodeHistoryService.validateAuthenticationCodeHistory(history);

        // 새로운 인증코드 생성
        String code = PasswordUtil.generateAuthCode(6);

        // 새로운 인증코드 저장
        authenticationCodeRepository.save(authenticationCodeService.createAuthenticationCode(requestDto.email(), bCryptPasswordEncoder.encode(code)));

        // 인증코드 발급 이력 업데이트
        if (history == null) {
            history = authenticationCodeHistoryRepository.save(authenticationCodeHistoryService.createAuthenticationCodeHistory(requestDto.email()));
        } else {
            history = authenticationCodeHistoryRepository.save(authenticationCodeHistoryService.incrementAuthenticationCodeCount(history));
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
        return accountRepository.findByEmailAndProvider(email, ESecurityProvider.DEFAULT).isPresent();
    }
}
