package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
public class ReadOwnerResumeDetailResponseDtoV1 extends SelfValidating<ReadOwnerResumeDetailResponseDtoV1> {

    @JsonProperty("profile_img_url")
    private final String profileImgUrl;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("visa")
    private final VisaDto visa;

    @JsonProperty("personal_information")
    private final PersonalInformationDto personalInformation;

    @JsonProperty("introduction")
    private final String introduction;

    @JsonProperty("work_experience")
    private final List<WorkExperienceDto> workExperience;

    @JsonProperty("education")
    private final List<EducationDto> education;

    @JsonProperty("languages")
    private final LanguagesDto languages;

    @Builder
    public ReadOwnerResumeDetailResponseDtoV1(
            String profileImgUrl,
            String name,
            VisaDto visa,
            PersonalInformationDto personalInformation,
            String introduction,
            List<WorkExperienceDto> workExperience,
            List<EducationDto> education,
            LanguagesDto languages
    ) {
        this.profileImgUrl = profileImgUrl;
        this.name = name;
        this.visa = visa;
        this.personalInformation = personalInformation;
        this.introduction = introduction;
        this.workExperience = workExperience;
        this.education = education;
        this.languages = languages;

        this.validateSelf();
    }

    @Getter
    public static class VisaDto {

        @JsonProperty("visa")
        private final String visa;

        @JsonProperty("description")
        private final String description;

        @Builder
        public VisaDto(String visa, String description) {
            this.visa = visa;
            this.description = description;
        }

        public static VisaDto fromEntity(EVisa visa) {
            return VisaDto.builder()
                    .visa(visa.toString())
                    .description(visa.getDescription())
                    .build();
        }
    }

    @Getter
    public static class PersonalInformationDto {

        @JsonProperty("gender")
        private final EGender gender;

        @JsonProperty("nationality")
        private final String nationality;

        @JsonProperty("birth")
        private final String birth;

        @JsonProperty("main_address")
        private final String mainAddress;

        @JsonProperty("detailed_address")
        private final String detailedAddress;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @JsonProperty("email")
        private final String email;

        @Builder
        public PersonalInformationDto(EGender gender, String nationality, String birth, String mainAddress, String detailedAddress, String phoneNumber, String email) {
            this.gender = gender;
            this.nationality = nationality;
            this.birth = birth;
            this.mainAddress = mainAddress;
            this.detailedAddress = detailedAddress;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public static PersonalInformationDto fromEntity(User user) {
            return PersonalInformationDto.builder()
                    .gender(user.getGender())
                    .nationality(user.getNationality() != null ? user.getNationality().getEnName() : null)
                    .birth(user.getBirth() != null ? DateTimeUtil.convertLocalDateToDARTString(user.getBirth()) : null)
                    .mainAddress(user.getAddress() != null ? user.getAddress().getAddressName() : null)
                    .detailedAddress(user.getAddress() != null ? user.getAddress().getAddressDetail() : null)
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .build();
        }
    }

    @Getter
    public static class WorkExperienceDto {

        @JsonProperty("id")
        private final Long id;

        @JsonProperty("title")
        private final String title;

        @JsonProperty("workplace")
        private final String workplace;

        @JsonProperty("description")
        private final String description;

        @JsonProperty("start_date")
        private final String startDate;

        @JsonProperty("end_date")
        private final String endDate;

        @JsonProperty("duration")
        private final Integer duration;

        @Builder
        public WorkExperienceDto(Long id, String title, String workplace, String description, String startDate, String endDate, Integer duration) {
            this.id = id;
            this.title = title;
            this.workplace = workplace;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
            this.duration = duration;
        }

        public static WorkExperienceDto fromEntity(WorkExperience workExperience) {
            return WorkExperienceDto.builder()
                    .id(workExperience.getId())
                    .title(workExperience.getExperienceTitle())
                    .workplace(workExperience.getWorkplace())
                    .description(workExperience.getDescription())
                    .startDate(workExperience.getStartDate() != null ? DateTimeUtil.convertLocalDateToString(workExperience.getStartDate()) : null)
                    .endDate(workExperience.getEndDate() != null ? DateTimeUtil.convertLocalDateToString(workExperience.getEndDate()) : null)
                    .duration(workExperience.getEndDate() != null ?
                            (int) ChronoUnit.MONTHS.between(workExperience.getStartDate(), workExperience.getEndDate())
                            : (int) ChronoUnit.MONTHS.between(workExperience.getStartDate(), LocalDate.now()))
                    .build();
        }
    }

