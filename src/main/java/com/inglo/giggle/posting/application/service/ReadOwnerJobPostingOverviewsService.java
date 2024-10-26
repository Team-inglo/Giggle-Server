package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerJobPostingOverviewsService implements ReadOwnerJobPostingOverviewsUseCase {

    private final OwnerRepository ownerRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final AccountService accountService;

    @Override
    public ReadOwnerJobPostingOverviewsResponseDto execute(UUID accountId, Integer page, Integer size) {

        // 고용주 조회 및 검증
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 고용주가 등록한 공고 리스트 조회
        Page<UserOwnerJobPosting> userOwnerJobPostingPageList = userOwnerJobPostingRepository.findWithJobPostingByOwner(
                owner,
                PageRequest.of(page, size)
        );

        // DTO 반환
        return ReadOwnerJobPostingOverviewsResponseDto.of(
                userOwnerJobPostingPageList
        );
    }
}
