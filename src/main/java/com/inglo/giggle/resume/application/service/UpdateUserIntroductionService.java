package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.dto.request.UpdateUserIntroductionRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserIntroductionUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserIntroductionService implements UpdateUserIntroductionUseCase {
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateUserIntroductionRequestDto requestDto) {
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        resume = resumeService.updateIntroduction(resume, requestDto.introduction());
        resumeRepository.save(resume);
    }

}
