package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserEducationRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserEducationService implements CreateUserEducationUseCase {

    private final AccountRepository accountRepository;
    private final ResumeRepository resumeRepository;
    private final SchoolRepository schoolRepository;
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserEducationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // School 조회
        School school = schoolRepository.findByIdOrElseThrow(requestDto.schoolId());

        // Education 생성
        Education education = Education.builder()
                .educationLevel(EEducationLevel.fromString(requestDto.educationLevel()))
                .schoolId(school.getId())
                .major(requestDto.major())
                .gpa(requestDto.gpa())
                .enrollmentDate(requestDto.startDate())
                .graduationDate(requestDto.endDate())
                .grade(requestDto.grade())
                .resumeId(resume.getAccountId())
                .build();
        educationRepository.save(education);
    }

}
