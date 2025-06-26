package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.response.ReadUserResumeCompletionRateResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserResumeCompletionRateUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserResumeCompletionRateService implements ReadUserResumeCompletionRateUseCase {

    private final ResumeRepository resumeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserResumeCompletionRateResponseDto execute(UUID resumeId) {
        Resume resume = resumeRepository.findByIdOrElseThrow(resumeId);

        Integer completionRate = resume.getCompletionRate();

        return ReadUserResumeCompletionRateResponseDto.builder()
                .completionRate(completionRate)
                .build();
    }
}
