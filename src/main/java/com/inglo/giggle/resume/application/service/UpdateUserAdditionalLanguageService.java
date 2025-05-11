package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateAdditionalLanguagePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserAdditionalLanguageService implements UpdateUserAdditionalLanguageUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateAdditionalLanguagePort updateAdditionalLanguagePort;

    @Override
    @Transactional
    public void execute(UpdateAdditionalLanguageCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // AdditionalLanguage 유효성 체크
        resume.checkAdditionalLanguageValidation(command.getAdditionalLanguageSkillId());

        // AdditionalLanguage 업데이트
        resume.updateAdditionalLanguage(command.getAdditionalLanguageSkillId(), command.getLanguageName(), command.getLevel());

        updateAdditionalLanguagePort.updateAdditionalLanguages(resume);
    }

}
