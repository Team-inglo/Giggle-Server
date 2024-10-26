package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepFillingOutDocumentUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUserOwnerJobPostingStepFillingOutDocumentService implements UpdateUserUserOwnerJobPostingStepFillingOutDocumentUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserRepository userRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId) {

        // User 조회
        userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting의 상태 변경
        userOwnerJobPostingService.updateStepFromStepFillingOutDocument(userOwnerJobPosting);

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

    }
}
