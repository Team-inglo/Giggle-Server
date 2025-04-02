package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReadAdminJobPostingsOverviewsResponseDto extends SelfValidating<ReadAdminJobPostingsSummariesResponseDto> {

    private final List<JobPostingOverviewDto> jobPostings;

    private final PageInfoDto pageInfo;

    @Builder
    public ReadAdminJobPostingsOverviewsResponseDto(
            List<JobPostingOverviewDto> jobPostings,
            PageInfoDto pageInfo
    ) {
        this.jobPostings = jobPostings;
        this.pageInfo = pageInfo;

        this.validateSelf();
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class JobPostingOverviewDto extends SelfValidating<JobPostingOverviewDto> {

        @NotNull
        private final Long jobPostingId;

        @NotNull
        private final String title;

        @NotNull
        private final EJobCategory jobCategory;

        @NotNull
        private final List<WorkDayTimeDto> workDayTimes;

        @NotNull
        private final Integer hourlyRate;

        @NotNull
        private final EEmploymentType employmentType;

        @NotNull
        private final String address;

        private final String recruitmentDeadLine; // nullable

        @NotNull
        private final Integer recruitmentNumber;

        @NotNull
        private final EGender gender;

        @NotNull
        private final Integer ageRestriction;

        @NotNull
        private final EEducationLevel educationLevel;

        @NotNull
        private final Set<EVisa> visa;

        @NotNull
        private final String registrationDate;

        @Builder
        public JobPostingOverviewDto(
                Long jobPostingId,
                String title,
                EJobCategory jobCategory,
                List<WorkDayTimeDto> workDayTimes,
                Integer hourlyRate,
                EEmploymentType employmentType,
                String address,
                String recruitmentDeadLine,
                Integer recruitmentNumber,
                EGender gender,
                Integer ageRestriction,
                EEducationLevel educationLevel,
                Set<EVisa> visa,
                String registrationDate
        ) {
            this.jobPostingId = jobPostingId;
            this.title = title;
            this.jobCategory = jobCategory;
            this.workDayTimes = workDayTimes;
            this.hourlyRate = hourlyRate;
            this.employmentType = employmentType;
            this.address = address;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.recruitmentNumber = recruitmentNumber;
            this.gender = gender;
            this.ageRestriction = ageRestriction;
            this.educationLevel = educationLevel;
            this.visa = visa;
            this.registrationDate = registrationDate;
            this.validateSelf();
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class WorkDayTimeDto extends SelfValidating<WorkDayTimeDto> {

        @NotNull
        private final EDayOfWeek dayOfWeek;

        private final String workStartTime; // nullable

        private final String workEndTime; // nullable

        @Builder
        public WorkDayTimeDto(
                EDayOfWeek dayOfWeek,
                String workStartTime,
                String workEndTime
        ) {
            this.dayOfWeek = dayOfWeek;
            this.workStartTime = workStartTime;
            this.workEndTime = workEndTime;
            this.validateSelf();
        }

        public static WorkDayTimeDto of(
                PostingWorkDayTime postingWorkDayTime
        ){
            return WorkDayTimeDto.builder()
                    .dayOfWeek(postingWorkDayTime.getDayOfWeek())
                    .workStartTime(
                            postingWorkDayTime.getWorkStartTime() == null ? "시간" : postingWorkDayTime.getWorkStartTime().toString()
                    )
                    .workEndTime(
                            postingWorkDayTime.getWorkEndTime() == null ? "무관" : postingWorkDayTime.getWorkEndTime().toString()
                    ).build();
        }
    }

    public static ReadAdminJobPostingsOverviewsResponseDto of(
            Page<JobPosting> jobPostingsPage
    ){
        List<JobPostingOverviewDto> jobPostingOverviewDtos = jobPostingsPage.getContent().stream()
                .map(jobPosting -> JobPostingOverviewDto.builder()
                        .jobPostingId(jobPosting.getId())
                        .title(jobPosting.getTitle())
                        .jobCategory(jobPosting.getJobCategory())
                        .workDayTimes(
                                jobPosting.getWorkDayTimes().stream().map(
                                        WorkDayTimeDto::of
                                ).toList()
                        )
                        .hourlyRate(jobPosting.getHourlyRate())
                        .employmentType(jobPosting.getEmploymentType())
                        .address(jobPosting.getAddress().getAddressDetail())
                        .recruitmentDeadLine(jobPosting.getRecruitmentDeadLine() != null ? DateTimeUtil.convertLocalDateToString(jobPosting.getRecruitmentDeadLine()) : null)
                        .recruitmentNumber(jobPosting.getRecruitmentNumber() != null ? jobPosting.getRecruitmentNumber() : null)
                        .gender(jobPosting.getGender())
                        .ageRestriction(jobPosting.getAgeRestriction() != null ? jobPosting.getAgeRestriction() : null)
                        .educationLevel(jobPosting.getEducationLevel())
                        .visa(new HashSet<>(jobPosting.getVisa()))
                        .registrationDate(DateTimeUtil.convertLocalDateToString(jobPosting.getCreatedAt().toLocalDate()))
                        .build()
                ).toList();

        PageInfoDto pageInfo = PageInfoDto.of(
                jobPostingsPage.getNumber() + 1,
                jobPostingsPage.getNumberOfElements(),
                jobPostingsPage.getSize(),
                jobPostingsPage.getTotalPages(),
                (int) jobPostingsPage.getTotalElements()
        );

        return ReadAdminJobPostingsOverviewsResponseDto.builder()
                .jobPostings(jobPostingOverviewDtos)
                .pageInfo(pageInfo)
                .build();
    }
}
