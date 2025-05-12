package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.DeleteAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateAdditionalLanguagePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserAdditionalLanguageService implements DeleteUserAdditionalLanguageUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateAdditionalLanguagePort updateAdditionalLanguagePort;

    @Override
    @Transactional
    public void execute(DeleteAdditionalLanguageCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithLanguageSkillAndAdditionalLanguageOrElseThrow(command.getAccountId());

        // AdditionalLanguage 유효성 체크
        resume.checkAdditionalLanguageValidation(command.getAdditionalLanguageSkillId());

        // AdditionalLanguage 삭제
        resume.deleteAdditionalLanguage(command.getAdditionalLanguageSkillId());

        // AdditionalLanguage 업데이트
        updateAdditionalLanguagePort.updateAdditionalLanguages(resume);
    }

}
