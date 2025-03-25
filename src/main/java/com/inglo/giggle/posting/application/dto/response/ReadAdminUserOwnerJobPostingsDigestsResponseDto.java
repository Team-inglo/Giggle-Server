package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReadAdminUserOwnerJobPostingsDigestsResponseDto extends SelfValidating<ReadAdminUserOwnerJobPostingsDigestsResponseDto> {

    @NotNull
    private final GraphDto graph;

    @NotNull
    private final CountInfoDto applyInfo;

    @NotNull
    private final AcceptanceRateDto acceptPerRejectRatio;

    @Builder
    public ReadAdminUserOwnerJobPostingsDigestsResponseDto(
            GraphDto graph,
            CountInfoDto applyInfo,
            AcceptanceRateDto acceptPerRejectRatio
    ) {
        this.graph = graph;
        this.applyInfo = applyInfo;
        this.acceptPerRejectRatio = acceptPerRejectRatio;
        this.validateSelf();
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GraphDto extends SelfValidating<GraphDto> {

        @NotNull
        private final List<GraphDetailDto> totalApply;

        @NotNull
        private final List<GraphDetailDto> resumeAccept;

        @NotNull
        private final List<GraphDetailDto> resumeReject;

        @NotNull
        private final List<GraphDetailDto> applicationSuccess;

        @Builder
        public GraphDto(
                List<GraphDetailDto> totalApply,
                List<GraphDetailDto> resumeAccept,
                List<GraphDetailDto> resumeReject,
                List<GraphDetailDto> applicationSuccess
        ) {
            this.totalApply = totalApply;
            this.resumeAccept = resumeAccept;
            this.resumeReject = resumeReject;
            this.applicationSuccess = applicationSuccess;
            this.validateSelf();
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GraphDetailDto extends SelfValidating<GraphDetailDto> {

        @NotNull
        private final Integer year;

        @NotNull
        private final Integer month;

        @NotNull
        private final Integer count;

        @Builder
        public GraphDetailDto(Integer year, Integer month, Integer count) {
            this.year = year;
            this.month = month;
            this.count = count;
            this.validateSelf();
        }

        public static GraphDetailDto of(Integer year, Integer month, Integer count) {
            return GraphDetailDto.builder()
                    .year(year)
                    .month(month)
                    .count(count)
                    .build();
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AcceptanceRateDto extends SelfValidating<AcceptanceRateDto> {

        @NotNull
        private final Double accept;

        @NotNull
        private final Double reject;

        @Builder
        public AcceptanceRateDto(Double accept, Double reject) {
            this.accept = accept;
            this.reject = reject;
            this.validateSelf();
        }

        public static AcceptanceRateDto of(Double accept, Double reject) {
            return AcceptanceRateDto.builder()
                    .accept(accept)
                    .reject(reject)
                    .build();
        }
    }

    public static ReadAdminUserOwnerJobPostingsDigestsResponseDto of(
            List<UserOwnerJobPostingRepository.GraphStats> currentStats,
            int priorTotalCount
    ) {
        List<GraphDetailDto> totalApply = new ArrayList<>();
        List<GraphDetailDto> resumeAccept = new ArrayList<>();
        List<GraphDetailDto> resumeReject = new ArrayList<>();
        List<GraphDetailDto> applicationSuccess = new ArrayList<>();

        int totalCount = 0;
        int acceptCount = 0;
        int rejectCount = 0;
        int successCount = 0;

        for (UserOwnerJobPostingRepository.GraphStats stat : currentStats) {
            int year = stat.year();
            int month = stat.month();

            int total = stat.total().intValue();
            int accepted = stat.accepted().intValue();
            int rejected = stat.rejected().intValue();
            int success = stat.success().intValue();

            if(total != 0){
                totalApply.add(
                        GraphDetailDto.of(
                                year,
                                month,
                                total)
                );
            }

            if(accepted != 0){
                resumeAccept.add(
                        GraphDetailDto.of(
                                year,
                                month,
                                accepted)
                );
            }

            if(rejected != 0){
                resumeReject.add(
                        GraphDetailDto.of(
                                year,
                                month,
                                rejected)
                );
            }

            if(success != 0){
                applicationSuccess.add(
                        GraphDetailDto.of(
                                year,
                                month,
                                success)
                );
            }

            totalCount += total;
            acceptCount += accepted;
            rejectCount += rejected;
            successCount += success;
        }

        double acceptRatio = acceptCount + rejectCount == 0 ? 0.0 : (double) acceptCount / (acceptCount + rejectCount);
        double rejectRatio = acceptCount + rejectCount == 0 ? 0.0 : (double) rejectCount / (acceptCount + rejectCount);

        double comparison;
        if (priorTotalCount == 0) {
            comparison = totalCount > 0 ? 100.0 : 0.0;
        } else {
            comparison = ((double) (totalCount - priorTotalCount) / priorTotalCount) * 100;
        }

        CountInfoDto applyInfo = CountInfoDto.of(
                totalCount,
                priorTotalCount,
                Math.round(comparison * 100.0) / 100.0
        );

        return ReadAdminUserOwnerJobPostingsDigestsResponseDto.builder()
                .graph(GraphDto.builder()
                        .totalApply(totalApply)
                        .resumeAccept(resumeAccept)
                        .resumeReject(resumeReject)
                        .applicationSuccess(applicationSuccess)
                        .build())
                .applyInfo(applyInfo)
                .acceptPerRejectRatio(AcceptanceRateDto.of(acceptRatio, rejectRatio))
                .build();
    }
}
