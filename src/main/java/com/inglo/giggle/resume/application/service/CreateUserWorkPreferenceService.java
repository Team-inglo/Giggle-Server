package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.request.CreateUserWorkPreferenceRequestDto;
import com.inglo.giggle.resume.application.usecase.CreateUserWorkPreferenceUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserWorkPreferenceService implements CreateUserWorkPreferenceUseCase {
    private final ResumeRepository resumeRepository;
    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    public void execute(UUID accountId, CreateUserWorkPreferenceRequestDto requestDto) {

        // Resume 조회
        Resume resume = resumeRepository.findByIdOrElseThrow(accountId);

        // WorkPreference 생성
        WorkPreference workPreference = WorkPreference.builder()
                .jobCategory(requestDto.jobCategory())
                .employmentType(requestDto.employmentType())
                .region1DepthName(requestDto.region1DepthName())
                .region2DepthName(requestDto.region2DepthName())
                .region3DepthName(requestDto.region3DepthName())
                .region4DepthName(requestDto.region4DepthName())
                .resume(resume)
                .build();

        // WorkPreference 저장
        workPreferenceRepository.save(workPreference);
    }
}
