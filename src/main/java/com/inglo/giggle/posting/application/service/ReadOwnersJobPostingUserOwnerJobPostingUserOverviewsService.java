package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto execute(UUID accountId, Long jobPostingId, Integer page, Integer size, String sorting, String status) {

        // 고용주 조회 및 검증
        ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE));

        // 채용공고 조회
        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 정렬 방향 설정
        Sort.Direction direction = "ASCENDING".equalsIgnoreCase(sorting) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(direction, "updatedAt"));

        Page<UserOwnerJobPosting> userOwnerJobPostingPage;
        EApplicationStep eApplicationStep = EApplicationStep.fromString(status);

        // 특정 상태값들에 해당하는 목록 정의
        List<EApplicationStep> applicableSteps = List.of(
                EApplicationStep.RESUME_UNDER_REVIEW,
                EApplicationStep.WAITING_FOR_INTERVIEW,
                EApplicationStep.FILLING_OUT_DOCUMENTS,
                EApplicationStep.DOCUMENT_UNDER_REVIEW,
                EApplicationStep.APPLICATION_IN_PROGRESS,
                EApplicationStep.REGISTERING_RESULTS
        );

        if (EApplicationStep.APPLICATION_IN_PROGRESS.equals(eApplicationStep)) {
            // application_in_progress일 경우, applicableSteps에 해당하는 모든 상태값을 조회
            userOwnerJobPostingPage = userOwnerJobPostingRepository.findAllPageWithUserByJobPostingAndSteps(
                    jobPosting,
                    applicableSteps,
                    pageRequest
            );
        } else {
            // 그 외의 경우, 단일 상태값으로 조회
            userOwnerJobPostingPage = userOwnerJobPostingRepository.findAllPageWithUserByJobPostingAndStep(
                    jobPosting,
                    eApplicationStep,
                    pageRequest
            );
        }

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
