package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfoDto extends SelfValidating<PageInfoDto> {

    @NotNull
    @JsonProperty("current_page")
    private final Integer currentPage;

    @NotNull
    @JsonProperty("current_items")
    private final Integer currentItems;

    @NotNull
    @JsonProperty("page_size")
    private final Integer pageSize;

    @NotNull
    @JsonProperty("total_pages")
    private final Integer totalPages;

    @NotNull
    @JsonProperty("total_items")
    private final Integer totalItems;

    @Builder
    public PageInfoDto(
            Integer currentPage,
            Integer currentItems,
            Integer pageSize,
            Integer totalPages,
            Integer totalItems
    ) {
        this.currentPage = currentPage;
        this.currentItems = currentItems;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.validateSelf();
    }

    public static PageInfoDto of(
            Integer currentPage,
            Integer currentItems,
            Integer pageSize,
            Integer totalPages,
            Integer totalItems
    ) {
        return PageInfoDto.builder()
                .currentPage(currentPage)
                .currentItems(currentItems)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalItems(totalItems)
                .build();
    }
}