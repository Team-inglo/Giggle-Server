package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsService implements ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase {

    private final OwnerRepository ownerRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final SchoolRepository schoolRepository;

    private final static String DASH = " - ";

    @Override
    @Transactional(readOnly = true)
    public ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto execute(UUID accountId, Long jobPostingId, Integer page, Integer size) {

        // 고용주 조회 및 검증
        ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 채용공고 조회
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 지원자 리스트 조회
        Page<UserOwnerJobPosting> userOwnerJobPostingPage = userOwnerJobPostingRepository.findAllPageWithUserByJobPosting(
                jobPosting,
                PageRequest.of(page - 1, size)
        );

        // 지원자 리스트의 학교 정보 조회
        List<UUID> userIds = userOwnerJobPostingPage.stream()
                .map(userOwnerJobPosting -> userOwnerJobPosting.getUser().getId())
                .toList();

        // 학교 정보 조회
        Map<UUID, String> userSchoolMap = schoolRepository.findUserIdsWithMostRecentSchoolNames(userIds).stream()
                .collect(Collectors.toMap(
                        result -> (UUID) result[0],
                        result -> (String) result[1],
                        (existing, replacement) -> existing
                ));

        // DTO 반환
        List<ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto.ApplicantOverviewDto> applicantList = userOwnerJobPostingPage.stream().map(userOwnerJobPosting -> {
            UUID userId = userOwnerJobPosting.getUser().getId();
            String schoolName = userSchoolMap.getOrDefault(userId, DASH);

            return ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto.ApplicantOverviewDto.of(
                    userOwnerJobPosting,
                    schoolName
            );
        }).toList();

        return ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto.of(
                applicantList,
                userOwnerJobPostingPage.hasNext()
        );
    }



}
