package com.inglo.giggle.posting.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingOverviewResponseDto;

@UseCase
public interface ReadJobPostingOverviewUseCase {

    /**
     * 유학생과 고용주가 공고 게시물 상세를 조회합니다.
     *
     * @param page              페이지
     * @param size              페이지 크기
     * @param jobTitle          채용공고 제목
     * @param sorting           정렬
     * @param region1Depth      지역 1차
     * @param region2Depth      지역 2차
     * @param region3Depth      지역 3차
     * @param industry          업종
     * @param workPeriod        근무기간
     * @param workDaysPerWeek   주근무일
     * @param workingDay        근무요일
     * @param workingHours      근무시간
     * @param recruitmentPeriod 채용기간
     * @param employmentType    고용형태
     * @param visa              비자
     *
     * @return 채용공고 목록 조회 응답 DTO
     */
    ReadJobPostingOverviewResponseDto execute(
            Integer page,
            Integer size,
            String jobTitle,
            String sorting,
            String region1Depth,
            String region2Depth,
            String region3Depth,
            String industry,
            String workPeriod,
            String workDaysPerWeek,
            String workingDay,
            String workingHours,
            String recruitmentPeriod,
            String employmentType,
            String visa
    );

    /**
     * 게스트가 공고 목록을 조회합니다.
     *
     * @param page              페이지
     * @param size              페이지 크기
     * @param type              타입
     *
     * @return 채용공고 목록 조회 응답 DTO
     */
    ReadJobPostingOverviewResponseDto execute(
            Integer page,
            Integer size,
            String type
    );
}
