package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.presentation.dto.request.UpdateOwnerJobPostingRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@UseCase
public interface UpdateOwnerJobPostingUseCase {

    /**
     * 4.11 (고용주) 공고 수정하기
     * @param accountId 계정 ID
     * @param jobPostingId 공고 ID
     * @param requestDto 공고 수정 요청 DTO
     */
    void execute(
            List<MultipartFile> image,
            UUID accountId,
            Long jobPostingId,
            UpdateOwnerJobPostingRequestDto requestDto
    );
}
