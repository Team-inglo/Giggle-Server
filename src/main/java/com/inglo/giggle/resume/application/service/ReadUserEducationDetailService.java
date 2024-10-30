package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.response.ReadUserEducationDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserEducationDetailUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadUserEducationDetailService implements ReadUserEducationDetailUseCase {

    private final EducationRepository educationRepository;

    @Override
    public ReadUserEducationDetailResponseDto execute(Long educationId) {
        Education eduation = educationRepository.findWithSchoolById(educationId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadUserEducationDetailResponseDto.fromEntity(eduation);
    }
}
