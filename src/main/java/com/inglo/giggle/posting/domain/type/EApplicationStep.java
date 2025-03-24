package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EApplicationStep {

    RESUME_UNDER_REVIEW("이력서 확인중", "RESUME_UNDER_REVIEW", 1),
    WAITING_FOR_INTERVIEW("면접 대기중", "WAITING_FOR_INTERVIEW", 2),
    FILLING_OUT_DOCUMENTS("서류 작성중", "FILLING_OUT_DOCUMENTS", 3),
    DOCUMENT_UNDER_REVIEW("서류 검토중", "DOCUMENT_UNDER_REVIEW", 4),
    APPLICATION_IN_PROGRESS("신청 허가중", "APPLICATION_IN_PROGRESS", 5),
    APPLICATION_SUCCESS("신청 성공", "APPLICATION_SUCCESS", 6),
    APPLICATION_REJECTED("리젝", "APPLICATION_REJECTED", 7),
    RESUME_REJECTED("고용주 이력서 거절", "RESUME_REJECTED", 8),
    PENDING("2주 이상 지연", "PENDING", 9),
    REGISTERING_RESULTS("결과보고", "REGISTERING_RESULTS", 10)
    ;
    private final String krName;
    private final String enName;
    private final Integer level;

    public static EApplicationStep fromString(String value) {
        return switch (value.toUpperCase()) {
            case "RESUME_UNDER_REVIEW" -> RESUME_UNDER_REVIEW;
            case "WAITING_FOR_INTERVIEW" -> WAITING_FOR_INTERVIEW;
            case "FILLING_OUT_DOCUMENTS" -> FILLING_OUT_DOCUMENTS;
            case "DOCUMENT_UNDER_REVIEW" -> DOCUMENT_UNDER_REVIEW;
            case "APPLICATION_IN_PROGRESS" -> APPLICATION_IN_PROGRESS;
            case "APPLICATION_SUCCESS" -> APPLICATION_SUCCESS;
            case "APPLICATION_REJECTED" -> APPLICATION_REJECTED;
            case "RESUME_REJECTED" -> RESUME_REJECTED;
            case "PENDING" -> PENDING;
            case "REGISTERING_RESULTS" -> REGISTERING_RESULTS;
            default -> throw new IllegalArgumentException("지원 단계가 잘못되었습니다.");
        };
    }

    public static boolean isExisted(String value) {
        return switch (value.toUpperCase()) {
            case "RESUME_UNDER_REVIEW" -> true;
            case "WAITING_FOR_INTERVIEW" -> true;
            case "FILLING_OUT_DOCUMENTS" -> true;
            case "DOCUMENT_UNDER_REVIEW" -> true;
            case "APPLICATION_IN_PROGRESS" -> true;
            case "APPLICATION_SUCCESS" -> true;
            case "APPLICATION_REJECTED" -> true;
            case "RESUME_REJECTED" -> true;
            case "PENDING" -> true;
            case "REGISTERING_RESULTS" -> true;
            default -> false;
        };
    }
}
