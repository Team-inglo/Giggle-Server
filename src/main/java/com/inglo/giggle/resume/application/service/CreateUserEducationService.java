package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.dto.request.CreateUserEducationRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserEducationService implements CreateUserEducationUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ResumeRepository resumeRepository;
    private final SchoolRepository schoolRepository;
    private final EducationRepository educationRepository;
    private final EducationService educationService;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserEducationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // School 조회
        School school = schoolRepository.findByIdOrElseThrow(requestDto.schoolId());

        // Education 생성
        Education education = educationService.createEducation(
                EEducationLevel.fromString(requestDto.educationLevel()),
                school,
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade(),
                resume
        );
        educationRepository.save(education);
    }

}