    @Getter
    public static class EducationDto {

        @JsonProperty("id")
        private final Long id;

        @JsonProperty("education_level")
        private final String educationLevel;

        @JsonProperty("school_name")
        private final String schoolName;

        @JsonProperty("major")
        private final String major;

        @JsonProperty("start_date")
        private final String startDate;

        @JsonProperty("end_date")
        private final String endDate;

        @JsonProperty("grade")
        private final Integer grade;

        @Builder
        public EducationDto(Long id, String educationLevel, String schoolName, String major, String startDate, String endDate, Integer grade) {
            this.id = id;
            this.educationLevel = educationLevel;
            this.schoolName = schoolName;
            this.major = major;
            this.startDate = startDate;
            this.endDate = endDate;
            this.grade = grade;
        }

        public static EducationDto fromEntity(Education education) {
            return EducationDto.builder()
                    .id(education.getId())
                    .educationLevel(education.getEducationLevel().name())
                    .schoolName(education.getSchool().getSchoolName())
                    .major(education.getMajor().getEnName())
                    .startDate(DateTimeUtil.convertLocalDateToString(education.getEnrollmentDate()))
                    .endDate(DateTimeUtil.convertLocalDateToString(education.getGraduationDate()))
                    .grade(education.getGrade())
                    .build();
        }
    }

    @Getter
    public static class LanguagesDto {

        @JsonProperty("topik")
        private final Integer topik;

        @JsonProperty("social_integration")
        private final Integer socialIntegration;

        @JsonProperty("sejong_institute")
        private final Integer sejongInstitute;

        @JsonProperty("etc")
        private final List<LanguageDetailDto> etc;

        @Builder
        public LanguagesDto(Integer topik, Integer socialIntegration, Integer sejongInstitute, List<LanguageDetailDto> etc) {
            this.topik = topik;
            this.socialIntegration = socialIntegration;
            this.sejongInstitute = sejongInstitute;
            this.etc = etc;
        }

        public static LanguagesDto fromEntity(LanguageSkill language) {
            List<LanguageDetailDto> languageDetails = language.getAdditionalLanguages().stream()
                    .map(LanguageDetailDto::fromEntity)
                    .toList();

            return LanguagesDto.builder()
                    .topik(language.getTopikLevel())
                    .socialIntegration(language.getSocialIntegrationLevel())
                    .sejongInstitute(language.getSejongInstituteLevel())
                    .etc(languageDetails)
                    .build();
        }
    }

    @Getter
    public static class LanguageDetailDto {

        @JsonProperty("id")
        private final Long id;

        @JsonProperty("language_name")
        private final String languageName;

        @JsonProperty("level")
        private final String level;

        @Builder
        public LanguageDetailDto(Long id, String languageName, String level) {
            this.id = id;
            this.languageName = languageName;
            this.level = level;
        }

        public static LanguageDetailDto fromEntity(AdditionalLanguage language) {
            return LanguageDetailDto.builder()
                    .id(language.getId())
                    .languageName(language.getLanguageName())
                    .level(language.getLevel().toString())
                    .build();
        }
    }

    public static ReadOwnerResumeDetailResponseDtoV1 of(Resume resume, List<WorkExperience> workExperiences, List<Education> educations, LanguageSkill languageSkill, User user) {
        return ReadOwnerResumeDetailResponseDtoV1.builder()
                .profileImgUrl(user.getProfileImgUrl())
                .name(user.getName())
                .visa(ReadOwnerResumeDetailResponseDtoV1.VisaDto.fromEntity(user.getVisa()))
                .personalInformation(ReadOwnerResumeDetailResponseDtoV1.PersonalInformationDto.fromEntity(user))
                .introduction(resume.getIntroduction())
                .workExperience(!workExperiences.isEmpty() ? workExperiences.stream().map(ReadOwnerResumeDetailResponseDtoV1.WorkExperienceDto::fromEntity).toList() : null)
                .education(!educations.isEmpty() ? educations.stream().map(ReadOwnerResumeDetailResponseDtoV1.EducationDto::fromEntity).toList() : null)
                .languages(ReadOwnerResumeDetailResponseDtoV1.LanguagesDto.fromEntity(languageSkill))
                .build();
    }
}
