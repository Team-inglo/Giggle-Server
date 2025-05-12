package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateWorkExperienceCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateWorkExperiencePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserWorkExperienceService implements UpdateUserWorkExperienceUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateWorkExperiencePort updateWorkExperiencePort;

    @Override
    @Transactional
    public void execute(UpdateWorkExperienceCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithWorkExperiencesOrElseThrow(command.getAccountId());

        // WorkExperience 업데이트
        resume.updateWorkExperience(
                command.getWorkExperienceId(),
                command.getTitle(),
                command.getWorkplace(),
                command.getStartDate(),
                command.getEndDate(),
                command.getDescription()
        );

       updateWorkExperiencePort.updateWorkExperiences(resume);
    }

}
