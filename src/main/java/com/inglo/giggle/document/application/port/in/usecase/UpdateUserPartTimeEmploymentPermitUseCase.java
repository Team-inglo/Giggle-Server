package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateUserPartTimeEmploymentPermitCommand;

@UseCase
public interface UpdateUserPartTimeEmploymentPermitUseCase {

    /**
     * (유학생) 시간제취업허가서 수정하기
     */
    void execute(UpdateUserPartTimeEmploymentPermitCommand command);
}
