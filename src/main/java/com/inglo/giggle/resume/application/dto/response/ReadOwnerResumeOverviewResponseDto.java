package com.inglo.giggle.resume.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.resume.domain.Resume;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Getter
public class ReadOwnerResumeOverviewResponseDto extends SelfValidating<ReadOwnerResumeOverviewResponseDto> {
    @JsonProperty("resumes")
    private final List<ResumeDto> resumes;

    @JsonProperty("has_next")
    private final boolean hasNext;

    @JsonProperty("total_count")
    private final int totalCount;

    public ReadOwnerResumeOverviewResponseDto(List<ResumeDto> resumes, boolean hasNext, int totalCount) {
        this.resumes = resumes;
        this.hasNext = hasNext;
        this.totalCount = totalCount;
        this.validateSelf();
    }

    @Getter
    public static class ResumeDto extends SelfValidating<ResumeDto> {
        @JsonProperty("id")
        private final UUID id;

        @JsonProperty("name")
        private final String name;

        @JsonProperty("profile_img_url")
        private final String profileImgUrl;

        @JsonProperty("nationality")
        private final String nationality;

        @JsonProperty("address")
        private final String address;

        @JsonProperty("title")
        private final String title;

        @JsonProperty("visa")
        private final EVisa visa;

        @JsonProperty("industry")
        private final String industry;

        @JsonProperty("bookmark_count")
        private final int bookmarkCount;

        @JsonProperty("is_bookmarked")
        private final Boolean isBookmarked;

        public ResumeDto(
                UUID id,
                String name,
                String profileImgUrl,
                String nationality,
                String address,
                String title,
                EVisa visa,
                String industry,
                int bookmarkCount,
                boolean isBookmarked
        ) {
            this.id = id;
            this.name = name;
            this.profileImgUrl = profileImgUrl;
            this.nationality = nationality;
            this.address = address;
            this.title = title;
            this.visa = visa;
            this.industry = industry;
            this.bookmarkCount = bookmarkCount;
            this.isBookmarked = isBookmarked;
        }
    }

    public static ReadOwnerResumeOverviewResponseDto from(
            Page<Resume> resumePage
    ) {
        List<ResumeDto> resumeDtos = resumePage.getContent().stream()
                .map(resume -> new ResumeDto(
                        resume.getAccountId(),
                        resume.getUser().getName(),
                        resume.getUser().getProfileImgUrl(),
                        resume.getUser().getNationality().getKrName(),
                        resume.getUser().getAddress() != null ? resume.getUser().getAddress().getFullAddress() : "지역 미등록",
                        resume.getTitle(),
                        resume.getUser().getVisa(),
                        resume.getWorkPreferenceJobCategoriesName(),
                        resume.getBookMarks().size(),
                        resume.getBookMarks().stream()
                                .anyMatch(bookmark -> bookmark.getResume().getAccountId().equals(resume.getAccountId()))
                ))
                .toList();

        return new ReadOwnerResumeOverviewResponseDto(
                resumeDtos,
                resumePage.hasNext(),
                (int) resumePage.getTotalElements()
        );
    }
}
