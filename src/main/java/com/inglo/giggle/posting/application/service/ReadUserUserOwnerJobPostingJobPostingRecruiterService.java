package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.presentation.dto.response.ReadUserOwnerJobPostingJobPostingRecruiterResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingJobPostingRecruiterUserCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingJobPostingRecruiterService implements ReadUserUserOwnerJobPostingJobPostingRecruiterUserCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;


    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingJobPostingRecruiterResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // DTO 반환
        return ReadUserOwnerJobPostingJobPostingRecruiterResponseDto.from(
                userOwnerJobPosting
        );
    }

}
