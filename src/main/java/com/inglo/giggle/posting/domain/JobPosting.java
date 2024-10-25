package com.inglo.giggle.posting.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job_postings")
public class JobPosting {

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

    @Column(name = "recruitment_number", nullable = false)
    private Integer recruitmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private EGender gender;

    @Column(name = "age_restriction", nullable = false)
    private Integer ageRestriction;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false)
    private EEducationLevel educationLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "visa")
    private EVisa visa;

    @Column(name = "recruiter_name", length = 10, nullable = false)
    private String recruiterName;

    @Column(name = "recruiter_email", length = 320, nullable = false)
    private String recruiterEmail;

    @Column(name = "recruiter_phone_number", length = 20, nullable = false)
    private String recruiterPhoneNumber;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "preferred_conditions", length = 50)
    private String preferredConditions;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EEmploymentType employmentType;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private Address address;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owners_id", nullable = false)
    private Owner owner;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostingWorkDayTime> workDayTimes = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyImage> companyImages = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public JobPosting(String title, EJobCategory jobCategory, Integer hourlyRate, LocalDate recruitmentDeadLine,
                      EWorkPeriod workPeriod, Integer recruitmentNumber, EGender gender, Integer ageRestriction,
                      EEducationLevel educationLevel, EVisa visa, String recruiterName, String recruiterEmail,
                      String recruiterPhoneNumber, String description, String preferredConditions,
                      EEmploymentType employmentType, Owner owner, Address address) {
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
        this.createdAt = LocalDate.now();
        this.owner = owner;
        this.address = address;
    }

}