package com.inglo.giggle.security.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.PasswordUtil;
import com.inglo.giggle.security.domain.redis.AuthenticationCode;
import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.dto.request.IssueAuthenticationCodeRequestDto;
import com.inglo.giggle.security.dto.response.IssueAuthenticationCodeResponseDto;
import com.inglo.giggle.security.event.CompleteEmailValidationEvent;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeHistoryRepository;
import com.inglo.giggle.security.repository.redis.AuthenticationCodeRepository;
import com.inglo.giggle.security.usecase.ReissueAuthenticationCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReissueAuthenticationCodeService implements ReissueAuthenticationCodeUseCase {
    private final AccountRepository accountRepository;
    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final AuthenticationCodeHistoryRepository authenticationCodeHistoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public IssueAuthenticationCodeResponseDto execute(IssueAuthenticationCodeRequestDto requestDto) {

        // 이메일 중복 확인
        if (isDuplicatedEmail(requestDto.email())) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        // 인증코드 발급 이력 조회
        AuthenticationCodeHistory history = authenticationCodeHistoryRepository.findById(requestDto.email())
                .orElse(null);

        // 인증코드 발급 제한 확인
        if (isBlockedIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_MANY_AUTHENTICATION_CODE_REQUESTS);
        }

        // 인증코드 발급 속도 제한 확인(1분 이내에 재요청 했을 경우)
        if (isTooFastIssuingAuthenticationCode(history)) {
            throw new CommonException(ErrorCode.TOO_FAST_AUTHENTICATION_CODE_REQUESTS);
        }

        // 새로운 인증코드 생성
        String code = PasswordUtil.generateAuthCode(6);
        AuthenticationCode authenticationCode = authenticationCodeRepository.save(
                AuthenticationCode.builder()
                        .email(requestDto.email())
                        .value(bCryptPasswordEncoder.encode(code))
                        .build()
        );

        // 인증코드 발급 이력 업데이트
        if (history == null) {
            history = authenticationCodeHistoryRepository.save(
                    AuthenticationCodeHistory.builder()
                            .email(requestDto.email())
                            .count(1)
                            .build()
            );
        } else {
            history = authenticationCodeHistoryRepository.save(history.copyWith(history.getCount() + 1));
        }

        // 메일 전송(비동기)
        applicationEventPublisher.publishEvent(CompleteEmailValidationEvent.builder()
                .receiverAddress(requestDto.email())
                .authenticationCode(code)
                .build());

        return IssueAuthenticationCodeResponseDto.fromEntity(history);
    }


    /**
     * 중복된 이메일인지 확인
     * @param email 이메일
     * @return 중복된 이메일인지 여부
     */
    private Boolean isDuplicatedEmail(String email) {
        return accountRepository.findBySerialIdAndProvider(email, ESecurityProvider.DEFAULT).isPresent();
    }

    /**
     * 인증코드 발급을 막은 사용자인지 확인
     * @param history 인증코드 발급 이력
     * @return 인증코드 발급을 막은 사용자인지 여부
     */
    private Boolean isBlockedIssuingAuthenticationCode(AuthenticationCodeHistory history) {
        if (history == null) {
            return false;
        }

        return history.getCount() >= 5;
    }

    /**
     * 인증코드 발급 속도가 너무 빠른지 확인
     * @param history 인증코드 발급 이력
     * @return 인증코드 발급 속도가 너무 빠른지 여부
     */
    private Boolean isTooFastIssuingAuthenticationCode(AuthenticationCodeHistory history) {
        if (history == null) {
            return false;
        }

        return history.getLastSentAt().isAfter(LocalDateTime.now().minusMinutes(1));
    }
}
