package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.presentation.dto.response.DefaultJsonWebTokenDto;

@UseCase
public interface SignUpDefaultUserUseCase {

    /**
     * 유학생 로그인 전용 회원가입 유스케이스
     * @param requestDto 유학생 회원가입 요청 DTO With Token
     */
    DefaultJsonWebTokenDto execute(SignUpDefaultUserRequestDto requestDto);
}
