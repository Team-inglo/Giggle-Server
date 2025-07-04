package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeOverviewResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeOverviewUseCase;
import com.inglo.giggle.resume.domain.BookMarkResume;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.type.EKorean;
import com.inglo.giggle.resume.repository.BookMarkResumeRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadOwnerResumeOverviewService implements ReadOwnerResumeOverviewUseCase {

    private static final String TRENDING = "POPULAR";
    private static final String RECENT = "RECENT";

    private final ResumeRepository resumeRepository;
    private final BookMarkResumeRepository bookMarkResumeRepository;

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

        // 북마크된 이력서만
        if (isBookmarked) {
            Page<Resume> bookmarkedResumes = resumeRepository.findBookmarkedResumes(accountId, pageable);
            return ReadOwnerResumeOverviewResponseDto.from(bookmarkedResumes);
        }

        // Null-safe
        List<EVisa> visaSafe = visa != null ? visa : List.of();
        List<EKorean> koreanSafe = korean != null ? korean : List.of();
        List<EMajor> majorSafe = major != null ? major : List.of();
        List<ENationality> nationalitySafe = nationality != null ? nationality : List.of();
        List<EJobCategory> industrySafe = industry != null ? industry : List.of();

        // 기본 정렬 방식 선택
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

        // 🔥 북마크 미리 조회 (N+1 방지)
        Set<UUID> resumeIds = resumePage.getContent().stream()
                .map(Resume::getAccountId)
                .collect(Collectors.toSet());

        List<BookMarkResume> bookMarks = bookMarkResumeRepository.findAllByResumeAccountIdIn(resumeIds);
        Map<UUID, List<BookMarkResume>> bookMarkMap = bookMarks.stream()
                .collect(Collectors.groupingBy(bm -> bm.getResume().getAccountId()));

        return ReadOwnerResumeOverviewResponseDto.from(resumePage, bookMarkMap, accountId);
    }
}
