package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepRegisteringResultUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.request.UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUserOwnerJobPostingStepRegisteringResultService implements UpdateUserUserOwnerJobPostingStepRegisteringResultUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long userOwnerJobPostingId, UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingByIdOrElseThrow(userOwnerJobPostingId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // UserOwnerJobPosting의 상태 변경 및 결과, 피드백 저장
        userOwnerJobPosting.updateFinalResult(requestDto.isApproval(), requestDto.feedback());

        // UserOwnerJobPosting 저장
        userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // TODO: 결과에 따른 Notification 전송
    }
}