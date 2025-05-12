package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.DeleteEducationCommand;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserEducationUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateEducationPort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserEducationService implements DeleteUserEducationUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateEducationPort updateEducationPort;

    @Override
    @Transactional
    public void execute(DeleteEducationCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeWithEducationsOrElseThrow(command.getAccountId());

        // Education 유효성 체크
        resume.checkEducationValidation(command.getEducationId());

        // Education 삭제
        resume.deleteEducation(command.getEducationId());

        // Education 업데이트
        updateEducationPort.updateEducations(resume);
    }
}
