package com.inglo.giggle.posting.domain;

import com.inglo.giggle.core.type.EDayOfWeek;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posting_work_day_times")
public class PostingWorkDayTime {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private EDayOfWeek dayOfWeek;

    @Column(name = "work_start_time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time")
    private LocalTime workEndTime;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public PostingWorkDayTime(EDayOfWeek dayOfWeek, LocalTime workStartTime, LocalTime workEndTime, JobPosting jobPosting) {
        this.dayOfWeek = dayOfWeek;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.jobPosting = jobPosting;
    }
}