package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.repository.UserRepository;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.presentation.dto.response.ReadAdminResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadAdminResumeDetailUseCase;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.persistence.repository.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadAdminResumeDetailService implements ReadAdminResumeDetailUseCase {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminResumeDetailResponseDto execute(UUID resumeId) {

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(resumeId);

        // User 조회
        User user = userRepository.findByIdOrElseThrow(resumeId);

        // education 조회
        List<Education> educations = educationRepository.findAllByResume(resume);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeOrElseThrow(resume);

        // WorkExperience 조회
        List<WorkExperience> workExperiences = workExperienceRepository.findAllByResume(resume);

        return ReadAdminResumeDetailResponseDto.of(resume, workExperiences, educations, languageSkill, user);
    }
}
