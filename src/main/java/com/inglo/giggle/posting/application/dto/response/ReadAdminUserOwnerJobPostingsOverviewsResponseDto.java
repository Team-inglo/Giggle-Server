package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReadAdminUserOwnerJobPostingsOverviewsResponseDto extends SelfValidating<ReadAdminUserOwnerJobPostingsOverviewsResponseDto> {

    @NotNull
    private final List<UserOwnerJobPostingOverviewDto> userOwnerJobPostings;

    @NotNull
    private final PageInfoDto pageInfo;

    @Builder
    public ReadAdminUserOwnerJobPostingsOverviewsResponseDto(
            List<UserOwnerJobPostingOverviewDto> userOwnerJobPostings,
            PageInfoDto pageInfo
    ) {
        this.userOwnerJobPostings = userOwnerJobPostings;
        this.pageInfo = pageInfo;
        this.validateSelf();
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserOwnerJobPostingOverviewDto extends SelfValidating<UserOwnerJobPostingOverviewDto> {

        @NotNull
        private final Long userOwnerJobPostingId;

        @NotNull
        private final Long jobPostingId;

        @NotNull
        private final String title;

        @NotNull
        private final EApplicationStep step;

        @NotNull
        private final String serialId;

        @NotNull
        private final String name;

        @NotNull
        private final String applicationDate;

        private final String endDate; // nullable

        private final String memo; // nullable

        @Builder
        public UserOwnerJobPostingOverviewDto(
                Long userOwnerJobPostingId,
                Long jobPostingId,
                String title,
                EApplicationStep step,
                String serialId,
                String name,
                String applicationDate,
                String endDate,
                String memo
        ) {
            this.userOwnerJobPostingId = userOwnerJobPostingId;
            this.jobPostingId = jobPostingId;
            this.title = title;
            this.step = step;
            this.serialId = serialId;
            this.name = name;
            this.applicationDate = applicationDate;
            this.endDate = endDate;
            this.memo = memo;
            this.validateSelf();
        }
    }

    public static ReadAdminUserOwnerJobPostingsOverviewsResponseDto of(
            Page<UserOwnerJobPosting> userOwnerJobPostingsPage
    ){
        List<UserOwnerJobPostingOverviewDto> userOwnerJobPostingOverviewDtos = userOwnerJobPostingsPage.stream()
                .map(userOwnerJobPosting -> UserOwnerJobPostingOverviewDto.builder()
                        .userOwnerJobPostingId(userOwnerJobPosting.getId())
                        .jobPostingId(userOwnerJobPosting.getJobPosting().getId())
                        .title(userOwnerJobPosting.getJobPosting().getTitle())
                        .step(userOwnerJobPosting.getStep())
                        .serialId(userOwnerJobPosting.getUser().getSerialId())
                        .name(userOwnerJobPosting.getUser().getFirstName() + " " + userOwnerJobPosting.getUser().getLastName())
                        .applicationDate(userOwnerJobPosting.getCreatedAt().toString())
                        .endDate(
                                userOwnerJobPosting.getStep().equals(EApplicationStep.RESUME_REJECTED) ||
                                userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_REJECTED) ||
                                userOwnerJobPosting.getStep().equals(EApplicationStep.APPLICATION_SUCCESS) ?
                                        DateTimeUtil.convertLocalDateToString(userOwnerJobPosting.getLastStepUpdated()) : null
                        )
                        .memo(userOwnerJobPosting.getMemo())
                        .build()
                )
                .toList();

        PageInfoDto pageInfo = PageInfoDto.of(
                userOwnerJobPostingsPage.getNumber() + 1,
                userOwnerJobPostingsPage.getNumberOfElements(),
                userOwnerJobPostingsPage.getSize(),
                userOwnerJobPostingsPage.getTotalPages(),
                (int) userOwnerJobPostingsPage.getTotalElements()
        );

        return ReadAdminUserOwnerJobPostingsOverviewsResponseDto.builder()
                .userOwnerJobPostings(userOwnerJobPostingOverviewDtos)
                .pageInfo(pageInfo)
                .build();
    }
}
