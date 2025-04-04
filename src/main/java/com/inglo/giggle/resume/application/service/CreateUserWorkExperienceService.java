package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import com.inglo.giggle.resume.presentation.dto.request.CreateUserWorkExperienceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserWorkExperienceService implements CreateUserWorkExperienceUseCase {

    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserWorkExperienceRequestDto requestDto) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // WorkExperience 추가
        resumeAggregate.addWorkExperience(
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description(),
                resumeAggregate.getResume().getAccountId()
        );

        workExperienceRepository.saveAll(resumeAggregate.getWorkExperiences());
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private ResumeAggregate getResumeAggregate(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResumeId(resume.getAccountId());
        
        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .workExperiences(workExperiences)
                .build();
    }

}
