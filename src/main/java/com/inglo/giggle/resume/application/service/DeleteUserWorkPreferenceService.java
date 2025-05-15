package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.usecase.DeleteUserWorkPreferenceUseCase;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserWorkPreferenceService implements DeleteUserWorkPreferenceUseCase {

    private final WorkPreferenceRepository workPreferenceRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long workPreferenceId) {
        // WorkPreference 조회
        WorkPreference workPreference = workPreferenceRepository.findByIdOrElseThrow(workPreferenceId);

        // WorkPreference 유효성 체크
        workPreference.checkUserValidation(accountId);

        // WorkPreference 삭제
        workPreferenceRepository.deleteById(workPreferenceId);
    }
}
