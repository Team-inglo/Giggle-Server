package com.inglo.giggle.school.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.repository.UserRepository;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.service.EducationService;
import com.inglo.giggle.resume.persistence.repository.EducationRepository;
import com.inglo.giggle.school.presentation.dto.response.ReadUserSchoolDetailResponseDto;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSchoolDetailService implements ReadUserSchoolDetailUseCase {
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final EducationService educationService;

    @Override
    public ReadUserSchoolDetailResponseDto execute(UUID accountId) {
        User user = userRepository.findByIdOrElseThrow(accountId);

        // 유저의 비자에 맵핑되는 educationLevel 조회
        EEducationLevel educationLevel = Education.getEducationLevelByVisa(user.getVisa());

        // 유저의 educationLevel에 맞는 학력 정보 조회
        List<Education> educations = educationRepository.findEducationByAccountIdAndEducationLevel(accountId, educationLevel);

        Education educationEntity = educationService.getLatestEducation(educations);

        return ReadUserSchoolDetailResponseDto.of(educationEntity);
    }


}
