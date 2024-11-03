package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadGuestJobPostingOverviewsService implements ReadGuestJobPostingOverviewsUseCase {

    private final JobPostingRepository jobPostingRepository;

    private static final String POPULAR_SORTING = "popular";
    private static final String NONE = "none";

    private static final String TRENDING = "TRENDING";
    private static final String RECENTLY = "RECENTLY";
    private static final String BOOKMARKED = "BOOKMARKED";

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
        List<List<String>> regionSets = parseRegionSets(region1Depth, region2Depth, region3Depth);
        List<String> region1DepthList = regionSets.stream().map(list -> list.get(0)).toList();
        List<String> region2DepthList = regionSets.stream().map(list -> list.get(1)).toList();
        List<String> region3DepthList = regionSets.stream().map(list -> list.get(2)).toList();

        // 필터 파라미터 변환
        List<EJobCategory> industryList = parseEnums(industry, EJobCategory.class);
        List<EWorkPeriod> workPeriodList = parseEnums(workPeriod, EWorkPeriod.class);
        List<Integer> workDaysPerWeekList = parseIntegerToEnums(workDaysPerWeek);

        // 요일 설정
        List<EDayOfWeek> workingDayList = parseEnums(workingDay, EDayOfWeek.class);

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

        boolean morningSelected = false;
        boolean afternoonSelected= false;
        boolean eveningSelected= false;
        boolean fullDaySelected= false;
        boolean dawnSelected = false;

        if (workingHoursList != null){
            morningSelected = workingHoursList.contains(EWorkingHours.MORNING);
            afternoonSelected = workingHoursList.contains(EWorkingHours.AFTERNOON);
            eveningSelected = workingHoursList.contains(EWorkingHours.EVENING);
            fullDaySelected = workingHoursList.contains(EWorkingHours.FULLDAY);
            dawnSelected = workingHoursList.contains(EWorkingHours.DAWN);
        }

        // 인기순 또는 최신순에 따라 다른 메서드 호출
        if (sorting !=null && sorting.equalsIgnoreCase(POPULAR_SORTING)) {
            List<JobPosting> jobPostingList = jobPostingRepository.findPopularJobPostingsWithFilters(
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
                    employmentType == null ? null : EEmploymentType.fromString(employmentType),
                    visa == null ? null : EVisa.fromString(visa)
            );

            Map<Long, Integer> bookmarkCountMap = jobPostingList.stream()
                    .collect(Collectors.toMap(
                            JobPosting::getId,
                            jobPosting -> jobPostingRepository.countBookmarksByJobPostingId(jobPosting.getId())
                    ));

            jobPostingList.sort((jobPosting1, jobPosting2) ->
                    Integer.compare(
                            bookmarkCountMap.getOrDefault(jobPosting2.getId(), 0),
                            bookmarkCountMap.getOrDefault(jobPosting1.getId(), 0)
                    )
            );

            // 정렬된 List를 다시 Page 객체로 변환
            Page<JobPosting> sortedJobPostingsPage = new PageImpl<>(jobPostingList, pageable, jobPostingList.size());

            // fromPage 메서드를 사용해 응답 생성
            return ReadGuestJobPostingOverviewsResponseDto.fromPage(sortedJobPostingsPage);

        } else {
            return ReadGuestJobPostingOverviewsResponseDto.fromPage(jobPostingRepository.findRecentJobPostingsWithFilters(
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
                    )
            );

        }
    }

    @Override
    public ReadGuestJobPostingOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String type
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<JobPosting> jobPostingsList = switch (type) {
            case TRENDING -> jobPostingRepository.findTrendingJobPostingsWithFetchJoin();
            case RECENTLY -> jobPostingRepository.findRecentlyJobPostingsWithFetchJoin();
            case BOOKMARKED -> jobPostingRepository.findBookmarkedJobPostingsWithFetchJoin();
            default -> throw new CommonException(ErrorCode.NOT_FOUND_TYPE);
        };

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), jobPostingsList.size());
        Page<JobPosting> jobPostingsPage = new PageImpl<>(jobPostingsList.subList(start, end), pageable, jobPostingsList.size());

        return ReadGuestJobPostingOverviewsResponseDto.fromPage(jobPostingsPage);
    }


    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */

    private List<List<String>> parseRegionSets(String region1Depth, String region2Depth, String region3Depth) {
        List<String> region1DepthList = parseSingleDepth(region1Depth);
        List<String> region2DepthList = parseSingleDepth(region2Depth);
        List<String> region3DepthList = parseSingleDepth(region3Depth);

        List<List<String>> regionSets = new ArrayList<>();
        int maxSize = Math.max(region1DepthList.size(), Math.max(region2DepthList.size(), region3DepthList.size()));

        for (int i = 0; i < maxSize; i++) {
            String region1 = i < region1DepthList.size() ? region1DepthList.get(i) : null;
            String region2 = i < region2DepthList.size() ? region2DepthList.get(i) : null;
            String region3 = i < region3DepthList.size() ? region3DepthList.get(i) : null;

            if (!(region1 == null && region2 == null && region3 == null)) {
                regionSets.add(Arrays.asList(region1, region2, region3));
            }
        }
        return regionSets;
    }

    private List<String> parseSingleDepth(String region) {
        if (region == null || region.isEmpty()) return new ArrayList<>(); // 빈 리스트 반환
        return Arrays.stream(region.split(","))
                .map(String::trim)
                .map(value -> value.equalsIgnoreCase(NONE) ? null : value) // "none"을 null로 변환
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
