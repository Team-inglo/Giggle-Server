package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "work_preferences")
@SQLDelete(sql = "UPDATE work_preferences SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class WorkPreference extends BaseEntity {

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
    @Column(name = "job_category")
    private EJobCategory jobCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EEmploymentType employmentType;

    @Column(name = "region_1depth_name", length = 50)
    private String region1DepthName;

    @Column(name = "region_2depth_name", length = 50)
    private String region2DepthName;

    @Column(name = "region_3depth_name", length = 50)
    private String region3DepthName;

    @Column(name = "region_4depth_name", length = 50)
    private String region4DepthName;

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
    public WorkPreference(
            EJobCategory jobCategory,
            EEmploymentType employmentType,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName,
            Resume resume
    ) {
        this.jobCategory = jobCategory;
        this.employmentType = employmentType;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region4DepthName = region4DepthName;
        this.resume = resume;
    }

    public void updateWorkPreference(
            EJobCategory jobCategory,
            EEmploymentType employmentType,
            String region1DepthName,
            String region2DepthName,
            String region3DepthName,
            String region4DepthName
    ) {
        this.jobCategory = jobCategory;
        this.employmentType = employmentType;
        this.region1DepthName = region1DepthName;
        this.region2DepthName = region2DepthName;
        this.region3DepthName = region3DepthName;
        this.region4DepthName = region4DepthName;
    }

    public void checkUserValidation(UUID accountId) {
        if (!this.resume.getAccountId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_PROPRIETOR);
        }
    }
}
