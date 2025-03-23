package com.inglo.giggle.term.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.term.application.dto.request.CreateAdminTermAccountRequestDto;

@UseCase
public interface CreateAdminTermAccountUseCase {

    public void execute(CreateAdminTermAccountRequestDto requestDto);
}
