package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.CreateResumeCommand;
import com.inglo.giggle.resume.application.port.in.usecase.CreateResumeUseCase;
import com.inglo.giggle.resume.application.port.out.CreateResumePort;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateResumeService implements CreateResumeUseCase {

    private final CreateResumePort createResumePort;

    @Override
    public void execute(CreateResumeCommand command) {

        LanguageSkill languageSkill = LanguageSkill.builder()
                .topikLevel(0)
                .socialIntegrationLevel(0)
                .sejongInstituteLevel(0)
                .build();

        Resume resume = Resume.builder()
                .accountId(command.getAccountId())
                .languageSkill(languageSkill)
                .build();

        createResumePort.createResume(resume);
    }
}
