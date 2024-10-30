package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserAdditionalLanguageSkillRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserAdditionalLanguageSkillUseCase;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.service.AdditionalLanguageService;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserAdditionalLanguageSkillService implements UpdateUserAdditionalLanguageSkillUseCase {

    private final AdditionalLanguageRepository additionalLanguageRepository;
    private final AdditionalLanguageService additionalLanguageService;


    @Override
    @Transactional
    public void execute(Long additionalLanguageSkillId, UpdateUserAdditionalLanguageSkillRequestDto requestDto) {
        AdditionalLanguage additionalLanguage = additionalLanguageRepository.findById(additionalLanguageSkillId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        additionalLanguage = additionalLanguageService.updateAdditionalLanguage(
                additionalLanguage, requestDto.languageName(), requestDto.level()
        );

        additionalLanguageRepository.save(additionalLanguage);
    }


}
