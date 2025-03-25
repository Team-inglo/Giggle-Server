package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.CreateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.WorkExperienceService;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserWorkExperienceService implements CreateUserWorkExperienceUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeRepository resumeRepository;
    private final WorkExperienceService workExperienceService;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserWorkExperienceRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // WorkExperience 생성
        WorkExperience workExperience = workExperienceService.createWorkExperience(
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description(),
                resume
        );
        workExperienceRepository.save(workExperience);
    }

}
