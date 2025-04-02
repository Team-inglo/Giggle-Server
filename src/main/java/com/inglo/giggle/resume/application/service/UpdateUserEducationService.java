package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.usecase.UpdateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserEducationRequestDto;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserEducationService implements UpdateUserEducationUseCase {

    private final AccountRepository accountRepository;
    private final EducationRepository educationRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long educationId, UpdateUserEducationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Education 조회
        Education education = educationRepository.findByIdOrElseThrow(educationId);

        // Education 유효성 체크
        education.checkValidation(accountId);

        // School 조회
        School school = schoolRepository.findByIdOrElseThrow(requestDto.schoolId());

        // Education 업데이트
        education.updateSelf(
                EEducationLevel.fromString(requestDto.educationLevel()),
                school.getId(),
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade()
        );

        educationRepository.save(education);
    }

}
