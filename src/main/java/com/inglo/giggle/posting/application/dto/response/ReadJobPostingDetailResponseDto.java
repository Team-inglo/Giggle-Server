package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
public class ReadJobPostingDetailResponseDto extends SelfValidating<ReadJobPostingDetailResponseDto> {

    @NotNull
    @JsonProperty("id")
    private final Long id;

    @JsonProperty("is_my_post")
    private final Boolean isMyPost;

    @JsonProperty("is_book_marked")
    private final Boolean isBookMarked;

    @NotNull
    @JsonProperty("company_name")
    private final String companyName;

    @NotNull
    @JsonProperty("title")
    private final String title;

    @JsonProperty("icon_img_url")
    private final String iconImgUrl;

    @JsonProperty("company_img_url_list")
    private final List<CompanyImageDto> companyImgUrlList;

    @JsonProperty("tags")
    private final Tags tags;

    @JsonProperty("summaries")
    private final Summaries summaries;

    @JsonProperty("recruitment_conditions")
    private final RecruitmentConditions recruitmentConditions;

    @JsonProperty("detailed_overview")
    private final String detailedOverview;

    @JsonProperty("workplace_information")
    private final WorkplaceInformation workplaceInformation;

    @JsonProperty("working_conditions")
    private final WorkingConditions workingConditions;

    @JsonProperty("company_information")
    private final CompanyInformation companyInformation;

    @JsonProperty("created_at")
    private final String createdAt;

    @Builder
    public ReadJobPostingDetailResponseDto(
            Long id,
            Boolean isMyPost,
            Boolean isBookMarked,
            String companyName,
            String title,
            String iconImgUrl,
            List<CompanyImageDto> companyImgUrlList,
            Tags tags,
            Summaries summaries,
            RecruitmentConditions recruitmentConditions,
            String detailedOverview,
            WorkplaceInformation workplaceInformation,
            WorkingConditions workingConditions,
            CompanyInformation companyInformation,
            String createdAt) {
        this.id = id;
        this.isMyPost = isMyPost;
        this.isBookMarked = isBookMarked;
        this.companyName = companyName;
        this.title = title;
        this.iconImgUrl = iconImgUrl;
        this.companyImgUrlList = companyImgUrlList;
        this.tags = tags;
        this.summaries = summaries;
        this.recruitmentConditions = recruitmentConditions;
        this.detailedOverview = detailedOverview;
        this.workplaceInformation = workplaceInformation;
        this.workingConditions = workingConditions;
        this.companyInformation = companyInformation;
        this.createdAt = createdAt;

        this.validateSelf();
    }

