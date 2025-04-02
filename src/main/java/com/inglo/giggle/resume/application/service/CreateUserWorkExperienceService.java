package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserWorkExperienceRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserWorkExperienceService implements CreateUserWorkExperienceUseCase {

    private final AccountRepository accountRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserWorkExperienceRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // WorkExperience 생성
        WorkExperience workExperience = WorkExperience.builder()
                .experienceTitle(requestDto.title())
                .workplace(requestDto.workplace())
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .description(requestDto.description())
                .resumeId(resume.getAccountId())
                .build();
        workExperienceRepository.save(workExperience);
    }

}
