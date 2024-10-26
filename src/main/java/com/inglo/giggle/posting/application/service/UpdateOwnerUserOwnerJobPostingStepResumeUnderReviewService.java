package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewService implements UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final OwnerRepository ownerRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto) {

        // Owner 조회
        ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPostingService.updateStepFromResumeUnderReview(userOwnerJobPosting, requestDto.isAccepted());

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // TODO: 결과에 따른 Notification 전송
    }
}
