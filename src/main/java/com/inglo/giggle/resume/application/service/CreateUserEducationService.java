package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserEducationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserEducationService implements CreateUserEducationUseCase {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserEducationRequestDto requestDto) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // Education 추가
        resumeAggregate.addEducation(
                EEducationLevel.fromString(requestDto.educationLevel()),
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade(),
                requestDto.schoolId(),
                accountId
        );

        educationRepository.saveAll(resumeAggregate.getEducations());
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private ResumeAggregate getResumeAggregate(UUID resumeId) {

        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // Education 조회
        List<Education> educations = educationRepository.findAllByResumeId(resume.getAccountId());

        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .educations(educations)
                .build();
    }

}
