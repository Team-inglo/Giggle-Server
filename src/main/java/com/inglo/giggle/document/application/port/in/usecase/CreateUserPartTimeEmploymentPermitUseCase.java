package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.CreateUserPartTimeEmploymentPermitCommand;

@UseCase
public interface CreateUserPartTimeEmploymentPermitUseCase {

    /**
     * (유학생) 시간제 취업 허가서 생성하기
     */
    void execute(CreateUserPartTimeEmploymentPermitCommand command);
}
