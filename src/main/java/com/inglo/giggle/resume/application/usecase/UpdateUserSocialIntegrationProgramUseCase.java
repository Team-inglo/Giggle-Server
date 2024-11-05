package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.application.dto.request.UpdateUserSocialIntegrationProgramReqeustDto;

import java.util.UUID;

@UseCase
public interface UpdateUserSocialIntegrationProgramUseCase {

    /**
     * (유학생) 언어 - SOCIAL INTEGRATION PROGRAM 레벨 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 언어 - SOCIAL INTEGRATION PROGRAM 레벨 수정 요청 DTO
     */
    void execute(UUID accountId, UpdateUserSocialIntegrationProgramReqeustDto requestDto);
}
