package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.ReadUserEducationDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserEducationDetailResponseDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserEducationDetailService implements ReadUserEducationDetailUseCase {

    private final AccountRepository accountRepository;
    private final EducationRepository educationRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserEducationDetailResponseDto execute(UUID accountId, Long educationId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Education 조회
        Education education = educationRepository.findWithSchoolByIdOrElseThrow(educationId);

        // Education 유효성 체크
        education.checkValidation(accountId);

        return ReadUserEducationDetailResponseDto.fromEntity(education);
    }

}
