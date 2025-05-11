package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateTopikCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserTopikUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateLanguageSkillPort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserTopikService implements UpdateUserTopikUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateLanguageSkillPort updateLanguageSkillPort;

    @Override
    @Transactional
    public void execute(UpdateTopikCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // TopikLevel 업데이트
        resume.updateLanguageSkillTopikLevel(command.getLevel());

        updateLanguageSkillPort.updateLanguageSkill(resume);
    }

}
