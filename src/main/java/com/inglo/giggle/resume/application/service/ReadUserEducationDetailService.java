package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.vo.EducationWithSchoolDto;
import com.inglo.giggle.resume.application.usecase.ReadUserEducationDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.presentation.dto.response.ReadUserEducationDetailResponseDto;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserEducationDetailService implements ReadUserEducationDetailUseCase {

    private final EducationRepository educationRepository;
    private final ResumeRepository resumeRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserEducationDetailResponseDto execute(UUID accountId, Long educationId) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // Education 유효성 체크
        resumeAggregate.checkEducationValidation(educationId);

        // EducationWithSchoolDto 생성
        EducationWithSchoolDto educationWithSchoolDto = getEducationWithSchoolDto(resumeAggregate.getEducation(educationId));

        return ReadUserEducationDetailResponseDto.from(educationWithSchoolDto);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */

    private ResumeAggregate getResumeAggregate(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResumeId(resume.getAccountId());

        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .educations(educations)
                .build();
    }

    private EducationWithSchoolDto getEducationWithSchoolDto(Education education) {
        // School 조회
        School school = schoolRepository.findByIdOrElseThrow(education.getSchoolId());

        // EducationWithSchoolDto 생성
        return new EducationWithSchoolDto(education, school);
    }
}
