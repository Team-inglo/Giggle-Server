package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.UpdateUserJobPostingBookMarkResponseDto;
import com.inglo.giggle.posting.application.usecase.UpdateUserJobPostingBookMarkUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.BookMarkService;
import com.inglo.giggle.posting.repository.BookMarkRepository;
import com.inglo.giggle.posting.repository.JobPostingRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserJobPostingBookMarkService implements UpdateUserJobPostingBookMarkUseCase {


    private final AccountRepository accountRepository;
    private final AccountService accountService;

    private final BookMarkRepository bookMarkRepository;
    private final BookMarkService bookMarkService;
    private final JobPostingRepository jobPostingRepository;

    @Override
    @Transactional
    public UpdateUserJobPostingBookMarkResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        accountService.checkUserValidation(account);

        // 북마크 여부 확인 -> 북마크 생성 및 삭제
        boolean isBookMarked = bookMarkRepository.findByUserIdAndJobPostingId(accountId, jobPostingId)
                .map(bookMark -> {
                    bookMarkRepository.delete(bookMark);
                    return false; // 삭제되면 false
                })
                .orElseGet(() -> {
                    User user = (User) account;

                    JobPosting jobPosting = jobPostingRepository.findByIdOrElseThrow(jobPostingId);

                    bookMarkRepository.save(
                            bookMarkService.createBookMark(
                                    user,
                                    jobPosting
                            )
                    );
                    return true; // 생성되면 true
                });

        return UpdateUserJobPostingBookMarkResponseDto.of(
                isBookMarked
        );
    }

}
