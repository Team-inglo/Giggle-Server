package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserPartTimeEmploymentPermitRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserPartTimeEmploymentPermitUseCase {

    /**
     * (유학생) 시간제취업허가서 수정하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     * @param requestDto (유학생) 시간제취업허가서 수정 요청 DTO
     */
    void execute(UUID accountId, Long documentId, UpdateUserPartTimeEmploymentPermitRequestDto requestDto);
}
