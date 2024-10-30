package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.request.CreateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.dto.response.CreateOwnerJobPostingResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@UseCase
public interface CreateOwnerJobPostingUseCase {

    /**
     * 4.8 (고용주) 공고 등록하기
     * @param accountId 계정 ID
     * @param image 이미지 배열
     * @param requestDto 공고 등록 요청 DTO
     *
     */
    CreateOwnerJobPostingResponseDto execute(
            UUID accountId,
            List<MultipartFile> image,
            CreateOwnerJobPostingRequestDto requestDto
    );
}
