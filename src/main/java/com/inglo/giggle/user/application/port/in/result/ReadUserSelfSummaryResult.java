package com.inglo.giggle.user.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserSelfSummaryResult extends SelfValidating<ReadUserSelfSummaryResult> {

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
    public ReadUserSelfSummaryResult(
            UserInfoDto userInformation,
            LanguageLevelDto languageLevel,
            MetaDataDto metaData
    ) {
        this.userInformation = userInformation;
        this.languageLevel = languageLevel;
        this.metaData = metaData;

        this.validateSelf();
    }

    public static ReadUserSelfSummaryResult of(
            String profileImgUrl,
            String firstName,
            String lastName,
            String birth,
            String schoolName,
            Integer grade,
            Double gpa,
            Boolean isNotificationAllowed,
            Integer topikLevel,
            Integer kiipLevel,
            Integer sejongLevel,
            Integer weekendWorkHour,
            Integer weekdayWorkHour,
            Boolean isLanguageSkill4OrMore,
            Boolean isMetropolitanArea
    ) {
        return ReadUserSelfSummaryResult.builder()
                .userInformation(UserInfoDto.of(profileImgUrl, firstName, lastName, birth, schoolName, grade, gpa, isNotificationAllowed))
                .languageLevel(LanguageLevelDto.of(topikLevel, kiipLevel, sejongLevel))
                .metaData(MetaDataDto.of(weekendWorkHour, weekdayWorkHour, isLanguageSkill4OrMore, isMetropolitanArea))
                .build();
    }

    @Getter
    public static class UserInfoDto extends SelfValidating<UserInfoDto> {

        @NotBlank(message = "profile_img_url은 null일 수 없습니다.")
        @JsonProperty("profile_img_url")
        private final String profileImgUrl;

        @NotBlank(message = "first_name은 null일 수 없습니다.")
        @JsonProperty("first_name")
        private final String firstName;

        @NotBlank(message = "last_name은 null일 수 없습니다.")
        @JsonProperty("last_name")
        private final String lastName;

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

        public static UserInfoDto of(
                String profileImgUrl,
                String firstName,
                String lastName,
                String birth,
                String schoolName,
                Integer grade,
                Double gpa,
                Boolean isNotificationAllowed
        ) {

            return UserInfoDto.builder()
                    .profileImgUrl(profileImgUrl)
                    .firstName(firstName)
                    .lastName(lastName)
                    .birth(birth)
                    .schoolName(schoolName)
                    .grade(grade)
                    .gpa(gpa)
                    .isNotificationAllowed(isNotificationAllowed)
                    .build();
        }
    }

    @Getter
    public static class LanguageLevelDto extends SelfValidating<LanguageLevelDto> {

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

        public static LanguageLevelDto of(Integer topikLevel, Integer kiipLevel, Integer sejongLevel) {
            return LanguageLevelDto.builder()
                    .topikLevel(topikLevel)
                    .kiipLevel(kiipLevel)
                    .sejongLevel(sejongLevel)
                    .build();
        }
    }

    @Getter
    public static class MetaDataDto extends SelfValidating<MetaDataDto> {

        @NotNull(message = "weekend_work_hour은 null일 수 없습니다.")
        @JsonProperty("weekend_work_hour")
        private final Integer weekendWorkHour;

        @NotNull(message = "weekday_work_hour은 null일 수 없습니다.")
        @JsonProperty("weekday_work_hour")
        private final Integer weekdayWorkHour;

        @NotNull(message = "is_topik_4_or_more은 null일 수 없습니다.")
        @JsonProperty("is_language_skill_4_or_more")
        private final Boolean isLanguageSkill4OrMore;

        @NotNull(message = "is_metropolitan_area은 null일 수 없습니다.")
        @JsonProperty("is_metropolitan_area")
        private final Boolean isMetropolitanArea;

        @Builder
        public MetaDataDto(
                Integer weekendWorkHour,
                Integer weekdayWorkHour,
                Boolean isLanguageSkill4OrMore,
                Boolean isMetropolitanArea
        ) {
            this.weekendWorkHour = weekendWorkHour;
            this.weekdayWorkHour = weekdayWorkHour;
            this.isLanguageSkill4OrMore = isLanguageSkill4OrMore;
            this.isMetropolitanArea = isMetropolitanArea;
        }

        public static MetaDataDto of(
                Integer weekendWorkHour,
                Integer weekdayWorkHour,
                Boolean isLanguageSkill4OrMore,
                Boolean isMetropolitanArea
        ) {
            return MetaDataDto.builder()
                    .weekendWorkHour(weekendWorkHour)
                    .weekdayWorkHour(weekdayWorkHour)
                    .isLanguageSkill4OrMore(isLanguageSkill4OrMore)
                    .isMetropolitanArea(isMetropolitanArea)
                    .build();
        }
    }
}
