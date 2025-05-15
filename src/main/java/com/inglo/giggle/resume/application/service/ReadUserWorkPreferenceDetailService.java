package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.response.ReadUserWorkPreferenceDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserWorkPreferenceDetailUseCase;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserWorkPreferenceDetailService implements ReadUserWorkPreferenceDetailUseCase {

    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    public ReadUserWorkPreferenceDetailResponseDto execute(Long workPreferenceId) {
        // WorkPreference 조회
        WorkPreference workPreference = workPreferenceRepository.findByIdOrElseThrow(workPreferenceId);

        return ReadUserWorkPreferenceDetailResponseDto.fromEntity(workPreference);
    }
}
