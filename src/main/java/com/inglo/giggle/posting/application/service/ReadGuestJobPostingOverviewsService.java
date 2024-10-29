package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.*;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadGuestJobPostingOverviewsService implements ReadGuestJobPostingOverviewsUseCase {

    private final JobPostingRepository jobPostingRepository;

    private static final String POPULAR_SORTING = "popular";
    private static final String NONE = "none";

    @Override
    @Transactional(readOnly = true)
    public ReadGuestJobPostingOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String jobTitle,
            String sorting,
            String region1Depth,
            String region2Depth,
            String region3Depth,
            String industry,
            String workPeriod,
            String workDaysPerWeek,
            String workingDay,
            String workingHours,
            String recruitmentPeriod,
            String employmentType,
            String visa
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        LocalDate today = LocalDate.now();

        // region 필터 값들을 ',' 기준으로 리스트로 변환
        List<String> region1DepthList = parseRegions(region1Depth);
        List<String> region2DepthList = parseRegions(region2Depth);
        List<String> region3DepthList = parseRegions(region3Depth);

        // 필터 파라미터 변환
        List<EJobCategory> industryList = parseEnums(industry, EJobCategory.class);
        List<EWorkPeriod> workPeriodList = parseEnums(workPeriod, EWorkPeriod.class);
        List<Integer> workDaysPerWeekList = parseIntegerToEnums(workDaysPerWeek);

        // 요일 설정
        List<EDayOfWeek> workingDayListBefore = parseEnums(workingDay, EDayOfWeek.class);
        if (workingDayListBefore == null) {
            workingDayListBefore = new ArrayList<>();
        } else {
            workingDayListBefore = new ArrayList<>(workingDayListBefore);
        }
        workingDayListBefore.add(EDayOfWeek.NEGOTIABLE);
        List<EDayOfWeek> workingDayList = workingDayListBefore.stream().distinct().toList();

        // 시간대 설정
        LocalTime morningStart = LocalTime.of(6, 0);
        LocalTime morningEnd = LocalTime.of(12, 0);
        LocalTime afternoonStart = LocalTime.of(12, 0);
        LocalTime afternoonEnd = LocalTime.of(18, 0);
        LocalTime eveningStart = LocalTime.of(18, 0);
        LocalTime eveningEnd = LocalTime.of(22, 0);
        LocalTime fullDayStart = LocalTime.of(12, 0);
        LocalTime fullDayEnd = LocalTime.of(23, 59);
        LocalTime dawnStart = LocalTime.of(0, 0);
        LocalTime dawnEnd = LocalTime.of(6, 0);

        // workingHoursList의 시간대에 따라 선택 여부 설정
        List<EWorkingHours> workingHoursList = parseEnums(workingHours, EWorkingHours.class);
        if (workingHoursList == null) {
            workingHoursList = new ArrayList<>();
        }

        boolean morningSelected = workingHoursList.contains(EWorkingHours.MORNING);
        boolean afternoonSelected = workingHoursList.contains(EWorkingHours.AFTERNOON);
        boolean eveningSelected = workingHoursList.contains(EWorkingHours.EVENING);
        boolean fullDaySelected = workingHoursList.contains(EWorkingHours.FULLDAY);
        boolean dawnSelected = workingHoursList.contains(EWorkingHours.DAWN);

        Page<JobPosting> jobPostingsPage;

        // 인기순 또는 최신순에 따라 다른 메서드 호출
        if (sorting.equalsIgnoreCase(POPULAR_SORTING)) {
            jobPostingsPage = jobPostingRepository.findPopularJobPostingsWithFilters(
                    jobTitle,
                    !region1DepthList.isEmpty() ? region1DepthList.get(0) : null,
                    region1DepthList.size() > 1 ? region1DepthList.get(1) : null,
                    region1DepthList.size() > 2 ? region1DepthList.get(2) : null,
                    !region2DepthList.isEmpty() ? region2DepthList.get(0) : null,
                    region2DepthList.size() > 1 ? region2DepthList.get(1) : null,
                    region2DepthList.size() > 2 ? region2DepthList.get(2) : null,
                    !region3DepthList.isEmpty() ? region3DepthList.get(0) : null,
                    region3DepthList.size() > 1 ? region3DepthList.get(1) : null,
                    region3DepthList.size() > 2 ? region3DepthList.get(2) : null,
                    industryList,
                    workPeriodList,
                    workDaysPerWeekList,
                    workingDayList,
                    workingHoursList,
                    morningStart,
                    morningEnd,
                    afternoonStart,
                    afternoonEnd,
                    eveningStart,
                    eveningEnd,
                    fullDayStart,
                    fullDayEnd,
                    dawnStart,
                    dawnEnd,
                    morningSelected,
                    afternoonSelected,
                    eveningSelected,
                    fullDaySelected,
                    dawnSelected,
                    EDayOfWeek.NEGOTIABLE,
                    today,
                    recruitmentPeriod,
                    employmentType == null ? null: EEmploymentType.fromString(employmentType),
                    visa == null ? null: EVisa.fromString(visa),
                    pageable
            );
        } else {
            jobPostingsPage = jobPostingRepository.findRecentJobPostingsWithFilters(
                    jobTitle,
                    !region1DepthList.isEmpty() ? region1DepthList.get(0) : null,
                    region1DepthList.size() > 1 ? region1DepthList.get(1) : null,
                    region1DepthList.size() > 2 ? region1DepthList.get(2) : null,
                    !region2DepthList.isEmpty() ? region2DepthList.get(0) : null,
                    region2DepthList.size() > 1 ? region2DepthList.get(1) : null,
                    region2DepthList.size() > 2 ? region2DepthList.get(2) : null,
                    !region3DepthList.isEmpty() ? region3DepthList.get(0) : null,
                    region3DepthList.size() > 1 ? region3DepthList.get(1) : null,
                    region3DepthList.size() > 2 ? region3DepthList.get(2) : null,
                    industryList,
                    workPeriodList,
                    workDaysPerWeekList,
                    workingDayList,
                    workingHoursList,
                    morningStart,
                    morningEnd,
                    afternoonStart,
                    afternoonEnd,
                    eveningStart,
                    eveningEnd,
                    fullDayStart,
                    fullDayEnd,
                    dawnStart,
                    dawnEnd,
                    morningSelected,
                    afternoonSelected,
                    eveningSelected,
                    fullDaySelected,
                    dawnSelected,
                    EDayOfWeek.NEGOTIABLE,
                    today,
                    recruitmentPeriod,
                    employmentType == null ? null: EEmploymentType.fromString(employmentType),
                    visa == null ? null: EVisa.fromString(visa),
                    pageable
            );

        }

        return ReadGuestJobPostingOverviewsResponseDto.fromPage(jobPostingsPage);
    }


    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */

    private List<String> parseRegions(String region) {
        if (region == null || region.isEmpty()) return new ArrayList<>(); // 빈 리스트 반환
        return Arrays.stream(region.split(","))
                .map(String::trim)
                .filter(value -> !value.equalsIgnoreCase(NONE)) // "none" 값을 제외
                .toList();
    }


    private List<Integer> parseIntegerToEnums(String input) {
        if (input == null || input.isEmpty()) return null;
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(value -> EWorkDaysPerWeek.toInt(Enum.valueOf(EWorkDaysPerWeek.class, value)))
                .toList();
    }

    private <E extends Enum<E>> List<E> parseEnums(String input, Class<E> enumClass) {
        if (input == null || input.isEmpty()) return null;
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(value -> Enum.valueOf(enumClass, value))
                .toList();
    }

}
