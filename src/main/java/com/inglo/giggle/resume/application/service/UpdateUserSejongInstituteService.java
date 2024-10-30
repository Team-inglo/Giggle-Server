package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserSejongInstituteReqeustDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserSejongInstituteUseCase;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.service.LanguageSkillService;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserSejongInstituteService implements UpdateUserSejongInstituteUseCase {
    private final LanguageSkillRepository languageSkillRepository;
    private final LanguageSkillService languageSkillService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserSejongInstituteReqeustDto requestDto) {
        LanguageSkill languageSkill = languageSkillRepository.findById(accountId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        languageSkill = languageSkillService.updateSejongInstitute(languageSkill, requestDto.level());

        languageSkillRepository.save(languageSkill);
    }

}
