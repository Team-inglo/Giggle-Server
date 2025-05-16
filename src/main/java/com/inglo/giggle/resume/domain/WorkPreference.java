package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "work_preferences")
@SQLDelete(sql = "UPDATE work_preferences SET deleted_at = CURRENT_TIMESTAMP WHERE resume_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class WorkPreference extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "resume_id")
    private UUID resumeId;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @ElementCollection(targetClass = EJobCategory.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "preference_job_categories", joinColumns = @JoinColumn(name = "preference_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "job_category")
    private Set<EJobCategory> jobCategories = new HashSet<>();

    @ElementCollection(targetClass = EEmploymentType.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "preference_employment_types", joinColumns = @JoinColumn(name = "preference_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private Set<EEmploymentType> employmentTypes = new HashSet<>();

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "workPreference", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PreferenceAddress> preferenceAddresses = new HashSet<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public WorkPreference(
            Resume resume,
            Set<EJobCategory> jobCategories,
            Set<EEmploymentType> employmentTypes,
            Set<PreferenceAddress> preferenceAddresses
    ) {
        this.resume = resume;
        this.jobCategories = jobCategories;
        this.employmentTypes = employmentTypes;
        this.preferenceAddresses = preferenceAddresses;
    }

    public void checkUserValidation(UUID accountId) {
        if (!this.resume.getAccountId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_PROPRIETOR);
        }
    }

    public void updateSelf(
            Set<EJobCategory> jobCategories,
            Set<EEmploymentType> employmentTypes,
            Set<PreferenceAddress> preferenceAddresses
    ) {
        this.jobCategories = jobCategories;
        this.employmentTypes = employmentTypes;
        this.preferenceAddresses.clear();
        this.preferenceAddresses.addAll(preferenceAddresses);
    }
}
