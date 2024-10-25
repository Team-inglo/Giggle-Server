package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingBriefListResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingBriefListUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOwnerJobPostingBriefListService implements ReadUserOwnerJobPostingBriefListUseCase {

    private final UserRepository userRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadUserOwnerJobPostingBriefListResponseDto execute(UUID accountId, Integer page, Integer size) {
        // 유학생 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 페이지네이션 설정 UserOwnerJobPosting 조회
        Pageable pageable = PageRequest.of(page, size);
        Page<UserOwnerJobPosting> userOwnerJobPostingList = userOwnerJobPostingRepository.findAllPagedWithJobPostingAndOwnerByUser(user, pageable);

        // DTO 반환
        return ReadUserOwnerJobPostingBriefListResponseDto.of(
                userOwnerJobPostingList
        );
    }
}