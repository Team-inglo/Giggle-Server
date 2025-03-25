package com.inglo.giggle.posting.application.service;

import com.inglo.giggle.posting.application.dto.response.ReadAdminUserOwnerJobPostingsBriefsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminUserOwnerJobPostingsBriefsUseCase;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.repository.UserOwnerJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAdminUserOwnerJobPostingsBriefsService implements ReadAdminUserOwnerJobPostingsBriefsUseCase {

    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadAdminUserOwnerJobPostingsBriefsResponseDto execute() {
        long totalCount = userOwnerJobPostingRepository.count();
        double acceptCount = userOwnerJobPostingRepository.countByStep(EApplicationStep.APPLICATION_SUCCESS);
        double rejectCount = userOwnerJobPostingRepository.countByStep(EApplicationStep.APPLICATION_REJECTED);

        double decisionCount = acceptCount + rejectCount;

        double acceptRate = 0.0;
        double rejectRate = 0.0;

        if (decisionCount > 0) {
            acceptRate = acceptCount / decisionCount;
            rejectRate = rejectCount / decisionCount;
        }

        return ReadAdminUserOwnerJobPostingsBriefsResponseDto.of(
                totalCount,
                acceptRate,
                rejectRate
        );
    }
}
