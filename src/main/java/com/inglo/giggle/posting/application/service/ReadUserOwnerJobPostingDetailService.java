package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOwnerJobPostingDetailService implements ReadUserOwnerJobPostingDetailUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    public ReadUserOwnerJobPostingDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // UserOwnerJobPosting을 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 조회한 UserOwnerJobPosting이 accountId와 일치하는지 확인
        userOwnerJobPostingService.validateUserOwnerJobPostingAndUser(userOwnerJobPosting, accountId);

        // DTO 변환
        return ReadUserOwnerJobPostingDetailResponseDto.fromEntity(
                userOwnerJobPosting
        );
    }
}