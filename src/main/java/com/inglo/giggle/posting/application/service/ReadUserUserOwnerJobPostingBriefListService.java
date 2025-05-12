package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingBriefListUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserOwnerJobPostingBriefListResponseDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingBriefListService implements ReadUserUserOwnerJobPostingBriefListUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    private static final String UPDATED_AT = "updatedAt";

    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingBriefListResponseDto execute(UUID accountId, Integer page, Integer size) {

        // Account 조회
        Account account = loadAccountPort.loadAccountWithRefreshTokenOrElseThrow(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // 페이지네이션 설정 UserOwnerJobPosting 조회
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(UPDATED_AT).descending());
        Page<UserOwnerJobPosting> userOwnerJobPostings = userOwnerJobPostingRepository.findAllPagedWithJobPostingAndOwnerByUser((User) account, pageable);

        // DTO 반환
        return ReadUserOwnerJobPostingBriefListResponseDto.of(
                userOwnerJobPostings
        );
    }
}