    public static ReadJobPostingDetailResponseDto of(
            Account account,
            JobPosting jobPosting,
            List<CompanyImage> companyImageList,
            List<PostingWorkDayTime> postingWorkDayTimeList,
            boolean isMyPost
            ) {
        if(account.getRole() == ESecurityRole.USER){
            if(jobPosting.getBookMarks().stream().anyMatch(
                    bookMark -> bookMark.getUser().getId().equals(account.getId())
            )){
                return ReadJobPostingDetailResponseDto.builder()
                        .id(jobPosting.getId())
                        .isMyPost(isMyPost)
                        .isBookMarked(true)
                        .companyName(jobPosting.getOwner().getCompanyName())
                        .title(jobPosting.getTitle())
                        .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                        .companyImgUrlList(companyImageList.stream().map(
                                CompanyImageDto::fromEntity
                        ).toList())
                        .tags(
                                Tags.fromEntity(jobPosting)
                        )
                        .summaries(
                                Summaries.fromEntity(jobPosting)
                        )
                        .recruitmentConditions(
                                RecruitmentConditions.fromEntity(jobPosting)
                        )
                        .detailedOverview(jobPosting.getDescription())
                        .workplaceInformation(
                                WorkplaceInformation.fromEntity(jobPosting)
                        )
                        .workingConditions(
                                WorkingConditions.of(
                                        jobPosting,
                                        postingWorkDayTimeList
                                )
                        )
                        .companyInformation(
                                CompanyInformation.fromEntity(jobPosting)
                        )
                        .createdAt(DateTimeUtil.convertLocalDateTimeToString(jobPosting.getCreatedAt()))
                        .build();
            }
        }

        return ReadJobPostingDetailResponseDto.builder()
                .id(jobPosting.getId())
                .isMyPost(isMyPost)
                .isBookMarked(false)
                .companyName(jobPosting.getOwner().getCompanyName())
                .title(jobPosting.getTitle())
                .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                .companyImgUrlList(companyImageList.stream().map(
                        CompanyImageDto::fromEntity
                ).toList())
                .tags(
                        Tags.fromEntity(jobPosting)
                )
                .summaries(
                        Summaries.fromEntity(jobPosting)
                )
                .recruitmentConditions(
                        RecruitmentConditions.fromEntity(jobPosting)
                )
                .detailedOverview(jobPosting.getDescription())
                .workplaceInformation(
                        WorkplaceInformation.fromEntity(jobPosting)
                )
                .workingConditions(
                        WorkingConditions.of(
                                jobPosting,
                                postingWorkDayTimeList
                        )
                )
                .companyInformation(
                        CompanyInformation.fromEntity(jobPosting)
                )
                .createdAt(DateTimeUtil.convertLocalDateTimeToString(jobPosting.getCreatedAt()))
                .build();
    }

    @Getter
    public static class CompanyImageDto extends SelfValidating<CompanyImageDto> {

        @JsonProperty("id")
        private final Long id;

        @JsonProperty("img_url")
        private final String imgUrl;

        @Builder
        public CompanyImageDto(Long id, String imgUrl) {
            this.id = id;
            this.imgUrl = imgUrl;
            this.validateSelf();
        }

        public static CompanyImageDto fromEntity(CompanyImage companyImage) {
            return CompanyImageDto.builder()
                    .id(companyImage.getId())
                    .imgUrl(companyImage.getImgUrl())
                    .build();
        }
    }

    @Getter
    public static class Tags extends SelfValidating<Tags> {

        @JsonProperty("is_recruiting")
        private final Boolean isRecruiting;

        @JsonProperty("visa")
        private final Set<EVisa> visa;

        @JsonProperty("job_category")
        private final EJobCategory jobCategory;

        @Builder
        public Tags(Boolean isRecruiting, Set<EVisa> visa, EJobCategory jobCategory) {
            this.isRecruiting = isRecruiting;
            this.visa = visa;
            this.jobCategory = jobCategory;
            this.validateSelf();
        }

        public static Tags fromEntity(JobPosting jobPosting) {
            return Tags.builder()
                    .isRecruiting(jobPosting.getRecruitmentDeadLine() == null || jobPosting.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                    .visa(jobPosting.getVisa())
                    .jobCategory(jobPosting.getJobCategory())
                    .build();
        }
    }

    @Getter
    public static class Summaries extends SelfValidating<Summaries> {

        @JsonProperty("address")
        private final String address;

        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @JsonProperty("work_period")
        private final EWorkPeriod workPeriod;

        @JsonProperty("work_days_per_week")
        private final String workDaysPerWeek;

        @Builder
        public Summaries(String address, Integer hourlyRate, EWorkPeriod workPeriod, String workDaysPerWeek) {
            this.address = address;
            this.hourlyRate = hourlyRate;
            this.workPeriod = workPeriod;
            this.workDaysPerWeek = workDaysPerWeek;
            this.validateSelf();
        }

        public static Summaries fromEntity(JobPosting jobPosting) {
            return Summaries.builder()
                    .address(jobPosting.getAddress().getAddressName())
                    .hourlyRate(jobPosting.getHourlyRate())
                    .workPeriod(jobPosting.getWorkPeriod())
                    .workDaysPerWeek(jobPosting.getWorkDaysPerWeekToString())
                    .build();
        }
    }

