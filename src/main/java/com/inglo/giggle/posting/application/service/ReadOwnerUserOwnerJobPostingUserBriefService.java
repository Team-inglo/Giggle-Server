package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingUserBriefUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingUserBriefResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingUserBriefService implements ReadOwnerUserOwnerJobPostingUserBriefUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingUserBriefResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserByIdOrElseThrow(userOwnerJobPostingsId);

        // UserOwnerJobPosting 고용주 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // DTO 반환
        return ReadOwnerUserOwnerJobPostingUserBriefResponseDto.of(
                userOwnerJobPosting.getUserInfo()
        );
    }
}
