package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.UpdateUserJobPostingBookMarkResponseDto;
import com.inglo.giggle.posting.application.usecase.UpdateUserJobPostingBookMarkUseCase;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.service.BookMarkService;
import com.inglo.giggle.posting.repository.mysql.BookMarkRepository;
import com.inglo.giggle.posting.repository.mysql.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserJobPostingBookMarkService implements UpdateUserJobPostingBookMarkUseCase {

    private final BookMarkRepository bookMarkRepository;
    private final BookMarkService bookMarkService;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;

    @Override
    @Transactional
    public UpdateUserJobPostingBookMarkResponseDto execute(UUID userId, Long jobPostingId) {

        boolean isBookMarked = bookMarkRepository.findByUserIdAndJobPostingId(userId, jobPostingId)
                .map(bookMark -> {
                    bookMarkRepository.delete(bookMark);
                    return false; // 삭제되면 false
                })
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

                    JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                            .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
