package com.inglo.giggle.posting.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private LocalDateTime createdAt;

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

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserOwnerJobPosting> userOwnerJobPostings = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public JobPosting(String title, EJobCategory jobCategory, Integer hourlyRate, LocalDate recruitmentDeadLine,
                      EWorkPeriod workPeriod, Integer recruitmentNumber, EGender gender, Integer ageRestriction,
                      EEducationLevel educationLevel, Set<EVisa> visa, String recruiterName, String recruiterEmail,
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
        this.createdAt = LocalDateTime.now();
        this.owner = owner;
        this.address = address;
    }

    public void updateJobPosting(
            String title,
            EJobCategory jobCategory,
            Integer hourlyRate,
            LocalDate recruitmentDeadLine,
            EWorkPeriod workPeriod,
            Integer recruitmentNumber,
            EGender gender,
            Integer ageRestriction,
            EEducationLevel educationLevel,
            Set<EVisa> visa,
            String recruiterName,
            String recruiterEmail,
            String recruiterPhoneNumber,
            String description,
            String preferredConditions,
            EEmploymentType employmentType,
            Address address
    ){
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
        this.address = address;
    }

    public String getWorkDaysPerWeekToString() {
        // 협의 가능 요일이 포함되어 있는 경우
        if (workDayTimes.stream().anyMatch(dayTime -> dayTime.getDayOfWeek() == EDayOfWeek.NEGOTIABLE)) {
            return "협의 가능";
        }

        // 중복되지 않은 요일의 개수 세기
        long distinctDays = workDayTimes.stream()
                .map(PostingWorkDayTime::getDayOfWeek)
                .distinct()
                .count();

        // 작업일 수에 따라 적절한 문자열 반환
        return switch ((int) distinctDays) {
            case 1 -> "1 day per week";
            case 2 -> "2 days per week";
            case 3 -> "3 days per week";
            case 4 -> "4 days per week";
            case 5 -> "5 days per week";
            case 6 -> "6 days per week";
            case 7 -> "7 days per week";
            default -> "협의 가능";
        };
    }

    public Map<String, Integer> calculateWorkHours() {
        int weekdayHours = 0;
        int weekendHours = 0;

        for (PostingWorkDayTime workDayTime : workDayTimes) {
            if (workDayTime.getDayOfWeek() == EDayOfWeek.NEGOTIABLE || workDayTime.getWorkStartTime() == null || workDayTime.getWorkEndTime() == null) {
                continue;
            }

            Duration workDuration = Duration.between(workDayTime.getWorkStartTime(), workDayTime.getWorkEndTime());
            int hours = (int) workDuration.toHours();

            if (isWeekday(workDayTime.getDayOfWeek())) {
                weekdayHours += hours;
            } else if (isWeekend(workDayTime.getDayOfWeek())) {
                weekendHours += hours;
            }
        }

        Map<String, Integer> workHoursMap = new HashMap<>();
        workHoursMap.put("weekdayWorkHours", weekdayHours);
        workHoursMap.put("weekendWorkHours", weekendHours);

        return workHoursMap;
    }

    private boolean isWeekday(EDayOfWeek dayOfWeek) {
        return dayOfWeek == EDayOfWeek.MONDAY || dayOfWeek == EDayOfWeek.TUESDAY ||
                dayOfWeek == EDayOfWeek.WEDNESDAY || dayOfWeek == EDayOfWeek.THURSDAY ||
                dayOfWeek == EDayOfWeek.FRIDAY;
    }

    private boolean isWeekend(EDayOfWeek dayOfWeek) {
        return dayOfWeek == EDayOfWeek.SATURDAY || dayOfWeek == EDayOfWeek.SUNDAY;
    }
}

