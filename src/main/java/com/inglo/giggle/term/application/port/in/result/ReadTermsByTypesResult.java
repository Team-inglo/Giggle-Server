package com.inglo.giggle.term.application.port.in.result;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.term.domain.type.ETermType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadTermsByTypesResult extends SelfValidating<ReadTermsByTypesResult> {

    @NotNull(message = "terms는 null일 수 없습니다.")
    private final List<TermInfo> terms;

    @Getter
    public static class TermInfo extends SelfValidating<TermInfo> {

        @NotNull(message = "id는 null일 수 없습니다.")
        private final Long id;

        @NotNull(message = "content는 null일 수 없습니다.")
        private final String content;

        @NotNull(message = "termType는 null일 수 없습니다.")
        private final ETermType termType;

        @NotNull(message = "version는 null일 수 없습니다.")
        private final Double version;

        public TermInfo(Long id, String content, ETermType termType, Double version) {
            this.id = id;
            this.content = content;
            this.termType = termType;
            this.version = version;

            this.validateSelf();
        }

        public static TermInfo of(Long id, String content, ETermType termType, Double version) {
            return new TermInfo(id, content, termType, version);
        }
    }

    public ReadTermsByTypesResult(List<TermInfo> terms) {
        this.terms = terms;

        this.validateSelf();
    }

    public static ReadTermsByTypesResult of(List<TermInfo> terms) {
        return new ReadTermsByTypesResult(terms);
    }
}
