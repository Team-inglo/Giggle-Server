package com.inglo.giggle.security.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.dto.response.ValidationResponseDto;

@UseCase
public interface ValidateIdUseCase {

        /**
        * ID 유효성 검사
        * @param id 회원가입시 입력하는 ID
        * @return ValidationResponseDto
        */
        ValidationResponseDto execute(String id);
}
