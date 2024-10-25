package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserAppliedJobDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserAppliedJobDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserAppliedJobDetailService implements ReadUserAppliedJobDetailUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadUserAppliedJobDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // UserOwnerJobPosting을 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 조회한 UserOwnerJobPosting이 accountId와 일치하는지 확인
        if (!userOwnerJobPosting.getUser().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // Dto 변환
        return ReadUserAppliedJobDetailResponseDto.fromEntity(
                userOwnerJobPosting
        );

    }
}