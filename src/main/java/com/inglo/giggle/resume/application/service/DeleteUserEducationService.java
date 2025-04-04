package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserEducationService implements DeleteUserEducationUseCase {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long educationId) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // Education 유효성 체크
        resumeAggregate.checkEducationValidation(educationId);

        // Education 삭제
        educationRepository.delete(resumeAggregate.getEducation(educationId));
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
