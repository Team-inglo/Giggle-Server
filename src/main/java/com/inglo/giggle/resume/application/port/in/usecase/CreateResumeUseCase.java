package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.CreateResumeCommand;

public interface CreateResumeUseCase {

    /**
     * 이력서 생성 유스케이스
     *
     */
    void execute(CreateResumeCommand command);
}
