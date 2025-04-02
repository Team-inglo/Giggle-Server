package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.UpdateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserWorkExperienceRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserWorkExperienceService implements UpdateUserWorkExperienceUseCase {

    private final AccountRepository accountRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long workExperienceId, UpdateUserWorkExperienceRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // WorkExperience 조회
        WorkExperience workExperience = workExperienceRepository.findByIdOrElseThrow(workExperienceId);

        // WorkExperience 유효성 체크
        workExperience.checkValidation(accountId);

        // WorkExperience 업데이트
        workExperience.updateSelf(
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description()
        );
        workExperienceRepository.save(workExperience);
    }

}
