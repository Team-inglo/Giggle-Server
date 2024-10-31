package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.dto.request.CreateUserEducationRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
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
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Resume 조회
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // School 조회
        School school = schoolRepository.findById(requestDto.schoolId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
