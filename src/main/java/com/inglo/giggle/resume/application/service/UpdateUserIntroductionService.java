package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.command.UpdateIntroductionCommand;
import com.inglo.giggle.resume.application.port.in.usecase.UpdateUserIntroductionUseCase;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateResumePort;
import com.inglo.giggle.resume.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserIntroductionService implements UpdateUserIntroductionUseCase {

    private final LoadResumePort loadResumePort;
    private final UpdateResumePort updateResumePort;

    @Override
    @Transactional
    public void execute(UpdateIntroductionCommand command) {

        // Resume 조회
        Resume resume = loadResumePort.loadResumeOrElseThrow(command.getAccountId());

        // Introduction 업데이트
        resume.updateIntroduction(command.getIntroduction());

        updateResumePort.updateResume(resume);
    }
}
