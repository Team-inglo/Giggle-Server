package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import org.springframework.stereotype.Service;

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
}
