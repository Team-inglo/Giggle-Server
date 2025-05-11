package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.port.in.command.CreateEducationCommand;
import com.inglo.giggle.resume.application.port.in.usecase.CreateUserEducationUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateEducationPort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserEducationService implements CreateUserEducationUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateEducationPort updateEducationPort;

    @Override
    @Transactional
    public void execute(CreateEducationCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // Education 추가
        resume.addEducation(
                EEducationLevel.fromString(command.getEducationLevel()),
                command.getMajor(),
                command.getGpa(),
                command.getStartDate(),
                command.getEndDate(),
                command.getGrade(),
                command.getSchoolId()
        );

        updateEducationPort.updateEducations(resume);
    }
}
