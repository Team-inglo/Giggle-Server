package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.repository.SchoolRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingDetailService implements ReadOwnerUserOwnerJobPostingDetailUseCase {

    private final AccountRepository accountRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkOwnerValidation();

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingByIdOrElseThrow(userOwnerJobPostingsId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

        // User에 속한 School 조회
        Optional<School> school = schoolRepository.findTopByUserIdOrderByGraduationDateDescOptional(
                userOwnerJobPosting.getUserInfo().getId()
        );

        // DTO 변환
        return ReadOwnerUserOwnerJobPostingDetailResponseDto.of(
                userOwnerJobPosting,
                school.orElse(null)
        );
    }
}
