package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.mysql.UserRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.posting.repository.mysql.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOwnerJobPostingCountService implements ReadUserOwnerJobPostingCountUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;
    private final UserRepository userRepository;

    private final UserOwnerJobPostingService userOwnerJobPostingService;

    @Override
    public ReadUserOwnerJobPostingCountResponseDto execute(UUID accountId) {
        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // UserOwnerJobPosting 조회
        List<UserOwnerJobPosting> userOwnerJobPostingList = userOwnerJobPostingRepository.findAllWithJobPostingByUser(user);

        // 지원 현황 및 지원 성공 개수 확인하기
        Integer applicationCounts = userOwnerJobPostingList.size();
        Integer successfulHireCounts = userOwnerJobPostingService.getSuccessFulHireCounts(userOwnerJobPostingList);

        // DTO 반환
        return ReadUserOwnerJobPostingCountResponseDto.of(
                applicationCounts,
                successfulHireCounts
        );
    }

}
