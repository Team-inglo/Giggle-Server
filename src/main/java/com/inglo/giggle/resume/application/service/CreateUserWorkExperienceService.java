package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.CreateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserWorkExperienceUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.WorkExperienceService;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserWorkExperienceService implements CreateUserWorkExperienceUseCase {
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceService workExperienceService;

    @Override
    @Transactional
    public void execute(CreateUserWorkExperienceRequestDto requestDto) {
        WorkExperience workExperience = workExperienceService.createWorkExperience(
                requestDto.title(),
                requestDto.workplace(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.description()
        );

        workExperienceRepository.save(workExperience);
    }

}
