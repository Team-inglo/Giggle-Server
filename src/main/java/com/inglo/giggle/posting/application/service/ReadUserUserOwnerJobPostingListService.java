package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.application.dto.response.ReadUserUserOwnerJobPostingListResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingListUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingListService implements ReadUserUserOwnerJobPostingListUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final static String property = "updatedAt";
    private final static String ASCENDING = "ASCENDING";
    private final static String ALL = "ALL";

    private final static List<EApplicationStep> applicationSteps = List.of(
            EApplicationStep.RESUME_UNDER_REVIEW,
            EApplicationStep.WAITING_FOR_INTERVIEW,
            EApplicationStep.FILLING_OUT_DOCUMENTS,
            EApplicationStep.DOCUMENT_UNDER_REVIEW,
            EApplicationStep.APPLICATION_IN_PROGRESS,
            EApplicationStep.REGISTERING_RESULTS
    );

    @Override
    @Transactional(readOnly = true)
    public ReadUserUserOwnerJobPostingListResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size,
            String sortingType,
            String status
    ) {
        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // User 조회
        User user = (User) account;

        // 정렬 기준 및 페이지네이션 설정
        Pageable pageable = PageRequest.of(page - 1, size, getSortByProperty(sortingType));

        // 유학생이 지원한 공고 리스트 조회
        Page<UserOwnerJobPosting> userOwnerJobPostingPage = findUserOwnerJobPostings(user, status, pageable);

        // DTO 반환
        return ReadUserUserOwnerJobPostingListResponseDto.of(userOwnerJobPostingPage);
    }

    /* -------------------------------------------- */
    /* Private Methods ---------------------------- */
    /* -------------------------------------------- */

    // 사용자와 상태에 따라 공고 리스트를 조회하는 메서드
    private Page<UserOwnerJobPosting> findUserOwnerJobPostings(
            User user,
            String status,
            Pageable pageable
    ) {
        if (status.equals(ALL)) {
            return userOwnerJobPostingRepository.findAllPagedWithJobPostingByUser(
                    user,
                    pageable
            );
        }

        EApplicationStep step = EApplicationStep.fromString(status);

        if (step.equals(EApplicationStep.APPLICATION_IN_PROGRESS)) {
            return userOwnerJobPostingRepository.findAllPagedWithJobPostingByUserAndSteps(
                    user,
                    applicationSteps,
                    pageable
            );
        }

        return userOwnerJobPostingRepository.findAllPagedWithJobPostingByUserAndStep(
                user,
                step,
                pageable
        );

    }

    // 정렬 기준에 따라 정렬하는 메서드
    private Sort getSortByProperty(String sortingType) {
        if(sortingType.equals(ASCENDING)) {
            return Sort.by(ReadUserUserOwnerJobPostingListService.property).ascending();
        }
        return Sort.by(ReadUserUserOwnerJobPostingListService.property).descending();
    }

}
