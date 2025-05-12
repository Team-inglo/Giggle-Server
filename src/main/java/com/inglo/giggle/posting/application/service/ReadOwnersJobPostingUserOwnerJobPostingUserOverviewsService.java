package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.presentation.dto.response.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
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

    private final LoadAccountPort loadAccountPort;

    private final JobPostingRepository jobPostingRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final LoadSchoolPort loadSchoolPort;

    private final static String DASH = " - ";

    @Override
    @Transactional(readOnly = true)
    public ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto execute(UUID accountId, Long jobPostingId, Integer page, Integer size, String sorting, String status) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // 채용공고 조회
        JobPosting jobPosting = jobPostingRepository.findByIdOrElseThrow(jobPostingId);

        // 정렬 방향 설정
        Sort.Direction direction = "ASCENDING".equalsIgnoreCase(sorting) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(direction, "updatedAt"));

        Page<UserOwnerJobPosting> userOwnerJobPostingPage;

        if(status == null) {
            userOwnerJobPostingPage = userOwnerJobPostingRepository.findAllPageWithUserByJobPosting(jobPosting, pageRequest);
        } else{
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
        }


        // 지원자 리스트의 학교 정보 조회
        List<UUID> userIds = userOwnerJobPostingPage.stream()
                .map(userOwnerJobPosting -> userOwnerJobPosting.getUserInfo().getId())
                .toList();

        // 학교 정보 조회
        Map<UUID, String> userSchoolMap = loadSchoolPort.loadUserIdsAndSchools(userIds).stream()
                .collect(Collectors.toMap(
                        result -> UUID.fromString((String) result[0]),
                        result -> (String) result[1],
                        (existing, replacement) -> existing
                ));

        // DTO 반환
        List<ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto.ApplicantOverviewDto> applicantList = userOwnerJobPostingPage.stream().map(userOwnerJobPosting -> {
            UUID userId = userOwnerJobPosting.getUserInfo().getId();
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
