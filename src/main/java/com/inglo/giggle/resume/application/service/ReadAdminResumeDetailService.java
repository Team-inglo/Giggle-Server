package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.response.ReadAdminResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadAdminResumeDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.repository.EducationRepository;
import com.inglo.giggle.resume.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAdminResumeDetailService implements ReadAdminResumeDetailUseCase {

    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminResumeDetailResponseDto execute(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(resumeId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        return ReadAdminResumeDetailResponseDto.of(resume, workExperiences, educations, languageSkill, resume.getUser());
    }
}
