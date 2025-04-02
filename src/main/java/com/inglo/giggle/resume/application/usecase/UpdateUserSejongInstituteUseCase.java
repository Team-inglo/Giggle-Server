package com.inglo.giggle.resume.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.resume.presentation.dto.request.UpdateUserSejongInstituteReqeustDto;

import java.util.UUID;

@UseCase
public interface UpdateUserSejongInstituteUseCase {

    /**
     * (유학생) 언어 - SEJONG INSTITUTE 레벨 수정하기 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 언어 - SEJONG INSTITUTE 레벨 수정 요청 DTO
     */
    void execute(UUID accountId, UpdateUserSejongInstituteReqeustDto requestDto);
}
