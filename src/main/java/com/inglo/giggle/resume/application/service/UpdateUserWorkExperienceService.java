package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.WorkExperienceService;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserWorkExperienceService implements UpdateUserWorkExperienceUseCase {
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceService workExperienceService;

    @Override
    @Transactional
    public void execute(Long workExperienceId, UpdateUserWorkExperienceRequestDto requestDto) {
        WorkExperience workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        workExperience = workExperienceService.updateWorkExperience(
                workExperience,
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description()
        );
        workExperienceRepository.save(workExperience);
    }
}
