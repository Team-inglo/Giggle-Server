package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.request.UpdateUserIntegratedApplicationRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateUserIntegratedApplicationUseCase {

    /**
     * (유학생) 통합신청서 수정하기
     *
     * @param accountId 계정 ID
     * @param documentId 문서 ID
     * @param requestDto (유학생) 통합신청서 수정 요청 DTO
     */
    void execute(UUID accountId, Long documentId, UpdateUserIntegratedApplicationRequestDto requestDto);
}
