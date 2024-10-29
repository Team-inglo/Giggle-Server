package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserBookMarkCountResponseDto extends SelfValidating<ReadUserBookMarkCountResponseDto> {

    @NotNull(message = "book_mark_counts는 null이 될 수 없습니다.")
    @JsonProperty("book_mark_counts")
    private final Integer bookMarkCounts;

    @Builder
    public ReadUserBookMarkCountResponseDto(Integer bookMarkCounts) {
        this.bookMarkCounts = bookMarkCounts;
        this.validateSelf();
    }

    public static ReadUserBookMarkCountResponseDto of(Integer bookMarkCounts) {
        return ReadUserBookMarkCountResponseDto.builder()
                .bookMarkCounts(bookMarkCounts)
                .build();
    }
}
