package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.ReadUserWorkExperienceDetailUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserWorkExperienceDetailResponseDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserWorkExperienceDetailService implements ReadUserWorkExperienceDetailUseCase {

    private final AccountRepository accountRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserWorkExperienceDetailResponseDto execute(UUID accountId, Long workExperienceId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // WorkExperience 조회
        WorkExperience workExperience = workExperienceRepository.findByIdOrElseThrow(workExperienceId);

        // WorkExperience 유효성 체크
        workExperience.checkValidation(accountId);
        
        return ReadUserWorkExperienceDetailResponseDto.from(workExperience);
    }

}
