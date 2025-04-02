package com.inglo.giggle.core.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Method Not Allowed Error
    METHOD_NOT_ALLOWED(40500, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드입니다."),

    // Not Found Error
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "존재하지 않는 API 엔드포인트입니다."),
    NOT_FOUND_AUTHORIZATION_HEADER(40401, HttpStatus.NOT_FOUND, "Authorization 헤더가 존재하지 않습니다."),
    NOT_FOUND_ACCOUNT(40402, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_TEMPORARY_ACCOUNT(40403, HttpStatus.NOT_FOUND, "존재하지 않는 임시 사용자입니다."),
    NOT_FOUND_RESOURCE(40404, HttpStatus.NOT_FOUND, "해당 리소스가 존재하지 않습니다."),
    NOT_FOUND_TYPE(40405, HttpStatus.NOT_FOUND ,"타입이 존재하지 않습니다." ),
    NOT_FOUND_USER(40406, HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_OWNER(40407, HttpStatus.NOT_FOUND, "존재하지 않는 고용주입니다."),
    NOT_FOUND_AUTHENTICATION_CODE(40408, HttpStatus.NOT_FOUND, "존재하지 않는 인증코드입니다."),
    NOT_FOUND_AUTHENTICATION_CODE_HISTORY(40409, HttpStatus.NOT_FOUND, "존재하지 않는 인증코드 이력입니다."),
    NOT_FOUND_REFRESH_TOKEN(40410, HttpStatus.NOT_FOUND, "존재하지 않는 리프레시 토큰입니다."),
    NOT_FOUND_TEMPORARY_TOKEN(40411, HttpStatus.NOT_FOUND, "존재하지 않는 임시 토큰입니다."),
    NOT_FOUND_BANNER(40412, HttpStatus.NOT_FOUND, "존재하지 않는 배너입니다."),
    NOT_FOUND_CONTRACT_WORK_DAY_TIME(40413, HttpStatus.NOT_FOUND, "존재하지 않는 근무 시간입니다."),
    NOT_FOUND_DOCUMENT(40414, HttpStatus.NOT_FOUND, "존재하지 않는 서류입니다."),
    NOT_FOUND_INTEGRATED_APPLICATION(40415, HttpStatus.NOT_FOUND, "존재하지 않는 통합 지원 서류입니다."),
    NOT_FOUND_PART_TIME_EMPLOYMENT_PERMIT(40416, HttpStatus.NOT_FOUND, "존재하지 않는 시간제 취업 허가 서류입니다."),
    NOT_FOUND_REJECT(40417, HttpStatus.NOT_FOUND, "존재하지 않는 미승인입니다."),
    NOT_FOUND_STANDARD_LABOR_CONTRACT(40418, HttpStatus.NOT_FOUND, "존재하지 않는 표준 근로 계약 서류입니다."),
    NOT_FOUND_NOTIFICATION(40419, HttpStatus.NOT_FOUND, "존재하지 않는 알림입니다."),
    NOT_FOUND_BOOK_MARK(40420, HttpStatus.NOT_FOUND, "존재하지 않는 북마크입니다."),
    NOT_FOUND_COMPANY_IMAGE(40421, HttpStatus.NOT_FOUND, "존재하지 않는 회사 이미지입니다."),
    NOT_FOUND_JOB_POSTING(40421, HttpStatus.NOT_FOUND, "존재하지 않는 공고입니다."),
    NOT_FOUND_POSTING_WORK_DAY_TIME(40422, HttpStatus.NOT_FOUND, "존재하지 않는 근무 시간입니다."),
    NOT_FOUND_USER_OWNER_JOB_POSTING(40423, HttpStatus.NOT_FOUND, "존재하지 않는 유저 -> 고용주 지원입니다."),
    NOT_FOUND_ADDITIONAL_LANGUAGE(40424, HttpStatus.NOT_FOUND, "존재하지 않는 추가 언어입니다."),
    NOT_FOUND_EDUCATION(40425, HttpStatus.NOT_FOUND, "존재하지 않는 학력입니다."),
    NOT_FOUND_LANGUAGE_SKILL(40426, HttpStatus.NOT_FOUND, "존재하지 않는 한국어 언어 수준 입니다."),
    NOT_FOUND_RESUME(40427, HttpStatus.NOT_FOUND, "존재하지 않는 이력서입니다."),
    NOT_FOUND_WORK_EXPERIENCE(40428, HttpStatus.NOT_FOUND, "존재하지 않는 경력입니다."),
    NOT_FOUND_SCHOOL(40429, HttpStatus.NOT_FOUND, "존재하지 않는 학교입니다."),
    NOT_FOUND_TERM(40430, HttpStatus.NOT_FOUND, "존재하지 않는 약관입니다."),
    NOT_FOUND_TERM_ACCOUNT(40431, HttpStatus.NOT_FOUND, "존재하지 않는 약관에 동의한 사용자입니다."),
    NOT_FOUND_VERSION(40432, HttpStatus.NOT_FOUND, "존재하지 않는 버전입니다."),

    // Invalid Argument Error
    MISSING_REQUEST_PARAMETER(40000, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    INVALID_ARGUMENT(40001, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    INVALID_PARAMETER_FORMAT(40002, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."),
    INVALID_HEADER_ERROR(40003, HttpStatus.BAD_REQUEST, "유효하지 않은 헤더입니다."),
    MISSING_REQUEST_HEADER(40004, HttpStatus.BAD_REQUEST, "필수 요청 헤더가 누락되었습니다."),
    BAD_REQUEST_PARAMETER(40005, HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    UNSUPPORTED_MEDIA_TYPE(40006, HttpStatus.BAD_REQUEST, "지원하지 않는 미디어 타입입니다."),
    BAD_REQUEST_JSON(40007, HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."),
    INVALID_ACCOUNT_TYPE(40008, HttpStatus.BAD_REQUEST, "Account Type이 잘못되었습니다."),
    INVALID_DOCUMENT_TYPE(40009, HttpStatus.BAD_REQUEST, "Document Type이 잘못되었습니다."),
    ALREADY_EXIST_RESOURCE(40010, HttpStatus.BAD_REQUEST, "이미 존재하는 리소스입니다."),
    ALREADY_APPLIED_JOB_POSTING(40011, HttpStatus.BAD_REQUEST, "이미 지원한 공고입니다."),
    INVALID_PASSWORD(40012, HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    EXPIRED_JOB_POSTING(40013, HttpStatus.BAD_REQUEST, "지원 기간이 지난 공고입니다."),
    INVALID_DATE_RANGE(40014, HttpStatus.BAD_REQUEST, "종료기간이 시작기간보다 빠를 수 없습니다."),
    INVALID_ENUM_TYPE(40015, HttpStatus.BAD_REQUEST, "잘못된 Enum Type입니다."),
  
    // SIGN UP Error
    ALREADY_EXIST_ID(40200, HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    ALREADY_EXIST_EMAIL(40201, HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),

    // Access Denied Error
    ACCESS_DENIED(40300, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_LOGIN_USER(40301, HttpStatus.FORBIDDEN, "로그인하지 않은 사용자입니다."),

    // Unauthorized Error
    FAILURE_LOGIN(40100, HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
    EXPIRED_TOKEN_ERROR(40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN_ERROR(40102, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_MALFORMED_ERROR(40103, HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_TYPE_ERROR(40104, HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않거나 비어있습니다."),
    TOKEN_UNSUPPORTED_ERROR(40105, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    TOKEN_GENERATION_ERROR(40106, HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."),
    TOKEN_UNKNOWN_ERROR(40107, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),

    // Too Many Requests Error
    TOO_FAST_AUTHENTICATION_CODE_REQUESTS(42900, HttpStatus.TOO_MANY_REQUESTS, "인증코드 발급 속도가 너무 빠릅니다."),
    TOO_MANY_AUTHENTICATION_CODE_REQUESTS(42901, HttpStatus.TOO_MANY_REQUESTS, "인증코드 발급 요청이 너무 많습니다."),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다."),
    INTERNAL_DATA_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 데이터 에러입니다."),
    UPLOAD_FILE_ERROR(50002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다."),
    INTERNAL_SERVER_ERROR_WITH_OSRM(50003, HttpStatus.INTERNAL_SERVER_ERROR, "OSRM 서버 내부 에러입니다."),
    FIREBASE_CONFIGURATION_FAILED(50004, HttpStatus.INTERNAL_SERVER_ERROR, "Firebase 설정에 실패하였습니다."),

    // External Server Error
    EXTERNAL_SERVER_ERROR(50200, HttpStatus.BAD_GATEWAY, "서버 외부 에러입니다."),
    ;


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
