package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingJobPostingRecruiterResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingJobPostingRecruiterUserCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOwnerJobPostingJobPostingRecruiterService implements ReadUserOwnerJobPostingJobPostingRecruiterUserCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;


    @Override
    public ReadUserOwnerJobPostingJobPostingRecruiterResponseDto execute(UUID accountId, Long userOwnerJobPostingId) {

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithJobPostingById(userOwnerJobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 조회한 UserOwnerJobPosting의 UserId가 accountId와 일치하는지 확인
        if (!userOwnerJobPosting.getUser().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        return ReadUserOwnerJobPostingJobPostingRecruiterResponseDto.fromEntity(
                userOwnerJobPosting.getJobPosting()
        );
    }

}
