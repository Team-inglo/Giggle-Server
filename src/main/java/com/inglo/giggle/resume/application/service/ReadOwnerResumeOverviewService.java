package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeOverviewResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeOverviewUseCase;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.type.EKorean;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerResumeOverviewService implements ReadOwnerResumeOverviewUseCase {

    // 북마크 많은 순
    private static final String TRENDING = "POPULAR";

    // 최신순
    private static final String RECENT = "RECENT";

    private final ResumeRepository resumeRepository;

    @Override
    public ReadOwnerResumeOverviewResponseDto execute(
            UUID accountId,
            Integer page,
            Integer size,
            String sorting,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Boolean isBookmarked
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 북마크한 인재 조회 시
        if (isBookmarked) {
            Page<Resume> bookmarkedResumes = resumeRepository.findBookmarkedResumes(accountId, pageable);
            return ReadOwnerResumeOverviewResponseDto.from(bookmarkedResumes);
        }

        List<EVisa> visaSafe = (visa == null) ? List.of() : visa;
        List<EKorean> koreanSafe = (korean == null) ? List.of() : korean;
        List<EMajor> majorSafe = (major == null) ? List.of() : major;
        List<ENationality> nationalitySafe = (nationality == null) ? List.of() : nationality;
        List<EJobCategory> industrySafe = (industry == null) ? List.of() : industry;

        Page<Resume> resumePage = switch (sorting.toUpperCase()) {
            case TRENDING -> resumeRepository.findResumesOrderByBookMarks(
                    accountId, visaSafe, koreanSafe, majorSafe, nationalitySafe, industrySafe, pageable
            );
            case RECENT -> resumeRepository.findResumesOrderByCreatedAt(
                    accountId, visaSafe, koreanSafe, majorSafe, nationalitySafe, industrySafe, pageable
            );
            default -> resumeRepository.findResumesOrderByBookMarks(
                    accountId, visaSafe, koreanSafe, majorSafe, nationalitySafe, industrySafe, pageable
            );
        };
        return ReadOwnerResumeOverviewResponseDto.from(resumePage);
    }
}
