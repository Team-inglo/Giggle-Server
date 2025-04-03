package com.inglo.giggle.posting.persistence.entity;

import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owners_id", nullable = false)
    private OwnerEntity ownerEntity;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "jobPostingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostingWorkDayTimeEntity> workDayTimeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobPostingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyImageEntity> companyImageEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobPostingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkEntity> bookMarkEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobPostingEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserOwnerJobPostingEntity> userOwnerJobPostingEntities = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public JobPostingEntity(String title, EJobCategory jobCategory, Integer hourlyRate, LocalDate recruitmentDeadLine,
                            EWorkPeriod workPeriod, Integer recruitmentNumber, EGender gender, Integer ageRestriction,
                            EEducationLevel educationLevel, Set<EVisa> visa, String recruiterName, String recruiterEmail,
                            String recruiterPhoneNumber, String description, String preferredConditions,
                            EEmploymentType employmentType, OwnerEntity ownerEntity, AddressEntity addressEntity,
                            List<PostingWorkDayTimeEntity> workDayTimeEntities, List<CompanyImageEntity> companyImageEntities, List<BookMarkEntity> bookMarkEntities,
                            List<UserOwnerJobPostingEntity> userOwnerJobPostingEntities) {
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
        this.ownerEntity = ownerEntity;
        this.addressEntity = addressEntity;
        this.workDayTimeEntities = workDayTimeEntities;
        this.companyImageEntities = companyImageEntities;
        this.bookMarkEntities = bookMarkEntities;
        this.userOwnerJobPostingEntities = userOwnerJobPostingEntities;
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
            AddressEntity addressEntity
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
        this.addressEntity = addressEntity;
    }

    public String getWorkDaysPerWeekToString() {
        // 협의 가능 요일이 포함되어 있는 경우
        if (workDayTimeEntities.stream().anyMatch(dayTime -> dayTime.getDayOfWeek() == EDayOfWeek.NEGOTIABLE)) {
            return "협의 가능";
        }

        // 중복되지 않은 요일의 개수 세기
        long distinctDays = workDayTimeEntities.stream()
                .map(PostingWorkDayTimeEntity::getDayOfWeek)
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

        for (PostingWorkDayTimeEntity workDayTime : workDayTimeEntities) {
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

    public void updatePostWorkDayTimes(List<PostingWorkDayTimeEntity> workDayTimes) {
        this.workDayTimeEntities.clear();
        this.workDayTimeEntities.addAll(workDayTimes);
    }

    public void fetchOwner(OwnerEntity ownerEntity) {
        this.ownerEntity = ownerEntity;
    }
}

