package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.dto.request.UpdateUserEducationRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
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
public class UpdateUserEducationService implements UpdateUserEducationUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final EducationRepository educationRepository;
    private final EducationService educationService;
    private final SchoolRepository schoolRepository;


    @Override
    @Transactional
    public void execute(UUID accountId, Long educationId, UpdateUserEducationRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Education 조회
        Education education = educationRepository.findById(educationId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Education 유효성 체크
        educationService.checkEducationValidation(education, accountId);

        // School 조회
        School school = schoolRepository.findById(requestDto.schoolId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Education 업데이트
        education = educationService.updateEducation(
                education,
                EEducationLevel.fromString(requestDto.educationLevel()),
                school,
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade()
        );
        educationRepository.save(education);
    }

}
