package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserWorkExperienceUseCase;
import com.inglo.giggle.resume.repository.mysql.WorkExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserWorkExperienceService implements DeleteUserWorkExperienceUseCase {
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    @Transactional
    public void execute(Long workExperienceId) {
        workExperienceRepository.deleteById(workExperienceId);
    }
}
