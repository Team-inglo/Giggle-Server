package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.CreateAdditionalLanguageCommand;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateAdditionalLanguagePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserAdditionalLanguageService implements CreateUserAdditionalLanguageUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateAdditionalLanguagePort updateAdditionalLanguagePort;

    @Override
    @Transactional
    public void execute(CreateAdditionalLanguageCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // 이미 존재하는 언어인지 체크
        resume.checkIsExistAdditionalLanguage(command.getLanguageName());

        // AdditionalLanguage 추가
        resume.addAdditionalLanguage(command.getLanguageName(), command.getLevel());

        // 추가된 언어 저장
        updateAdditionalLanguagePort.updateAdditionalLanguages(resume);
    }
}
