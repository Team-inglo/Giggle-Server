package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserWorkExperienceDetailUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.WorkExperienceService;
import com.inglo.giggle.resume.repository.WorkExperienceRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserWorkExperienceDetailService implements ReadUserWorkExperienceDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceService workExperienceService;
    @Override
    @Transactional(readOnly = true)
    public ReadUserWorkExperienceDetailResponseDto execute(UUID accountId, Long workExperienceId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // WorkExperience 조회
        WorkExperience workExperience = workExperienceRepository.findByIdOrElseThrow(workExperienceId);
        // WorkExperience 유효성 체크
        workExperienceService.checkWorkExperienceValidation(workExperience, accountId);
        
        return ReadUserWorkExperienceDetailResponseDto.fromEntity(workExperience);
    }

}
