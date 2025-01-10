package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.EnumParseUtil;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.posting.domain.type.EWorkingHours;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadGuestJobPostingOverviewsService implements ReadGuestJobPostingOverviewsUseCase {

    private final AddressService addressService;

    private final JobPostingRepository jobPostingRepository;

    private static final String POPULAR_SORTING = "popular";

    private static final String TRENDING = "TRENDING";
    private static final String RECENTLY = "RECENTLY";

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
        List<List<String>> regionSets = addressService.parseRegionSets(region1Depth, region2Depth, region3Depth);
        List<String> region1DepthList = addressService.getDepthList(regionSets, 0);
        List<String> region2DepthList = addressService.getDepthList(regionSets, 1);
        List<String> region3DepthList = addressService.getDepthList(regionSets, 2);

        // 필터 파라미터 변환
        List<EJobCategory> industryList = EnumParseUtil.parseEnums(industry, EJobCategory.class);
        List<EWorkPeriod> workPeriodList = EnumParseUtil.parseEnums(workPeriod, EWorkPeriod.class);
        List<Integer> workDaysPerWeekList = EnumParseUtil.parseIntegerToEnums(workDaysPerWeek);
        List<EDayOfWeek> workingDayList = EnumParseUtil.parseEnums(workingDay, EDayOfWeek.class);
        List<EWorkingHours> workingHoursList = EnumParseUtil.parseEnums(workingHours, EWorkingHours.class);

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

        boolean morningSelected = false;
        boolean afternoonSelected = false;
        boolean eveningSelected = false;
        boolean fullDaySelected = false;
        boolean dawnSelected = false;

        if (workingHoursList != null) {
            morningSelected = workingHoursList.contains(EWorkingHours.MORNING);
            afternoonSelected = workingHoursList.contains(EWorkingHours.AFTERNOON);
            eveningSelected = workingHoursList.contains(EWorkingHours.EVENING);
            fullDaySelected = workingHoursList.contains(EWorkingHours.FULLDAY);
            dawnSelected = workingHoursList.contains(EWorkingHours.DAWN);
        }

        Page<JobPostingRepository.JobPostingProjection> jobPostingProjections;
        // 인기순 또는 최신순에 따라 다른 메서드 호출
        if (sorting != null && sorting.equalsIgnoreCase(POPULAR_SORTING)) {
            jobPostingProjections = jobPostingRepository.findPopularJobPostingsWithFilters(
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
                    visa == null ? null : EVisa.fromString(visa),
                    pageable
            );
        } else {
            jobPostingProjections = jobPostingRepository.findRecentJobPostingsWithFilters(
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
                    visa == null ? null : EVisa.fromString(visa),
                    pageable
            );
        }
        // Step 2: JobPosting IDs 추출
        List<Long> jobPostingIds = jobPostingProjections.stream()
                .map(JobPostingRepository.JobPostingProjection::getJobPostingId)
                .toList();

        // Step 3: 상세 데이터 가져오기
        List<JobPosting> jobPostings = jobPostingRepository.findJobPostingsWithDetailsByIds(jobPostingIds);

        // Step 4: jobPostings을 jobPostingIds 순서로 정렬
        Map<Long, JobPosting> jobPostingMap = jobPostings.stream()
                .collect(Collectors.toMap(JobPosting::getId, Function.identity())); // ID를 키로 하는 Map 생성

        List<JobPosting> sortedJobPostings = jobPostingIds.stream()
                .map(jobPostingMap::get) // jobPostingIds 순서대로 Map에서 값 가져오기
                .filter(Objects::nonNull) // Map에 없는 ID가 있을 경우 필터링
                .toList();

        // Step 4: hasNext 정보와 상세 데이터를 활용해 응답 생성
        return ReadGuestJobPostingOverviewsResponseDto.of(
                jobPostingProjections.hasNext(),
                sortedJobPostings
        );
    }

    @Override
    public ReadGuestJobPostingOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String type
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<JobPostingRepository.JobPostingProjection> jobPostingProjections = switch (type) {
            case TRENDING -> jobPostingRepository.findTrendingJobPostingsWithFetchJoin(LocalDate.now(), pageable);
            case RECENTLY -> jobPostingRepository.findRecentlyJobPostingsWithFetchJoin(LocalDate.now(), pageable);
            default -> throw new CommonException(ErrorCode.NOT_FOUND_TYPE);
        };

        List<Long> jobPostingIds = jobPostingProjections.stream()
                .map(JobPostingRepository.JobPostingProjection::getJobPostingId)
                .toList();
        log.info("jobPostingIds: {}", jobPostingIds);

        // Step 3: 상세 데이터 가져오기
        List<JobPosting> jobPostings = jobPostingRepository.findJobPostingsWithDetailsByIds(jobPostingIds);

        // Step 4: jobPostings을 jobPostingIds 순서로 정렬
        Map<Long, JobPosting> jobPostingMap = jobPostings.stream()
                .collect(Collectors.toMap(JobPosting::getId, Function.identity())); // ID를 키로 하는 Map 생성

        List<JobPosting> sortedJobPostings = jobPostingIds.stream()
                .map(jobPostingMap::get) // jobPostingIds 순서대로 Map에서 값 가져오기
                .filter(Objects::nonNull) // Map에 없는 ID가 있을 경우 필터링
                .toList();

        return ReadGuestJobPostingOverviewsResponseDto.of(
                jobPostingProjections.hasNext(),
                sortedJobPostings
        );
    }
}
