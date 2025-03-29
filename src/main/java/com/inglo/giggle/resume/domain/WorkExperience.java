package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "work_experiences")
@SQLDelete(sql = "UPDATE work_experiences SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class WorkExperience extends BaseEntity {

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

    @Column(name = "description", length = 200, nullable = false)
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

    public void updateWorkExperienceTitle(String experienceTitle) {
        this.experienceTitle = experienceTitle;
    }

    public void updateWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}