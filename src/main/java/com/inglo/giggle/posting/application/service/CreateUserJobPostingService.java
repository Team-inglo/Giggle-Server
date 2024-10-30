package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.CreateUserJobPostingResponseDto;
import com.inglo.giggle.posting.application.usecase.CreateUserJobPostingUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserJobPostingService implements CreateUserJobPostingUseCase {

    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional
    public CreateUserJobPostingResponseDto execute(UUID accountId, Long jobPostingId) {

        // 유저, 공고 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        JobPosting jobPosting = jobPostingRepository.findWithOwnerById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저가 이미 지원한 공고인지 확인
        if(userOwnerJobPostingRepository.existsByUserAndJobPosting(user, jobPosting)){
            throw new CommonException(ErrorCode.ALREADY_APPLIED_JOB_POSTING);
        }

        // 유저-공고 매핑 생성
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingService.createUserOwnerJobPosting(
                user,
                jobPosting,
                jobPosting.getOwner()
        );

        // 유저-공고 매핑 저장
        UserOwnerJobPosting savedUserOwnerJobPosting = userOwnerJobPostingRepository.save(userOwnerJobPosting);

        // DTO 반환
        return CreateUserJobPostingResponseDto.of(
                savedUserOwnerJobPosting.getId()
        );
    }
}
