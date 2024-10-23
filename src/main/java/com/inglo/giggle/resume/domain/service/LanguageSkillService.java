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
}
