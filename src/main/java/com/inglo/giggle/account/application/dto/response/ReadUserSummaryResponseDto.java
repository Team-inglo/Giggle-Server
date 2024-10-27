package com.inglo.giggle.account.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.ResumeAggregate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class ReadUserSummaryResponseDto extends SelfValidating<ReadUserSummaryResponseDto> {

    @NotNull(message = "user_information은 null일 수 없습니다.")
    @JsonProperty("user_information")
    private final UserInfoDto userInformation;

    @NotNull(message = "language_level은 null일 수 없습니다.")
    @JsonProperty("language_level")
    private final LanguageLevelDto languageLevel;

    @NotNull(message = "meta_data는 null일 수 없습니다.")
    @JsonProperty("meta_data")
    private final MetaDataDto metaData;

    @Builder
    public ReadUserSummaryResponseDto(
            UserInfoDto userInformation,
            LanguageLevelDto languageLevel,
            MetaDataDto metaData
    ) {
        this.userInformation = userInformation;
        this.languageLevel = languageLevel;
        this.metaData = metaData;

        this.validateSelf();
    }

    public static ReadUserSummaryResponseDto of(ResumeAggregate resumeAggregate) {
        return ReadUserSummaryResponseDto.builder()
                .userInformation(UserInfoDto.of(resumeAggregate.getUser(), resumeAggregate.getEducation()))
                .languageLevel(LanguageLevelDto.fromEntity(resumeAggregate.getResume()))
                .metaData(MetaDataDto.of(resumeAggregate))
                .build();
    }

    @Getter
    public static class UserInfoDto {

        @NotBlank(message = "profile_img_url은 null일 수 없습니다.")
        @JsonProperty("profile_img_url")
        private final String profileImgUrl;

        @NotBlank(message = "first_name은 null일 수 없습니다.")
        @JsonProperty("first_name")
        private final String firstName;

        @NotBlank(message = "last_name은 null일 수 없습니다.")
        @JsonProperty("last_name")
        private final String lastName;

        @NotBlank(message = "birth는 null일 수 없습니다.")
        @JsonProperty("birth")
        private final String birth;

        @NotBlank(message = "school_name은 null일 수 없습니다.")
        @JsonProperty("school_name")
        private final String schoolName;

        @NotNull(message = "grade는 null일 수 없습니다.")
        @JsonProperty("grade")
        private final Integer grade;

        @NotNull(message = "gpa는 null일 수 없습니다.")
        @JsonProperty("gpa")
        private final Double gpa;

        @NotNull(message = "is_notification_allowed는 null일 수 없습니다.")
        @JsonProperty("is_notification_allowed")
        private final Boolean isNotificationAllowed;

        @Builder
        public UserInfoDto(
                String profileImgUrl,
                String firstName,
                String lastName,
                String birth,
                String schoolName,
                Integer grade,
                Double gpa,
                Boolean isNotificationAllowed
        ) {
            this.profileImgUrl = profileImgUrl;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birth = birth;
            this.schoolName = schoolName;
            this.grade = grade;
            this.gpa = gpa;
            this.isNotificationAllowed = isNotificationAllowed;
        }

        public static UserInfoDto of(User user, Education education) {
            String schoolName = " - ";
            Integer grade = 0;
            Double gpa = 0.0;
            if (education != null) {
                schoolName = education.getSchool().getSchoolName();
                grade = education.getGrade();
                gpa = education.getGpa();
            }
            return UserInfoDto.builder()
                    .profileImgUrl(user.getProfileImgUrl())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .birth(user.getBirth().toString())
                    .schoolName(schoolName)
                    .grade(grade)
                    .gpa(gpa)
                    .isNotificationAllowed(user.getNotificationAllowed())
                    .build();
        }
    }

    @Getter
    public static class LanguageLevelDto {

        @NotNull(message = "topik_level은 null일 수 없습니다.")
        @JsonProperty("topik_level")
        private final Integer topikLevel;

        @NotNull(message = "kiip_level은 null일 수 없습니다.")
        @JsonProperty("kiip_level")
        private final Integer kiipLevel;

        @NotNull(message = "sejong_level은 null일 수 없습니다.")
        @JsonProperty("sejong_level")
        private final Integer sejongLevel;

        @Builder
        public LanguageLevelDto(
                Integer topikLevel,
                Integer kiipLevel,
                Integer sejongLevel
        ) {
            this.topikLevel = topikLevel;
            this.kiipLevel = kiipLevel;
            this.sejongLevel = sejongLevel;
        }

        public static LanguageLevelDto fromEntity(Resume resume) {
            return LanguageLevelDto.builder()
                    .topikLevel(resume.getLanguageSkill().getTopikLevel())
                    .kiipLevel(resume.getLanguageSkill().getSocialIntegrationLevel())
                    .sejongLevel(resume.getLanguageSkill().getSejongInstituteLevel())
                    .build();
        }
    }

    @Getter
    public static class MetaDataDto {

        @NotNull(message = "weekend_work_hour은 null일 수 없습니다.")
        @JsonProperty("weekend_work_hour")
        private final Integer weekendWorkHour;

        @NotNull(message = "weekday_work_hour은 null일 수 없습니다.")
        @JsonProperty("weekday_work_hour")
        private final Integer weekdayWorkHour;

        @NotNull(message = "is_topik_4_or_more은 null일 수 없습니다.")
        @JsonProperty("is_topik_4_or_more")
        private final Boolean isTopik4OrMore;

        @NotNull(message = "is_metropolitan_area은 null일 수 없습니다.")
        @JsonProperty("is_metropolitan_area")
        private final Boolean isMetropolitanArea;

        @Builder
        public MetaDataDto(
                Integer weekendWorkHour,
                Integer weekdayWorkHour,
                Boolean isTopik4OrMore,
                Boolean isMetropolitanArea
        ) {
            this.weekendWorkHour = weekendWorkHour;
            this.weekdayWorkHour = weekdayWorkHour;
            this.isTopik4OrMore = isTopik4OrMore;
            this.isMetropolitanArea = isMetropolitanArea;
        }

        public static final String WEEKEND_WORK_HOURS = "weekendWorkHours";
        public static final String WEEKDAY_WORK_HOURS = "weekdayWorkHours";

        public static MetaDataDto of(ResumeAggregate resumeAggregate) {

            Map<String, Integer> stringIntegerMap = resumeAggregate.calculateWorkHours();

            return MetaDataDto.builder()
                    .weekendWorkHour(stringIntegerMap.get(WEEKEND_WORK_HOURS))
                    .weekdayWorkHour(stringIntegerMap.get(WEEKDAY_WORK_HOURS))
                    .isTopik4OrMore(resumeAggregate.getResume().getLanguageSkill().getTopikLevel() >= 4)
                    .isMetropolitanArea(resumeAggregate.getEducation().getSchool().getIsMetropolitan())
                    .build();
        }
    }
}
