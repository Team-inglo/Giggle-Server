package com.inglo.giggle.posting.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public class JobPosting extends BaseDomain {
    private Long id;
    private String title;
    private EJobCategory jobCategory;
    private Integer hourlyRate;
    private LocalDate recruitmentDeadLine;
    private EWorkPeriod workPeriod;
    private Integer recruitmentNumber;
    private EGender gender;
    private Integer ageRestriction;
    private EEducationLevel educationLevel;
    private Set<EVisa> visa;
    private String recruiterName;
    private String recruiterEmail;
    private String recruiterPhoneNumber;
    private String description;
    private String preferredConditions;
    private EEmploymentType employmentType;
    private Address address;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID ownerId;

    @Builder
    public JobPosting(Long id, String title, EJobCategory jobCategory, Integer hourlyRate, LocalDate recruitmentDeadLine,
                      EWorkPeriod workPeriod, Integer recruitmentNumber, EGender gender, Integer ageRestriction,
                      EEducationLevel educationLevel, Set<EVisa> visa, String recruiterName, String recruiterEmail,
                      String recruiterPhoneNumber, String description, String preferredConditions,
                      EEmploymentType employmentType, Address address,
                      UUID ownerId,
                      LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
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
        this.address = address;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    @Getter
    public static class OwnerInfo {
        private UUID id;
        private String profileImgUrl;
        private String companyName;
        private String name;
        private Address address;

        @Builder
        public OwnerInfo(UUID id, String profileImgUrl, String companyName, String name, Address address) {
            this.id = id;
            this.profileImgUrl = profileImgUrl;
            this.companyName = companyName;
            this.name = name;
            this.address = address;
        }
    }

    // TODO: 애그리게이트로 이동
    public String getWorkDaysPerWeekToString(List<PostingWorkDayTime> workDayTimes) {
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

    // TODO: 애그리게이트로 이동
    public String getWorkDaysPerWeekDescription(List<PostingWorkDayTime> workDayTimes) {
        if (workDayTimes.stream().anyMatch(d -> d.getDayOfWeek() == EDayOfWeek.NEGOTIABLE)) return "협의 가능";
        return workDayTimes.stream().map(PostingWorkDayTime::getDayOfWeek).distinct().count() + " days per week";
    }

    // TODO: 애그리게이트로 이동
    public Map<String, Integer> calculateWorkHours(List<PostingWorkDayTime> workDayTimes) {
        int weekday = 0, weekend = 0;
        for (PostingWorkDayTime time : workDayTimes) {
            if (time.getWorkStartTime() == null || time.getWorkEndTime() == null) continue;
            int hours = (int) Duration.between(time.getWorkStartTime(), time.getWorkEndTime()).toHours();
            if (isWeekday(time.getDayOfWeek())) weekday += hours;
            else if (isWeekend(time.getDayOfWeek())) weekend += hours;
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("weekdayWorkHours", weekday);
        result.put("weekendWorkHours", weekend);
        return result;
    }

    private boolean isWeekday(EDayOfWeek d) {
        return Set.of(EDayOfWeek.MONDAY, EDayOfWeek.TUESDAY, EDayOfWeek.WEDNESDAY, EDayOfWeek.THURSDAY, EDayOfWeek.FRIDAY).contains(d);
    }

    private boolean isWeekend(EDayOfWeek d) {
        return d == EDayOfWeek.SATURDAY || d == EDayOfWeek.SUNDAY;
    }

    // TODO: 애그리게이트로 이동
//    public void validateUpdateJobPosting(Owner owner) {
//        if (!ownerInfo.getId().equals(owner.getId())) {
//            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
//        }
//    }

    public void updateSelf(
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
    ) {
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
}

