package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.user.domain.User;
import com.inglo.giggle.posting.presentation.dto.response.ReadUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserUserOwnerJobPostingCountService implements ReadUserUserOwnerJobPostingCountUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    @Transactional(readOnly = true)
    public ReadUserOwnerJobPostingCountResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 검사
        account.checkUserValidation();

        // UserOwnerJobPosting 조회
        List<UserOwnerJobPosting> userOwnerJobPostings = userOwnerJobPostingRepository.findAllWithJobPostingByUser((User) account);

        // 지원 현황 및 지원 성공 개수 확인하기
        Integer applicationCounts = userOwnerJobPostings.size();
        Integer successfulHireCounts = userOwnerJobPostingService.getSuccessFulHireCounts(userOwnerJobPostings);

        // DTO 반환
        return ReadUserOwnerJobPostingCountResponseDto.of(
                applicationCounts,
                successfulHireCounts
        );
    }

}
