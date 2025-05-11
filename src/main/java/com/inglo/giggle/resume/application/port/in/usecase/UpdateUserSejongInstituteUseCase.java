package com.inglo.giggle.resume.application.port.in.usecase;

import com.inglo.giggle.resume.application.port.in.command.UpdateSejongInstituteCommand;

public interface UpdateUserSejongInstituteUseCase {

    /**
     * (유학생) 언어 - SEJONG INSTITUTE 레벨 수정하기 유스케이스
     */
    void execute(UpdateSejongInstituteCommand command);
}
