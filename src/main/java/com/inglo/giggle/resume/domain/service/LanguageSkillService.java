package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LanguageSkillService {

    public LanguageSkill createLanguageSkill(Resume resume) {
        return LanguageSkill.builder()
                .resume(resume)
                .build();
    }

    public LanguageSkill updateTopik(LanguageSkill languageSkill, Integer level) {
        languageSkill.updateTopikLevel(level);
        return languageSkill;
    }

    public LanguageSkill updateSejongInstitute(LanguageSkill languageSkill, Integer level) {
        languageSkill.updateSejongInstituteLevel(level);
        return languageSkill;
    }

    public LanguageSkill updateSocialIntegrationProgram(LanguageSkill languageSkill, Integer level) {
        languageSkill.updateSocialIntegrationLevel(level);
        return languageSkill;
    }

    public void checkLanguageSkillValidation(LanguageSkill languageSkill, UUID accountId) {
        if (!languageSkill.getResume().getAccountId().equals(accountId))
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
    }

}
