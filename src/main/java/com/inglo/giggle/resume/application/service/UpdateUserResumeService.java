package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.UpdateUserResumeRequestDtoV1;
import com.inglo.giggle.resume.application.dto.request.UpdateUserResumeRequestDtoV2;
import com.inglo.giggle.resume.application.usecase.UpdateUserResumeUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserResumeService implements UpdateUserResumeUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserResumeRequestDtoV1 requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // Introduction 업데이트
        resume.updateIntroduction(requestDto.introduction());
        resumeRepository.save(resume);
    }

    @Override
    @Transactional
    public void executeV2(UUID accountId, UpdateUserResumeRequestDtoV2 requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // Introduction 업데이트
        resume.updateResume(requestDto.title(), requestDto.introduction());
        resumeRepository.save(resume);
    }

}
