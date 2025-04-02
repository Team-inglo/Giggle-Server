package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingDetailService implements ReadUserUserOwnerJobPostingDetailUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserUserOwnerJobPostingDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting을 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesByIdOrElseThrow(userOwnerJobPostingsId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // DTO 변환
        return ReadUserUserOwnerJobPostingDetailResponseDto.from(
                userOwnerJobPosting
        );
    }
}