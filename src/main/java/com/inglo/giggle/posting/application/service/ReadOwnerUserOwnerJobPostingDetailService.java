package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.mysql.SchoolRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerUserOwnerJobPostingDetailService implements ReadOwnerUserOwnerJobPostingDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final SchoolRepository schoolRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerUserOwnerJobPostingDetailResponseDto execute(UUID accountId, Long userOwnerJobPostingsId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkOwnerValidation(account);

        // UserOwnerJobPosting 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findWithOwnerAndUserJobPostingById(userOwnerJobPostingsId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(userOwnerJobPosting, accountId);

        // User에 속한 School 조회
        Optional<School> school = schoolRepository.findTopByUserIdOrderByGraduationDateDesc(
                userOwnerJobPosting.getUser().getId()
        );

        // DTO 변환
        return ReadOwnerUserOwnerJobPostingDetailResponseDto.of(
                userOwnerJobPosting,
                school.orElse(null)
        );
    }
}
