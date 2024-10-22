package com.inglo.giggle.document.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EPaymentMethod {
    DIRECT("근로자에게 직접지급", "DIRECT"),
    BANK_TRANSFER("근로자 명의 계좌로 입금", "BANK_TRANSFER"),
    ;
    private final String krName;
    private final String enName;

    public static EPaymentMethod fromString(String value) {
        return switch (value.toUpperCase()) {
            case "DIRECT" -> DIRECT;
            case "BANK_TRANSFER" -> BANK_TRANSFER;
            default -> throw new IllegalArgumentException("지급방식이 잘못되었습니다.");
        };
    }
}
