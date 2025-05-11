package com.inglo.giggle.resume.domain;

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
public class Resume extends BaseDomain {

    private UUID accountId;
    private String introduction;
    private List<WorkExperience> workExperiences;
    private List<Education> educations;
    private LanguageSkill languageSkill;

    @Builder
    public Resume(
            UUID accountId,
            String introduction,
            List<WorkExperience> workExperiences,
            List<Education> educations,
            LanguageSkill languageSkill
    ) {
        this.accountId = accountId;
        this.introduction = introduction;
        this.workExperiences = workExperiences;
        this.educations = educations;
        this.languageSkill = languageSkill;
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                        get                                                      -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public AdditionalLanguage getAdditionalLanguage(Long additionalLanguageId) {
        return this.languageSkill.getAdditionalLanguages().stream()
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
    public void addAdditionalLanguage(String languageName, Integer level) {
        this.languageSkill.addAdditionalLanguage(languageName, level);
    }

    public void addEducation(
            EEducationLevel level,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            Long schoolId
    ) {
        Education education = Education.builder()
                .educationLevel(level)
                .major(major)
                .gpa(gpa)
                .enrollmentDate(enrollmentDate)
                .graduationDate(graduationDate)
                .grade(grade)
                .schoolId(schoolId)
                .build();
        this.educations.add(education);
    }

    public void addWorkExperience(
            String experienceTitle,
            String workplace,
            LocalDate startDate,
            LocalDate endDate,
            String description
    ) {
        WorkExperience workExperience = WorkExperience.builder()
                .experienceTitle(experienceTitle)
                .workplace(workplace)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .build();
        this.workExperiences.add(workExperience);
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      update                                                     -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void clearIntroduction() {
        this.introduction = null;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateAdditionalLanguage(
            Long additionalLanguageId,
            String languageName,
            Integer level
    ) {
        AdditionalLanguage additionalLanguage = this.languageSkill.getAdditionalLanguages().stream()
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
     * -------                                      delete                                                     -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void deleteAdditionalLanguage(Long additionalLanguageId) {
        AdditionalLanguage additionalLanguage = this.getAdditionalLanguage(additionalLanguageId);
        if (additionalLanguage != null) {
            this.languageSkill.getAdditionalLanguages().remove(additionalLanguage);
        }
    }

    public void deleteEducation(Long educationId) {
        Education education = this.educations.stream()
                .filter(
                        edu -> edu.getId().equals(educationId)
                )
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EDUCATION)
                );
        if (education != null) {
            this.educations.remove(education);
        }
    }

    public void deleteWorkExperience(Long workExperienceId) {
        WorkExperience workExperience = this.workExperiences.stream()
                .filter(
                        workExp -> workExp.getId().equals(workExperienceId)
                )
                .findFirst()
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE)
                );
        if (workExperience != null) {
            this.workExperiences.remove(workExperience);
        }
    }

    /* ---------------------------------------------------------------------------------------------------------------*
     * -------                                      validate                                                   -------*
     * -------------------------------------------------------------------------------------------------------------- */
    public void checkIsExistAdditionalLanguage(String language) {
        if (this.languageSkill.getAdditionalLanguages().stream().
                anyMatch(
                        additionalLanguage -> additionalLanguage.getLanguageName().equals(language)
                )
        ) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }
    }

    public void checkAdditionalLanguageValidation(Long additionalLanguageId) {
        if (this.languageSkill.getAdditionalLanguages().stream().
                noneMatch(
                        additionalLanguage -> additionalLanguage.getId().equals(additionalLanguageId)
                )
        ) {
            throw new CommonException(ErrorCode.NOT_FOUND_ADDITIONAL_LANGUAGE);
        }
    }

    public void checkEducationValidation(Long educationId) {
        if (this.educations.stream().
                noneMatch(
                        education -> education.getId().equals(educationId)
                )
        ) {
            throw new CommonException(ErrorCode.NOT_FOUND_EDUCATION);
        }
    }

    public void checkWorkExperienceValidation(Long workExperienceId) {
        if (this.workExperiences.stream().
                noneMatch(
                        workExperience -> workExperience.getId().equals(workExperienceId)
                )
        ) {
            throw new CommonException(ErrorCode.NOT_FOUND_WORK_EXPERIENCE);
        }
    }


    public Map<String, Integer> calculateWorkHours(Long educationId) {

        Education education = this.getEducation(educationId);

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

        stringIntegerHashMap.put("weekendWorkHours", weekendWorkHour);
        stringIntegerHashMap.put("weekdayWorkHours", weekdayWorkHour);

        return stringIntegerHashMap;
    }
}
