package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.CreateUserPartTimeEmploymentPermitRequestDto;

import java.util.UUID;

@UseCase
public interface CreateUserPartTimeEmploymentPermitUseCase {

    /**
     * (유학생) 시간제 취업 허가서 생성하기
     *
     * @param accountId 계정 ID
     * @param userOwnerJobPostingId 유저가 지원한 공고 ID
     * @param requestDto (유학생) 시간제 취업 허가서 생성하기
     */
    void execute(UUID accountId, Long userOwnerJobPostingId, CreateUserPartTimeEmploymentPermitRequestDto requestDto);
}
