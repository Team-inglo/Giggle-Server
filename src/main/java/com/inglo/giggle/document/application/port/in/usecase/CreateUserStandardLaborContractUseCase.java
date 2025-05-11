package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.CreateUserStandardLaborContractCommand;

@UseCase
public interface CreateUserStandardLaborContractUseCase {

    /**
     * (유학생) 표준 근로계약서 생성하기
     */
    void execute(CreateUserStandardLaborContractCommand command);
}
