package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.CreateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserAdditionalLanguageSkillUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserAdditionalLanguageSkillService implements CreateUserAdditionalLanguageSkillUseCase {
    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final ResumeRepository resumeRepository;
    private final AdditionalLanguageService additionalLanguageService;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateUserAdditionalLanguageSkillRequestDto requestDto) {
        Resume resume = resumeRepository.findWithLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<AdditionalLanguage> additionalLanguages = resume.getLanguageSkill().getAdditionalLanguages();

        if (additionalLanguages.stream().
                anyMatch(
                        additionalLanguage ->
                                additionalLanguage.getLanguageName().equals(
                                        requestDto.languageName()
                                )
                )
        ) {
            throw new CommonException(ErrorCode.ALREADY_EXIST_RESOURCE);
        }

        AdditionalLanguage additionalLanguage = additionalLanguageService.createAdditionalLanguage(
                requestDto.languageName(),
                requestDto.level(),
                resume.getLanguageSkill()
        );
        additionalLanguageRepository.save(additionalLanguage);
    }

}
