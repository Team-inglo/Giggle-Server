package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadUserSummaryResponseDto;
import com.inglo.giggle.account.application.usecase.ReadUserSummaryUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSummaryService implements ReadUserSummaryUseCase {
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final SchoolRepository schoolRepository;

    private final EducationService educationService;

    @Override
    public ReadUserSummaryResponseDto execute(UUID accountId) {
        // 유저 정보 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        // 이력서 정보 조회
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저의 비자에 맵핑되는 educationLevel 조회
        EEducationLevel educationLevel = educationService.getEducationLevelByVisa(user.getVisa());

        // 유저의 educationLevel에 맞는 학력 정보 조회
        Education education = educationRepository.findEducationByAccountIdAndEducationLevel(accountId, educationLevel)
                .orElse(null);

        return ReadUserSummaryResponseDto.of(user, resume, education);
    }
}
