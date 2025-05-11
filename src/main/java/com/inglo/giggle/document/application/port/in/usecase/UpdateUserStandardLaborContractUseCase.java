package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateUserStandardLaborContractCommand;

@UseCase
public interface UpdateUserStandardLaborContractUseCase {

    /**
     * (유학생) 표준 근로계약서 수정하기
     */
    void execute(UpdateUserStandardLaborContractCommand command);
}
