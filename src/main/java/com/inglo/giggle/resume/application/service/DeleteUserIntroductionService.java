package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserIntroductionUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserIntroductionService implements DeleteUserIntroductionUseCase {

    private final AccountRepository accountRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // Introduction null로 업데이트
        resume.deleteIntroduction();
        resumeRepository.save(resume);
    }

}
