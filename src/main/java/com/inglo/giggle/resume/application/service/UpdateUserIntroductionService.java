package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.UpdateUserIntroductionUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserIntroductionRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserIntroductionService implements UpdateUserIntroductionUseCase {

    private final AccountRepository accountRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserIntroductionRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // Introduction 업데이트
        resume.updateIntroduction(requestDto.introduction());
        resumeRepository.save(resume);
    }

}
