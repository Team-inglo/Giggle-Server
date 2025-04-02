package com.inglo.giggle.resume.persistence.entity;

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
public class WorkExperienceEntity extends BaseEntity {

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
    private ResumeEntity resumeEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public WorkExperienceEntity(String experienceTitle, String workplace, LocalDate startDate, LocalDate endDate, String description, ResumeEntity resumeEntity) {
        this.experienceTitle = experienceTitle;
        this.workplace = workplace;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.resumeEntity = resumeEntity;
    }

    public void fetchResumeEntity(ResumeEntity resumeEntity) {
        this.resumeEntity = resumeEntity;
    }
}