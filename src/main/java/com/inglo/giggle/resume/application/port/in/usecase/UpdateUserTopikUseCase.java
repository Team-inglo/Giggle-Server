package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateTopikCommand;

public interface UpdateUserTopikUseCase {

    /**
     * (유학생) 언어 - TOPIK 레벨 수정하기 유스케이스
     */
    void execute(UpdateTopikCommand command);
}
