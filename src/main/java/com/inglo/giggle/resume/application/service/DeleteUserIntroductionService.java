package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.application.usecase.DeleteUserIntroductionUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.mysql.ResumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserIntroductionService implements DeleteUserIntroductionUseCase {
    private final ResumeRepository resumeRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public void execute(UUID accountId) {
        Resume resume = resumeRepository.findById(accountId)
                .orElseThrow(()->new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        resume = resumeService.updateIntroduction(resume, null);
        resumeRepository.save(resume);
    }
}
