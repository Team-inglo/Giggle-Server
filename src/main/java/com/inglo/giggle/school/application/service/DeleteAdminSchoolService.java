package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.usecase.DeleteAdminSchoolUseCase;
import com.inglo.giggle.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAdminSchoolService implements DeleteAdminSchoolUseCase {

    private final SchoolRepository schoolRepository;

    @Override
    @Transactional
    public void execute(Long schoolId) {
        schoolRepository.deleteById(schoolId);
    }
}
