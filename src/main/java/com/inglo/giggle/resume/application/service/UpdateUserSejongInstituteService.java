package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateSejongInstituteCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserSejongInstituteUseCase;
import com.inglo.giggle.resume.application.port.out.UpdateLanguageSkillPort;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserSejongInstituteService implements UpdateUserSejongInstituteUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateLanguageSkillPort updateLanguageSkillPort;

    @Override
    @Transactional
    public void execute(UpdateSejongInstituteCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // SejongInstitute 업데이트
        resume.updateLanguageSkillSejongInstituteLevel(command.getLevel());

        updateLanguageSkillPort.updateLanguageSkill(resume);
    }

}
