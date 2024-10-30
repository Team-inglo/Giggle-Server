package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.application.dto.request.UpdateUserEducationRequestDto;
import com.inglo.giggle.resume.application.dto.request.UpdateUserWorkExperienceRequestDto;
import com.inglo.giggle.resume.application.usecase.UpdateUserEducationUseCase;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.WorkExperience;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.repository.mysql.EducationRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserEducationService implements UpdateUserEducationUseCase {
    private final EducationRepository educationRepository;
    private final EducationService educationService;
    private final SchoolRepository schoolRepository;


    @Override
    @Transactional
    public void execute(Long educationId, UpdateUserEducationRequestDto requestDto) {

        Education education = educationRepository.findById(educationId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        School school = schoolRepository.findById(requestDto.schoolId())
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        education = educationService.updateEducation(
                education,
                EEducationLevel.fromString(requestDto.educationLevel()),
                school,
                requestDto.major(),
                requestDto.gpa(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.grade()
        );
        educationRepository.save(education);
    }
}
