package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateSocialIntegrationProgramCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserSocialIntegrationProgramUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateLanguageSkillPort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserSocialIntegrationProgramService implements UpdateUserSocialIntegrationProgramUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateLanguageSkillPort updateLanguageSkillPort;

    @Override
    @Transactional
    public void execute(UpdateSocialIntegrationProgramCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // SocialIntegrationLevel 업데이트
        resume.updateLanguageSkillSocialIntegrationLevel(command.getLevel());

        updateLanguageSkillPort.updateLanguageSkill(resume);
    }

}
