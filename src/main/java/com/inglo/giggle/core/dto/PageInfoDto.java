package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageInfoDto {

    @NotNull
    private final Integer currentPage;

    @NotNull
    private final Integer currentItems;

    @NotNull
    private final Integer pageSize;

    @NotNull
    private final Integer totalPages;

    @NotNull
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