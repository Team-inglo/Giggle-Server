package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReadAdminJobPostingsOverviewsService implements ReadAdminJobPostingsOverviewsUseCase {

    private final JobPostingRepository jobPostingRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminJobPostingsOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String search,
            String stringStartDate,
            String stringEndDate,
            String filterType,
            String filter,
            String sortType,
            String sort
    ) {
        // 날짜 파싱
        LocalDateTime startDateTime = DateTimeUtil.convertStringToLocalDate(stringStartDate).atStartOfDay();
        LocalDateTime endDateTime = DateTimeUtil.convertStringToLocalDate(stringEndDate).plusDays(1).atStartOfDay(); // inclusive

        // 정렬 처리
        Sort.Direction direction = "ASC".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        String sortField = resolveSortField(sortType);

        // 페이징 처리
        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by(direction, sortField)
        );

        // QueryDSL 조회
        Page<JobPosting> jobPostingsPage = jobPostingRepository.searchWithConditions(
                search,
                startDateTime,
                endDateTime,
                filterType,
                filter,
                pageable
        );

        return ReadAdminJobPostingsOverviewsResponseDto.of(jobPostingsPage);
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */
    private String resolveSortField(String sortType) {
        if (sortType == null) return "createdAt"; // 기본 정렬 필드

        return switch (sortType) {
            case "hourly_rate" -> "hourlyRate";
            case "recruitment_dead_line" -> "recruitmentDeadLine";
            case "age_restriction" -> "ageRestriction";
            case "registration_date" -> "registrationDate";
            default -> "createdAt"; // 알 수 없는 값도 기본 필드로 fallback
        };
    }
}

