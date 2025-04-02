package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.presentation.dto.request.ChangePasswordRequestDto;

import java.util.UUID;

@UseCase
public interface ChangePasswordUseCase {

        /**
        * 비밀번호 변경 유스케이스
        * @param accountId 비밀번호 변경을 요청한 계정의 ID
        * @param requestDto 비밀번호 변경 요청 DTO
        */
        void execute(UUID accountId, ChangePasswordRequestDto requestDto);
}
