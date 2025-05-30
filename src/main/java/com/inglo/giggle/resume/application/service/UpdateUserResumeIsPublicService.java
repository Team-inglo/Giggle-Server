package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.dto.response.UpdateUserResumeIsPublicResponseDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserResumeIsPublicUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserResumeIsPublicService implements UpdateUserResumeIsPublicUseCase {

    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public UpdateUserResumeIsPublicResponseDto execute(boolean targetStatus, UUID resumeId) {

        Resume resume = resumeRepository.findByIdOrElseThrow(resumeId);
        resume.updatePublicStatus(targetStatus);

        resumeRepository.save(resume);

        return UpdateUserResumeIsPublicResponseDto.of(
                resume.isPublic()
        );
    }
}
