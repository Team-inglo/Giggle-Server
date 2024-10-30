package com.inglo.giggle.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "work_experiences")
public class WorkExperience {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "experience_title", length = 20, nullable = false)
    private String experienceTitle;

    @Column(name = "workplace", length = 20, nullable = false)
    private String workplace;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date") // null인 경우 진행중인 일
    private LocalDate endDate;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public WorkExperience(String experienceTitle, String workplace, LocalDate startDate, LocalDate endDate, String description, Resume resume) {
        this.experienceTitle = experienceTitle;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.resume = resume;
    }

}