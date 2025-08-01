package com.inglo.giggle.career.application.service;

import com.inglo.giggle.career.application.dto.response.ReadGuestsCareersOverviewsResponseDto;
import com.inglo.giggle.career.application.usecase.ReadGuestCareersOverviewsUseCase;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.CareerImage;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.career.domain.type.ERecruitmentStatus;
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

@Service
@RequiredArgsConstructor
public class ReadGuestCareersOverviewsService implements ReadGuestCareersOverviewsUseCase {

    // 북마크 많은 순
    private static final String TRENDING = "POPULAR";

    // 최신순
    private static final String RECENT = "RECENT";

    private final CareerRepository careerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadGuestsCareersOverviewsResponseDto execute(
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

        List<ReadGuestsCareersOverviewsResponseDto.CareerOverviewDto> dtos = careerPage.getContent().stream()
                .map(this::toDto)
                .toList();

        return ReadGuestsCareersOverviewsResponseDto.builder()
                .careerList(dtos)
                .hasNext(careerPage.hasNext())
                .build();
    }

    private ReadGuestsCareersOverviewsResponseDto.CareerOverviewDto toDto(Career career) {
        return ReadGuestsCareersOverviewsResponseDto.CareerOverviewDto.builder()
                .id(career.getId())
                .title(career.getTitle())
                .careerCategory(career.getCategory())
                .visa(new ArrayList<>(career.getVisa()))
                .hostName(career.getHost())
                .organizerName(career.getOrganizer())
                .leftDays(calculateLeftDays(career.getStartDate(), career.getEndDate()))
                .status(calculateRecruitmentStatus(career.getStartDate(), career.getEndDate()))
                .recruitmentStartDate(career.getStartDate().toString())
                .recruitmentEndDate(career.getEndDate().toString())
                .imgUrls(new ArrayList<>(career.getCareerImages().stream()
                        .map(CareerImage::getImgUrl)
                        .toList()))
                .createdAt(career.getCreatedAt().toString())
                .build();
    }

    private int calculateLeftDays(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        if (today.isBefore(startDate)) {
            // 아직 시작 전 → 시작일까지 남은 날짜
            return (int) ChronoUnit.DAYS.between(today, startDate);
        } else if (!today.isAfter(endDate)) {
            // 시작일 이상이고 종료일 이하 → 종료일까지 남은 날짜
            return (int) ChronoUnit.DAYS.between(today, endDate);
        } else {
            // 종료일 이후 → 이미 지난 기간 (음수로 반환)
            return (int) ChronoUnit.DAYS.between(endDate, today) * -1;
        }
    }

    private ERecruitmentStatus calculateRecruitmentStatus(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(start)) return ERecruitmentStatus.PRE_RECRUITMENT;
        if (!today.isAfter(end)) return ERecruitmentStatus.RECRUITMENT;
        return ERecruitmentStatus.CLOSED;
    }
}
