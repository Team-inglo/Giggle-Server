package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserAdditionalLanguageUseCase;
import com.inglo.giggle.resume.repository.mysql.AdditionalLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserAdditionalLanguageService implements DeleteUserAdditionalLanguageUseCase {
    private final AdditionalLanguageRepository additionalLanguageRepository;

    @Override
    @Transactional
    public void execute(Long additionalLanguageId) {
        additionalLanguageRepository.deleteById(additionalLanguageId);
    }
}
