package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingCountService implements ReadOwnerUserOwnerJobPostingCountUseCase {

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    public ReadOwnerUserOwnerJobPostingCountResponseDto execute(UUID accountId) {

        // Owner 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Owner가 등록한 JobPosting 조회
        List<JobPosting> jobPostingList = jobPostingRepository.findAllByOwner(owner);

        // Owner가 등록한 UserOwnerJobPosting 조회 및 성공한 지원자 수 확인
        List<UserOwnerJobPosting> userOwnerJobPostingList = userOwnerJobPostingRepository.findAllWithJobPostingByOwner(owner);
        Integer successFulHireCounts = userOwnerJobPostingService.getSuccessFulHireCounts(userOwnerJobPostingList);

        // DTO 반환
        return ReadOwnerUserOwnerJobPostingCountResponseDto.of(
                jobPostingList.size(),
                userOwnerJobPostingList.size(),
                successFulHireCounts
        );
    }
}
