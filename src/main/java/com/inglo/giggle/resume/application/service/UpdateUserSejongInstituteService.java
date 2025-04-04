package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.UpdateUserSejongInstituteUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserSejongInstituteReqeustDto;
import com.inglo.giggle.security.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserSejongInstituteService implements UpdateUserSejongInstituteUseCase {

    private final ResumeRepository resumeRepository;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserSejongInstituteReqeustDto requestDto) {

        // ResumeAggregate 생성
        ResumeAggregate resumeAggregate = getResumeAggregate(accountId);

        // SejongInstitute 업데이트
        resumeAggregate.updateLanguageSkillSejongInstituteLevel(requestDto.level());

        languageSkillRepository.save(resumeAggregate.getLanguageSkill());
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                       private method                                            -------*
     * -------------------------------------------------------------------------------------------------------------- */
    private ResumeAggregate getResumeAggregate(UUID resumeId) {
        // Resume 조회
        Resume resume = resumeRepository.findByAccountIdOrElseThrow(resumeId);

        // LanguageSkill 조회
        LanguageSkill languageSkill = languageSkillRepository.findByResumeIdOrElseThrow(resume.getAccountId());

        // ResumeAggregate 생성
        return ResumeAggregate.builder()
                .resume(resume)
                .languageSkill(languageSkill)
                .build();
    }

}
