package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserEducationUseCase;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserEducationService implements DeleteUserEducationUseCase {
    private final EducationRepository educationRepository;

    @Override
    @Transactional
    public void execute(Long educationId) {
        educationRepository.deleteById(educationId);
    }
}
