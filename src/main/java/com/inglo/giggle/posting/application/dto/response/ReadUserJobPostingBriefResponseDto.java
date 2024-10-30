package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUserJobPostingBriefResponseDto extends SelfValidating<ReadUserJobPostingBriefResponseDto> {

    @JsonProperty("job_posting_list")
    private final List<JobPostingBriefDto> jobPostingList;

    @Builder
    public ReadUserJobPostingBriefResponseDto(List<JobPostingBriefDto> jobPostingList) {
        this.jobPostingList = jobPostingList;
        this.validateSelf();
    }
    public static ReadUserJobPostingBriefResponseDto fromEntities(List<JobPosting> jobPostingList) {
        return ReadUserJobPostingBriefResponseDto.builder()
                .jobPostingList(jobPostingList.stream()
                        .map(JobPostingBriefDto::fromEntity)
                        .toList())
                .build();
    }

    @Getter
    public static class JobPostingBriefDto extends SelfValidating<JobPostingBriefDto> {

        @NotNull(message = "id는 null이 될 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "title은 null이 될 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotNull(message = "recruitment_dead_line는 null이 될 수 없습니다.")
        @JsonProperty("recruitment_dead_line")
        private final String recruitmentDeadLine;

        @NotNull(message = "job_category는 null이 될 수 없습니다.")
        @JsonProperty("job_category")
        private final EJobCategory jobCategory;

        @Builder
        public JobPostingBriefDto(Long id, String title, String recruitmentDeadLine, EJobCategory jobCategory) {
            this.id = id;
            this.title = title;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.jobCategory = jobCategory;
            this.validateSelf();
        }

        public static JobPostingBriefDto fromEntity(JobPosting jobPosting) {
            return JobPostingBriefDto.builder()
                    .id(jobPosting.getId())
                    .title(jobPosting.getTitle())
                    .recruitmentDeadLine(jobPosting.getRecruitmentDeadLine().toString())
                    .jobCategory(jobPosting.getJobCategory())
                    .build();
        }
    }
}
