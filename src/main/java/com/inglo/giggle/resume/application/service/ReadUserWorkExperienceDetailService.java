package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.response.ReadUserWorkExperienceDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserWorkExperienceDetailUseCase;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadUserWorkExperienceDetailService implements ReadUserWorkExperienceDetailUseCase {
    private final WorkExperienceRepository workExperienceRepository;
    @Override
    public ReadUserWorkExperienceDetailResponseDto execute(Long workExperienceId) {
        WorkExperience workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadUserWorkExperienceDetailResponseDto.fromEntity(workExperience);
    }

}
