package com.inglo.giggle.posting.persistence.entity;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_postings")
@SQLDelete(sql = "UPDATE job_postings SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class JobPostingEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_category", nullable = false)
    private EJobCategory jobCategory;

    @Column(name = "hourly_rate", nullable = false)
    private Integer hourlyRate;

    @Column(name = "recruitment_dead_line")
    private LocalDate recruitmentDeadLine;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_period", nullable = false)
    private EWorkPeriod workPeriod;

    @Column(name = "recruitment_number")
    private Integer recruitmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private EGender gender;

    @Column(name = "age_restriction")
    private Integer ageRestriction;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EEducationLevel educationLevel;

    @ElementCollection(targetClass = EVisa.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "job_posting_visas", joinColumns = @JoinColumn(name = "job_posting_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "visa")
    private Set<EVisa> visa = new HashSet<>();

    @Column(name = "recruiter_name", length = 10, nullable = false)
    private String recruiterName;

    @Column(name = "recruiter_email", length = 320, nullable = false)
    private String recruiterEmail;

    @Column(name = "recruiter_phone_number", length = 20, nullable = false)
    private String recruiterPhoneNumber;

    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "preferred_conditions", length = 50)
    private String preferredConditions;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EEmploymentType employmentType;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private AddressEntity addressEntity;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @Column(name = "owners_id", nullable = false)
    private UUID ownerId;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public JobPostingEntity(Long id, String title, EJobCategory jobCategory, Integer hourlyRate, LocalDate recruitmentDeadLine,
                            EWorkPeriod workPeriod, Integer recruitmentNumber, EGender gender, Integer ageRestriction,
                            EEducationLevel educationLevel, Set<EVisa> visa, String recruiterName, String recruiterEmail,
                            String recruiterPhoneNumber, String description, String preferredConditions,
                            EEmploymentType employmentType, AddressEntity addressEntity, UUID ownerId) {
        this.id = id;
        this.title = title;
        this.jobCategory = jobCategory;
        this.hourlyRate = hourlyRate;
        this.recruitmentDeadLine = recruitmentDeadLine;
        this.workPeriod = workPeriod;
        this.recruitmentNumber = recruitmentNumber;
        this.gender = gender;
        this.ageRestriction = ageRestriction;
        this.educationLevel = educationLevel;
        this.visa = visa;
        this.recruiterName = recruiterName;
        this.recruiterEmail = recruiterEmail;
        this.recruiterPhoneNumber = recruiterPhoneNumber;
        this.description = description;
        this.preferredConditions = preferredConditions;
        this.employmentType = employmentType;
        this.addressEntity = addressEntity;
        this.ownerId = ownerId;
    }
}

