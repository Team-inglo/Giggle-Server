package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerUserOwnerJobPostingUserBriefResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingUserBriefUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingUserBriefService implements ReadOwnerUserOwnerJobPostingUserBriefUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final OwnerRepository ownerRepository;
    private final AccountService accountService;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingUserBriefResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // DTO 반환
        return ReadOwnerUserOwnerJobPostingUserBriefResponseDto.fromEntity(
                userOwnerJobPosting.getUser()
        );
    }
}
