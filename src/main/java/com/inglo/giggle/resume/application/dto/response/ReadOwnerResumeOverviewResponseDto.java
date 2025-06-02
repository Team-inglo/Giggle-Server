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

        @JsonProperty("title")
        private final String title;

        @JsonProperty("visa")
        private final EVisa visa;

        @JsonProperty("industry")
        private final String industry;

        @JsonProperty("bookmark_count")
        private final int bookmarkCount;

        public ResumeDto(
                UUID id,
                String name,
                String profileImgUrl,
                String nationality,
                String title,
                EVisa visa,
                String industry,
                int bookmarkCount
        ) {
            this.id = id;
            this.name = name;
            this.profileImgUrl = profileImgUrl;
            this.nationality = nationality;
            this.title = title;
            this.visa = visa;
            this.industry = industry;
            this.bookmarkCount = bookmarkCount;
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
                        resume.getTitle(),
                        resume.getUser().getVisa(),
                        resume.getWorkPreferenceJobCategoriesName(),
                        resume.getBookMarks().size()
                ))
                .toList();

        return new ReadOwnerResumeOverviewResponseDto(
                resumeDtos,
                resumePage.hasNext(),
                (int) resumePage.getTotalElements()
        );
    }
}
