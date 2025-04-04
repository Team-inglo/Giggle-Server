package com.inglo.giggle.resume.domain;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeAggregate extends BaseDomain {

    private Resume resume;
    private LanguageSkill languageSkill;
    private List<Education> educations;
    private List<WorkExperience> workExperiences;
    private List<AdditionalLanguage> additionalLanguages;

    @Builder
    public ResumeAggregate(
            Resume resume,
            LanguageSkill languageSkill,
            List<Education> educations,
            List<WorkExperience> workExperiences,
            List<AdditionalLanguage> additionalLanguages
    ) {
        this.resume = resume;
        this.languageSkill = languageSkill;
        this.educations = educations;
        this.workExperiences = workExperiences;
        this.additionalLanguages = additionalLanguages;
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        get                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public AdditionalLanguage getAdditionalLanguage(Long additionalLanguageId) {
        return this.additionalLanguages.stream()
                .filter(
                        additionalLanguage -> additionalLanguage.getId().equals(additionalLanguageId)
                )
                .findFirst()
                .orElse(null);
    }

    public Education getEducation(Long educationId) {
        return this.educations.stream()
                .filter(
                        education -> education.getId().equals(educationId)
                )
                .findFirst()
                .orElse(null);
    }

    public WorkExperience getWorkExperience(Long workExperienceId) {
        return this.workExperiences.stream()
                .filter(
                        workExperience -> workExperience.getId().equals(workExperienceId)
                )
                .findFirst()
                .orElse(null);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        add                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void addAdditionalLanguage(String languageName, Integer level, UUID languageSkillId) {
        AdditionalLanguage additionalLanguage = AdditionalLanguage.builder()
                .languageName(languageName)
                .level(level)
                .languageSkillId(languageSkillId)
                .build();
        this.additionalLanguages.add(additionalLanguage);
    }

    public void addEducation(
            EEducationLevel level,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            Long schoolId,
            UUID resumeId
    ) {
        Education education = Education.builder()
                .educationLevel(level)
                .major(major)
                .gpa(gpa)
                .enrollmentDate(enrollmentDate)
                .graduationDate(graduationDate)
                .grade(grade)
                .schoolId(schoolId)
                .resumeId(resumeId)
                .build();
        this.educations.add(education);
    }

    public void addWorkExperience(
            String experienceTitle,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description,
            UUID resumeId
    ) {
        WorkExperience workExperience = WorkExperience.builder()
                .experienceTitle(experienceTitle)
                .workplace(workplace)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .resumeId(resumeId)
                .build();
        this.workExperiences.add(workExperience);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      update                                                     -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void clearResumeIntroduction() {
        this.resume.clearIntroduction();
    }

    public void updateResumeIntroduction(String introduction) {
        this.resume.updateIntroduction(introduction);
    }

    public void updateAdditionalLanguage(
            Long additionalLanguageId,
            String languageName,
            Integer level
    ) {
        AdditionalLanguage additionalLanguage = this.additionalLanguages.stream()
                .filter(
                        additionalLang -> additionalLang.getId().equals(additionalLanguageId)
                )
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ADDITIONAL_LANGUAGE)
                );
        if (additionalLanguage != null) {
            additionalLanguage.updateSelf(languageName, level);
        }
    }

    public void updateEducation(
            Long id,
            EEducationLevel level,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            Long schoolId
    ) {
        Education education = this.educations.stream()
                .filter(
                        edu -> edu.getId().equals(id)
                )
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION)
                );
        if (education != null) {
            education.updateSelf(level, major, gpa, enrollmentDate, graduationDate, grade, schoolId);
        }
    }

    public void updateWorkExperience(
            Long id,
            String experienceTitle,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description
    ) {
        WorkExperience workExperience = this.workExperiences.stream()
                .filter(
                        workExp -> workExp.getId().equals(id)
                )
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE)
                );
        if (workExperience != null) {
            workExperience.updateSelf(experienceTitle, workplace, startDate, endDate, description);
        }
    }

    public void updateLanguageSkillSejongInstituteLevel(Integer sejongInstituteLevel) {
        this.languageSkill.updateSejongInstituteLevel(sejongInstituteLevel);
    }

    public void updateLanguageSkillSocialIntegrationLevel(Integer socialIntegrationLevel) {
        this.languageSkill.updateSocialIntegrationLevel(socialIntegrationLevel);
    }

    public void updateLanguageSkillTopikLevel(Integer topikLevel) {
        this.languageSkill.updateTopikLevel(topikLevel);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      validate                                                   -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void checkIsExistAdditionalLanguage(String language) {
        if (this.additionalLanguages.stream().
                anyMatch(
                        additionalLanguage -> additionalLanguage.getLanguageName().equals(language)
                )
        ) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }
    }

    public void checkAdditionalLanguageValidation(Long additionalLanguageId) {
        if (this.additionalLanguages.stream().
                noneMatch(
                        additionalLanguage -> additionalLanguage.getId().equals(additionalLanguageId)
                )
        ) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkEducationValidation(Long educationId) {
        if (this.educations.stream().
                noneMatch(
                        education -> education.getId().equals(educationId)
                )
        ) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkWorkExperienceValidation(Long workExperienceId) {
        if (this.workExperiences.stream().
                noneMatch(
                        workExperience -> workExperience.getId().equals(workExperienceId)
                )
        ) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }


    public Map<String, Integer> calculateWorkHours(Education education, User user) {

        Integer topicLevel = languageSkill.getTopikLevel();
        Integer socialIntegrationLevel = languageSkill.getSocialIntegrationLevel();
        Integer sejongInstituteLevel = languageSkill.getSejongInstituteLevel();
        int weekendWorkHour = 0;
        int weekdayWorkHour = 0;

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        if (education != null && education.getGpa() <= 2.0) {

            stringIntegerHashMap.put("weekendWorkHours", weekendWorkHour);
            stringIntegerHashMap.put("weekdayWorkHours", weekdayWorkHour);

            return stringIntegerHashMap;
        }

        switch (user.getVisa()) {
            case D_2_1 -> {
                if (topicLevel >= 3 && socialIntegrationLevel >= 3 && sejongInstituteLevel >= 3) { // 한국어 능력기준 달성 시
                    if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                        weekendWorkHour = 168;
                        weekdayWorkHour = 30;
                    } else { // 성적우수자 혜택 미적용
                        weekendWorkHour = 168;
                        weekdayWorkHour = 25;
                    }
                } else { // 한국어 능력기준 미달성 시
                    weekendWorkHour = 10;
                    weekdayWorkHour = 10;
                }
            }
            case D_2_2 -> {
                if (education != null && education.getGrade() < 3) { // 학년이 3학년 미만일 시
                    if (topicLevel >= 3 && socialIntegrationLevel >= 3 && sejongInstituteLevel >= 3) { // 한국어 능력기준 달성 시
                        if (education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                            weekendWorkHour = 168;
                            weekdayWorkHour = 30;
                        } else { // 성적우수자 혜택 미적용
                            weekendWorkHour = 168;
                            weekdayWorkHour = 25;
                        }
                    } else { // 한국어 능력기준 미달성 시
                        weekendWorkHour = 10;
                        weekdayWorkHour = 10;
                    }
                } else { // 학년이 3학년 이상일 시
                    if (topicLevel >= 4 && socialIntegrationLevel >= 4 && sejongInstituteLevel >= 4) { // 한국어 능력기준 달성 시
                        if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                            weekendWorkHour = 168;
                            weekdayWorkHour = 30;
                        } else { // 성적우수자 혜택 미적용
                            weekendWorkHour = 168;
                            weekdayWorkHour = 25;
                        }
                    } else { // 한국어 능력기준 미달성 시
                        weekendWorkHour = 10;
                        weekdayWorkHour = 10;
                    }
                }
            }
            case D_2_3, D_2_4 -> {
                if (topicLevel >= 4 && socialIntegrationLevel >= 4 && sejongInstituteLevel >= 4) { // 한국어 능력기준 달성 시
                    if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                        weekendWorkHour = 168;
                        weekdayWorkHour = 35;
                    } else { // 성적우수자 혜택 미적용
                        weekendWorkHour = 168;
                        weekdayWorkHour = 30;
                    }
                } else { // 한국어 능력기준 미달성 시
                    weekendWorkHour = 15;
                    weekdayWorkHour = 15;
                }
            }
        }
        stringIntegerHashMap.put("weekendWorkHours", weekendWorkHour);
        stringIntegerHashMap.put("weekdayWorkHours", weekdayWorkHour);

        return stringIntegerHashMap;
    }
}
