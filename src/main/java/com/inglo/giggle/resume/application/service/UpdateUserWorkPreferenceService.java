package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkPreferenceRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserWorkPreferenceUseCase;
import com.inglo.giggle.resume.domain.PreferenceAddress;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateUserWorkPreferenceService implements UpdateUserWorkPreferenceUseCase {

    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserWorkPreferenceRequestDto requestDto) {

        // WorkPreference 조회
        WorkPreference workPreference = workPreferenceRepository.findByResumeIdOrElseThrow(accountId);
        Set<PreferenceAddress> preferenceAddresses = requestDto.preferenceAddresses()
                .stream()
                .map(address -> new PreferenceAddress(
                        address.region1DepthName(),
                        address.region2DepthName(),
                        address.region3DepthName(),
                        address.region4DepthName(),
                        workPreference
                ))
                .collect(Collectors.toSet());

        // WorkPreference 업데이트
        workPreference.updateSelf(
                requestDto.jobCategories(),
                requestDto.employmentTypes(),
                preferenceAddresses
        );

        // WorkPreference 저장
        workPreferenceRepository.save(workPreference);
    }
}
