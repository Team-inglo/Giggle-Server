package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.response.ReadUsersCareersOverviewsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadUsersCareersOverviewsUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.career.domain.type.ERecruitmentStatus;
import com.inglo.giggle.career.repository.BookMarkCareerRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.core.utility.EnumParseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserCareersOverviewsService implements ReadUsersCareersOverviewsUseCase {

    // 북마크 많은 순
    private static final String TRENDING = "POPULAR";

    // 최신순
    private static final String RECENT = "RECENT";

    private static final String END = "end";

    private final CareerRepository careerRepository;
    private final BookMarkCareerRepository bookMarkCareerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUsersCareersOverviewsResponseDto execute(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Career> careerPage = careerRepository.findBookmarkedCareersByUser(userId, pageable);

        List<ReadUsersCareersOverviewsResponseDto.CareerOverviewDto> dtos = careerPage.getContent().stream()
                .map(career -> ReadUsersCareersOverviewsResponseDto.CareerOverviewDto.builder()
                        .id(career.getId())
                        .isBookMarked(true)
                        .title(career.getTitle())
                        .careerCategory(career.getCategory())
                        .visa(new ArrayList<>(career.getVisa()))
                        .hostName(career.getHost())
                        .organizerName(career.getOrganizer())
                        .leftDays(calculateLeftDays(career.getStartDate(), career.getEndDate()))
                        .status(calculateRecruitmentStatus(career.getStartDate(), career.getEndDate()))
                        .recruitmentStartDate(career.getStartDate().toString())
                        .recruitmentEndDate(career.getEndDate().toString())
                        .createdAt(career.getCreatedAt().toString())
                        .build()
                )
                .toList();

        return ReadUsersCareersOverviewsResponseDto.builder()
                .careerList(dtos)
                .hasNext(careerPage.hasNext())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReadUsersCareersOverviewsResponseDto execute(
            UUID userId,
            int page,
            int size,
            String search,
            String sorting,
            String category
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ECareerCategory> categories;
        if(category == null || category.isBlank()) {
            categories = List.of(
                    ECareerCategory.ACTIVITY,
                    ECareerCategory.PROGRAM,
                    ECareerCategory.CONTEST,
                    ECareerCategory.CLUB
            );
        } else {
            categories = EnumParseUtil.parseEnums(category, ECareerCategory.class);
        }

        Page<Career> careerPage = switch (sorting.toUpperCase()) {
            case TRENDING -> careerRepository.findCareersOrderByBookMarks(search, categories, pageable);
            case RECENT -> careerRepository.findCareersOrderByCreatedAt(search, categories, pageable);
            default -> careerRepository.findCareersOrderByBookMarks(search, categories, pageable);
        };

        List<ReadUsersCareersOverviewsResponseDto.CareerOverviewDto> dtos = careerPage.getContent().stream()
                .map(career -> toDto(career, userId))
                .toList();

        return ReadUsersCareersOverviewsResponseDto.builder()
                .careerList(dtos)
                .hasNext(careerPage.hasNext())
                .build();
    }


    /**
     * ============================= PRIVATE METHODS =============================
     */
    private ReadUsersCareersOverviewsResponseDto.CareerOverviewDto toDto(Career career, UUID userId) {
        boolean isCareerBookmarked = bookMarkCareerRepository.existsByCareerIdAndUserId(career.getId(), userId);
        return ReadUsersCareersOverviewsResponseDto.CareerOverviewDto.builder()
                .id(career.getId())
                .isBookMarked(isCareerBookmarked)
                .title(career.getTitle())
                .careerCategory(career.getCategory())
                .visa(new ArrayList<>(career.getVisa()))
                .hostName(career.getHost())
                .organizerName(career.getOrganizer())
                .leftDays(calculateLeftDays(career.getStartDate(), career.getEndDate()))
                .status(calculateRecruitmentStatus(career.getStartDate(), career.getEndDate()))
                .recruitmentStartDate(career.getStartDate().toString())
                .recruitmentEndDate(career.getEndDate().toString())
                .createdAt(career.getCreatedAt().toString())
                .build();
    }

    private String calculateLeftDays(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        Long days;
        if (today.isBefore(startDate)) {
            days = ChronoUnit.DAYS.between(LocalDate.now(), startDate);
        } else if (today.isEqual(startDate) || today.isAfter(startDate)) {
            days = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
        } else {
            days = null;
        }
        return days != null ? days + " days left" : END;
    }

    private ERecruitmentStatus calculateRecruitmentStatus(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(start)) return ERecruitmentStatus.PRE_RECRUITMENT;
        if (!today.isAfter(end)) return ERecruitmentStatus.RECRUITMENT;
        return ERecruitmentStatus.CLOSED;
    }
}