    @Getter
    public static class RecruitmentConditions extends SelfValidating<RecruitmentConditions> {

        @JsonProperty("recruitment_deadline")
        private final String recruitmentDeadline;

        @JsonProperty("education")
        private final String education;

        @JsonProperty("number_of_recruits")
        private final Integer numberOfRecruits;

        @JsonProperty("visa")
        private final Set<EVisa> visa;

        @JsonProperty("gender")
        private final String gender;

        @JsonProperty("preferred_conditions")
        private final String preferredConditions;

        @JsonProperty("age_restriction")
        private final Integer ageRestriction;

        @Builder
        public RecruitmentConditions(String recruitmentDeadline, String education, Integer numberOfRecruits,
                                     Set<EVisa> visa, String gender, String preferredConditions, Integer ageRestriction) {
            this.recruitmentDeadline = recruitmentDeadline;
            this.education = education;
            this.numberOfRecruits = numberOfRecruits;
            this.visa = visa;
            this.gender = gender;
            this.preferredConditions = preferredConditions;
            this.ageRestriction = ageRestriction;
            this.validateSelf();
        }

        public static RecruitmentConditions fromEntity(JobPosting jobPosting) {
            return RecruitmentConditions.builder()
                    .recruitmentDeadline(jobPosting.getRecruitmentDeadLine() == null ? "상시모집" : DateTimeUtil.convertLocalDateToString(jobPosting.getRecruitmentDeadLine()))
                    .education(jobPosting.getEducationLevel().toString())
                    .numberOfRecruits(jobPosting.getRecruitmentNumber())
                    .visa(jobPosting.getVisa())
                    .gender(jobPosting.getGender().toString())
                    .preferredConditions(jobPosting.getPreferredConditions())
                    .ageRestriction(jobPosting.getAgeRestriction())
                    .build();
        }
    }

    @Getter
    public static class WorkplaceInformation extends SelfValidating<WorkplaceInformation> {

        @JsonProperty("main_address")
        private final String mainAddress;

        @JsonProperty("detailed_address")
        private final String detailedAddress;

        @JsonProperty("latitude")
        private final double latitude;

        @JsonProperty("longitude")
        private final double longitude;

        @JsonProperty("region_1depth_name")
        private final String region1DepthName;

        @JsonProperty("region_2depth_name")
        private final String region2DepthName;

        @JsonProperty("region_3depth_name")
        private final String region3DepthName;

        @JsonProperty("region_4depth_name")
        private final String region4DepthName;

        @Builder
        public WorkplaceInformation(String mainAddress, String detailedAddress, double latitude, double longitude,
                                    String region1DepthName, String region2DepthName, String region3DepthName, String region4DepthName) {
            this.mainAddress = mainAddress;
            this.detailedAddress = detailedAddress;
            this.latitude = latitude;
            this.longitude = longitude;
            this.region1DepthName = region1DepthName;
            this.region2DepthName = region2DepthName;
            this.region3DepthName = region3DepthName;
            this.region4DepthName = region4DepthName;
            this.validateSelf();
        }

        public static WorkplaceInformation fromEntity(JobPosting jobPosting) {
            return WorkplaceInformation.builder()
                    .mainAddress(jobPosting.getAddress().getAddressName())
                    .detailedAddress(jobPosting.getAddress().getAddressDetail())
                    .latitude(jobPosting.getAddress().getLatitude())
                    .longitude(jobPosting.getAddress().getLongitude())
                    .region1DepthName(jobPosting.getAddress().getRegion1DepthName())
                    .region2DepthName(jobPosting.getAddress().getRegion2DepthName())
                    .region3DepthName(jobPosting.getAddress().getRegion3DepthName())
                    .region4DepthName(jobPosting.getAddress().getRegion4DepthName())
                    .build();
        }
    }

    @Getter
    public static class WorkingConditions extends SelfValidating<WorkingConditions> {

        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @JsonProperty("work_period")
        private final EWorkPeriod workPeriod;

