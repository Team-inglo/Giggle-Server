package com.inglo.giggle.account.application.usecase;

import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface UpdateUserUseCase {

    /**
     * 유저 정보 수정하기
     *
     * @param accountId 계정 ID
     * @param requestDto 유저 정보 수정하기
     * @param image 이미지
     */
    void execute(UUID accountId, UpdateUserRequestDto requestDto, MultipartFile image);
}
