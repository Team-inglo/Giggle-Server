package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.presentation.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface UpdateOwnerUseCase {

    /**
     * 고용주 정보 수정하기
     *
     * @param accountId 계정 ID
     * @param requestDto 고용주 정보 수정하기
     * @param multipartFile 이미지
     */
    void execute(UUID accountId, UpdateOwnerRequestDto requestDto, MultipartFile multipartFile);
}
