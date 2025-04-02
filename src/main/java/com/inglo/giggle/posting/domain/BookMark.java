package com.inglo.giggle.posting.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
public class BookMark extends BaseDomain {
    private final Long id;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private final UUID userId;
    private final Long jobPostingId;

    /* -------------------------------------------- */
    /* Nested class ------------------------------- */
    /* -------------------------------------------- */
    private final JobPostingInfo jobPostingInfo;

    @Builder
    public BookMark(Long id, UUID userId, Long jobPostingId, JobPostingInfo jobPostingInfo, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.userId = userId;
        this.jobPostingId = jobPostingId;
        this.jobPostingInfo = jobPostingInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    @Getter
    public static class JobPostingInfo {
        private Long id;
        private String title;
        private Integer hourlyRate;
        private LocalDate recruitmentDeadLine;
        private Address address;
        private EWorkPeriod workPeriod;
        private String workDaysPerWeek;
        private Set<EVisa> visa;
        private EJobCategory jobCategory;
        private LocalDateTime createdAt;
        private OwnerInfo ownerInfo;

        @Builder
        public JobPostingInfo(Long id, String title, Integer hourlyRate, LocalDate recruitmentDeadLine, Address address, EWorkPeriod workPeriod, String workDaysPerWeek, Set<EVisa> visa, EJobCategory jobCategory, LocalDateTime createdAt, OwnerInfo ownerInfo) {
            this.id = id;
            this.title = title;
            this.hourlyRate = hourlyRate;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.address = address;
            this.workPeriod = workPeriod;
            this.workDaysPerWeek = workDaysPerWeek;
            this.visa = visa;
            this.jobCategory = jobCategory;
            this.createdAt = createdAt;
            this.ownerInfo = ownerInfo;
        }

        @Getter
        public static class OwnerInfo {
            private String profileImgUrl;

            @Builder
            public OwnerInfo(String profileImgUrl) {
                this.profileImgUrl = profileImgUrl;
            }
        }
    }

}
