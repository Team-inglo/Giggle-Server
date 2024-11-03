package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerUserOwnerJobPostingDetailService implements ReadOwnerUserOwnerJobPostingDetailUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // User에 속한 School 조회
        List<School> school = schoolRepository.findMostRecentGraduationSchoolByUserId(
                userOwnerJobPosting.getUser().getId()
        );

        // DTO 변환
        return ReadOwnerUserOwnerJobPostingDetailResponseDto.of(
                userOwnerJobPosting,
                school.isEmpty() ? null : school.get(0)
        );
    }
}
