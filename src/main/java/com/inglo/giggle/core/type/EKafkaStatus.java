package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EKafkaStatus {
    USER_RESUME_CONFIRMED("USER_RESUME_CONFIRMED", "이력서 승인", "고용주가 이력서를 승인하였습니다. 면접 일정을 잡아주세요!"),
    USER_RESUME_REJECTED("USER_RESUME_REJECTED", "이력서 거절", "고용주가 이력서를 거절하였습니다. 이력서를 확인하고 문제를 검토해보세요."),
    USER_INTERVIEW_COMPLETED("USER_INTERVIEW_COMPLETED", "면접 완료", "면접이 완료되었습니다. 서류를 작성해주세요."),
    USER_DOCUMENT_COMPLETED("USER_DOCUMENT_COMPLETED", "서류 작성 완료", "서류작성이 완료되었어요. 교내 유학생 담당자의 승인을 받아주세요."),
    USER_COMPLAINT("USER_DOCUMENT_REJECTED", "전자민원 신청 단계", "하이코리아 전자민원을 신청해주세요!"),
    USER_RESULT("USER_RESULT", "결과 기록", "하이코리아 신청 결과가 나왔나요? 결과를 기록해주세요."),
    USER_STANDARD_LABOR_CONTRACT("USER_STANDARD_LABOR_CONTRACT", "근로계약서 수정", "고용주가 근로계약서를 수정하였습니다. 내용을 다시 확인해주세요!"),
    USER_PART_TIME_EMPLOYMENT_PERMIT("USER_PART_TIME_EMPLOYMENT_PERMIT", "시간제 취업허가서 수정", "고용주가 시간제취업허가서를 수정하였습니다. 내용을 다시 확인해주세요!"),
    OWNER_NEW_APPLICANT("OWNER_NEW_APPLICANT", "새로운 지원자", "에 새로운 지원자가 들어왔습니다. 앱 내에서 확인해주세요!"),
    OWNER_DOCUMENT_CONFIRMED("OWNER_DOCUMENT_CONFIRMED", "서류 검토 완료", "지원자가 서류 검토를 끝마쳤습니다."),
    OWNER_DOCUMENT_REQUEST("OWNER_DOCUMENT_REQUEST", "서류 재작성", "지원자가 서류 재작성을 요청하였습니다. 앱 내에서 확인해주세요!"),
    OWNER_COMPLAINT("OWNER_COMPLAINT", "지원자 민원신청 완료", "지원자가 하이코리아 전자민원 신청을 하였습니다."),
    OWNER_STANDARD_LABOR_CONTRACT("OWNER_STANDARD_LABOR_CONTRACT", "지원자 근로계약서 수정", "지원자가 근로계약서를 수정하였습니다. 내용을 다시 확인해주세요!"),
    OWNER_PART_TIME_EMPLOYMENT_PERMIT("OWNER_PART_TIME_EMPLOYMENT_PERMIT", "지원자 시간제 취업허가서 수정", "지원자가 시간제취업허가서를 수정하였습니다. 내용을 다시 확인해주세요!")
    ;

    private final String enName;
    private final String krName;
    private final String message;

    public static EKafkaStatus findByEnName(String enName) {
        for (EKafkaStatus eKafkaStatus : values()) {
            if (eKafkaStatus.getEnName().equals(enName)) {
                return eKafkaStatus;
            }
        }
        return null;
    }





}
