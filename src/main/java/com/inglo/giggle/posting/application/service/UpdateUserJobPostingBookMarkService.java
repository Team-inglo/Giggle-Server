package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.posting.application.usecase.UpdateUserJobPostingBookMarkUseCase;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.persistence.repository.BookMarkRepository;
import com.inglo.giggle.posting.persistence.repository.JobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.UpdateUserJobPostingBookMarkResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserJobPostingBookMarkService implements UpdateUserJobPostingBookMarkUseCase {


    private final LoadAccountPort loadAccountPort;
    private final BookMarkRepository bookMarkRepository;
    private final JobPostingRepository jobPostingRepository;

    @Override
    @Transactional
    public UpdateUserJobPostingBookMarkResponseDto execute(UUID accountId, Long jobPostingId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

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
                            BookMark.builder()
                                    .userId(user.getId())
                                    .jobPostingId(jobPosting.getId())
                                    .build());
                    return true; // 생성되면 true
                });

        return UpdateUserJobPostingBookMarkResponseDto.of(
                isBookMarked
        );
    }

}
