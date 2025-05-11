package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.CreateWorkExperienceCommand;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateWorkExperiencePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserWorkExperienceService implements CreateUserWorkExperienceUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateWorkExperiencePort updateWorkExperiencePort;

    @Override
    @Transactional
    public void execute(CreateWorkExperienceCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // WorkExperience 추가
        resume.addWorkExperience(
                command.getTitle(),
                command.getWorkplace(),
                command.getStartDate(),
                command.getEndDate(),
                command.getDescription()
        );

        updateWorkExperiencePort.updateWorkExperiences(resume);
    }
}
