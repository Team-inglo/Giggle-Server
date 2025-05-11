package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.DeleteIntroductionCommand;
import com.inglo.giggle.resume.application.port.in.usecase.DeleteUserIntroductionUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateResumePort;
import com.inglo.giggle.resume.domain.Resume;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserIntroductionService implements DeleteUserIntroductionUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateResumePort updateResumePort;

    @Override
    @Transactional
    public void execute(DeleteIntroductionCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResume(command.getAccountId());

        // Introduction null 로 업데이트
        resume.clearIntroduction();

        // Resume 업데이트
        updateResumePort.updateResume(resume);
    }
}
