package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserWorkExperienceDetailUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.WorkExperienceService;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserWorkExperienceDetailService implements ReadUserWorkExperienceDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceService workExperienceService;
    @Override
    public ReadUserWorkExperienceDetailResponseDto execute(UUID accountId, Long workExperienceId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // WorkExperience 조회
        WorkExperience workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        // WorkExperience 유효성 체크
        workExperienceService.checkWorkExperienceValidation(workExperience, accountId);
        
        return ReadUserWorkExperienceDetailResponseDto.fromEntity(workExperience);
    }

}
