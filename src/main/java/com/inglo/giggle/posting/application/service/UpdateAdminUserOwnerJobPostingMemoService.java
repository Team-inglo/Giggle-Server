package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.UpdateAdminUserOwnerJobPostingMemoUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAdminUserOwnerJobPostingMemoService implements UpdateAdminUserOwnerJobPostingMemoUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(Long userOwnerJobPostingId, String memo) {

        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByIdOrElseThrow(userOwnerJobPostingId);

        userOwnerJobPosting.updateMemo(memo);
    }
}
