package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.WorkPreference;
import com.inglo.giggle.resume.repository.WorkPreferenceRepository;
import com.inglo.giggle.resume.repository.mysql.WorkPreferenceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WorkPreferenceRepositoryImpl implements WorkPreferenceRepository {

    private final WorkPreferenceJpaRepository workPreferenceJpaRepository;

    @Override
    public WorkPreference findByIdOrElseThrow(Long id) {
        return workPreferenceJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_WORK_PREFERENCE));
    }

    @Override
    public List<WorkPreference> findAllByResumeId(UUID resumeId) {
        return workPreferenceJpaRepository.findAllByResumeAccountId(resumeId);
    }

    @Override
    public void save(WorkPreference workPreference) {
        workPreferenceJpaRepository.save(workPreference);
    }

    @Override
    public void deleteById(Long workPreferenceId) {
        workPreferenceJpaRepository.deleteById(workPreferenceId);
    }
}
