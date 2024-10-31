package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.usecase.DeleteUserIntroductionUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserIntroductionService implements DeleteUserIntroductionUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public void execute(UUID accountId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Introduction null로 업데이트
        resume = resumeService.updateIntroduction(resume, null);
        resumeRepository.save(resume);
    }

}
