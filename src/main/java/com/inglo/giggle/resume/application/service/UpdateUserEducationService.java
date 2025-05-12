package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.port.in.command.UpdateEducationCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserEducationUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateEducationPort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserEducationService implements UpdateUserEducationUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateEducationPort updateEducationPort;

    @Override
    @Transactional
    public void execute(UpdateEducationCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithEducationsOrElseThrow(command.getAccountId());

        // Education 유효성 체크
        resume.checkEducationValidation(command.getEducationId());

        // Education 업데이트
        resume.updateEducation(
                command.getEducationId(),
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
