package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadUserSummaryResponseDto;
import com.inglo.giggle.account.application.usecase.ReadUserSummaryUseCase;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.UserRepository;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.domain.service.ResumeAggregateService;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSummaryService implements ReadUserSummaryUseCase {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final EducationService educationService;
    private final ResumeAggregateService resumeAggregateService;

    @Override
    @Transactional(readOnly = true)
    public ReadUserSummaryResponseDto execute(UUID accountId) {

        // 유저 정보 조회
        User user = userRepository.findByIdOrElseThrow(accountId);

        // 이력서 정보 조회
        Resume resume = resumeRepository.findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(accountId);

        // 유저의 educationLevel에 맞는 학력 정보 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // 가장 졸업일자가 늦은 학력 정보 조회
        Education education = educationService.getLatestEnrollmentEducation(educations);

        // ResumeAggregate 생성 및 반환
        ResumeAggregate resumeAggregate = resumeAggregateService.createResumeAggregate(user, resume, education);
        Map<String, Integer> stringIntegerMap = resumeAggregateService.calculateWorkHours(resumeAggregate);

        return ReadUserSummaryResponseDto.of(
                user,
                resume,
                education,
                stringIntegerMap
        );
    }

}
