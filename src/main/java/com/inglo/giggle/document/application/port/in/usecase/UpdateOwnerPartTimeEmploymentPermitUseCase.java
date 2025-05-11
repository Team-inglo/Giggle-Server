package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerPartTimeEmploymentPermitCommand;

@UseCase
public interface UpdateOwnerPartTimeEmploymentPermitUseCase {

    /**
     * (고용주) 시간제취업허가서 수정하기
     */
    void execute(UpdateOwnerPartTimeEmploymentPermitCommand command);
}
