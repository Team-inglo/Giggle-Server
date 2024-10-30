package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerResumeDetailService implements ReadOwnerResumeDetailUseCase {
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final ResumeRepository resumeRepository;
    private final EducationRepository educationRepository;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    public ReadOwnerResumeDetailResponseDto execute(Long userOwnerJobPostingId) {
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithUserById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        UUID accountId = userOwnerJobPosting.getUser().getId();

        Resume resume = resumeRepository.findWithWorkExperiencesAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<Education> educations = educationRepository.findAllByResume(resume);

        LanguageSkill languageSkill = languageSkillRepository.findByResume(resume)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        return ReadOwnerResumeDetailResponseDto.of(resume, educations, languageSkill, userOwnerJobPosting.getUser());
    }
}
