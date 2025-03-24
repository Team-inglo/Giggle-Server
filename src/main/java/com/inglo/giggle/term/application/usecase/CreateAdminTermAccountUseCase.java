package com.inglo.giggle.term.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.term.application.dto.request.CreateAdminTermAccountRequestDto;

@UseCase
public interface CreateAdminTermAccountUseCase {

    /**
     * (관리자) 약관 생성하기 UseCase
     */
    void execute(CreateAdminTermAccountRequestDto requestDto);
}
