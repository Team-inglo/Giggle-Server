package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingJobPostingRecruiterResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingJobPostingRecruiterUserCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOwnerJobPostingJobPostingRecruiterService implements ReadUserOwnerJobPostingJobPostingRecruiterUserCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;


    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingJobPostingRecruiterResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // DTO 반환
        return ReadUserOwnerJobPostingJobPostingRecruiterResponseDto.fromEntity(
                userOwnerJobPosting.getJobPosting()
        );
    }

}