        @JsonProperty("work_day_times")
        private final List<WorkDayTimeDto> workDayTime;

        @JsonProperty("job_category")
        private final EJobCategory jobCategory;

        @JsonProperty("employment_type")
        private final String employmentType;

        @Builder
        public WorkingConditions(
                Integer hourlyRate,
                EWorkPeriod workPeriod,
                List<WorkDayTimeDto> workDayTime,
                EJobCategory jobCategory,
                String employmentType
        ) {
            this.hourlyRate = hourlyRate;
            this.workPeriod = workPeriod;
            this.workDayTime = workDayTime;
            this.jobCategory = jobCategory;
            this.employmentType = employmentType;
            this.validateSelf();
        }

        public static WorkingConditions of(JobPosting jobPosting, List<PostingWorkDayTime> postingWorkDayTimeList) {
            return WorkingConditions.builder()
                    .hourlyRate(jobPosting.getHourlyRate())
                    .workPeriod(jobPosting.getWorkPeriod())
                    .workDayTime(
                            Optional.ofNullable(postingWorkDayTimeList)
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .map(WorkDayTimeDto::fromEntity)
                                    .toList()
                    )
                    .jobCategory(jobPosting.getJobCategory())
                    .employmentType(jobPosting.getEmploymentType().toString())
                    .build();
        }

        @Getter
        public static class WorkDayTimeDto extends SelfValidating<WorkDayTimeDto> {

            @JsonProperty("day_of_week")
            private final String dayOfWeek;

            @JsonProperty("work_start_time")
            private final String workStartTime;

            @JsonProperty("work_end_time")
            private final String workEndTime;

            @Builder
            public WorkDayTimeDto(String dayOfWeek, String workStartTime, String workEndTime) {
                this.dayOfWeek = dayOfWeek;
                this.workStartTime = workStartTime;
                this.workEndTime = workEndTime;
                this.validateSelf();
            }
            private static final String NEGOTIABLE_TO_KO_STRING = "협의가능";

            public static WorkDayTimeDto fromEntity(PostingWorkDayTime postingWorkDayTime) {
                return WorkDayTimeDto.builder()
                        .dayOfWeek(postingWorkDayTime.getDayOfWeek().toString())
                        .workStartTime(postingWorkDayTime.getWorkEndTime() == null ?
                                NEGOTIABLE_TO_KO_STRING : DateTimeUtil.convertLocalTimeToString(postingWorkDayTime.getWorkStartTime()))
                        .workEndTime(postingWorkDayTime.getWorkEndTime() == null ?
                                NEGOTIABLE_TO_KO_STRING : DateTimeUtil.convertLocalTimeToString(postingWorkDayTime.getWorkEndTime()))
                        .build();
            }
        }
    }

    @Getter
    public static class CompanyInformation extends SelfValidating<CompanyInformation> {

        @JsonProperty("company_address")
        private final String companyAddress;

        @JsonProperty("representative_name")
        private final String representativeName;

        @JsonProperty("recruiter")
        private final String recruiter;

        @JsonProperty("contact")
        private final String contact;

        @JsonProperty("email")
        private final String email;

        @Builder
        public CompanyInformation(String companyAddress, String representativeName, String recruiter,
                                  String contact, String email) {
            this.companyAddress = companyAddress;
            this.representativeName = representativeName;
            this.recruiter = recruiter;
            this.contact = contact;
            this.email = email;
            this.validateSelf();
        }

        public static CompanyInformation fromEntity(JobPosting jobPosting) {
            return CompanyInformation.builder()
                    .companyAddress(jobPosting.getOwner().getAddress().getAddressName())
                    .representativeName(jobPosting.getOwner().getName())
                    .recruiter(jobPosting.getRecruiterName())
                    .contact(jobPosting.getRecruiterPhoneNumber())
                    .email(jobPosting.getRecruiterEmail())
                    .build();
        }
    }
}
