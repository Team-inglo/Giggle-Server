package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Resume extends BaseDomain {

    private UUID accountId;
    private String introduction;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    private List<WorkExperience> workExperiences;
    private List<Education> educations;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    private LanguageSkill languageSkill;

    @Builder
    public Resume(UUID accountId, String introduction, List<WorkExperience> workExperiences, List<Education> educations, LanguageSkill languageSkill) {
        this.accountId = accountId;
        this.introduction = introduction;
        this.workExperiences = workExperiences;
        this.educations = educations;
        this.languageSkill = languageSkill;
    }

    public void checkValidation(UUID accountId) {
        if (!this.accountId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void deleteIntroduction() {
        this.introduction = null;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
