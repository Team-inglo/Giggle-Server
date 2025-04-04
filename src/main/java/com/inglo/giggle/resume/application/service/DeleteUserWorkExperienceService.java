package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserWorkExperienceService implements DeleteUserWorkExperienceUseCase {

    private final ResumeRepository resumeRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long workExperienceId) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // WorkExperience 유효성 체크
        resumeAggregate.checkWorkExperienceValidation(workExperienceId);

        // WorkExperience 삭제
        workExperienceRepository.delete(resumeAggregate.getWorkExperience(workExperienceId));
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
