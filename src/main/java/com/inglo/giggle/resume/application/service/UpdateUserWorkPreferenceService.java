package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkPreferenceRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserWorkPreferenceUseCase;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserWorkPreferenceService implements UpdateUserWorkPreferenceUseCase {
    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    @Transactional
    public void execute(Long workPreferenceId, UpdateUserWorkPreferenceRequestDto requestDto) {

        // WorkPreference 조회
        WorkPreference workPreference = workPreferenceRepository.findByIdOrElseThrow(workPreferenceId);

        // WorkPreference 업데이트
        workPreference.updateWorkPreference(
                requestDto.jobCategory(),
                requestDto.employmentType(),
                requestDto.region1DepthName(),
                requestDto.region2DepthName(),
                requestDto.region3DepthName(),
                requestDto.region4DepthName()
        );

        // WorkPreference 저장
        workPreferenceRepository.save(workPreference);
    }
}
