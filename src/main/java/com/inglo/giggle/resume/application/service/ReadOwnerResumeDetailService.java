package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.resume.application.port.in.query.ReadOwnerResumeDetailQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadOwnerResumeDetailResult;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
//TODO : 합칠때 변경
public class ReadOwnerResumeDetailService implements ReadOwnerResumeDetailQuery {

    private final LoadResumePort loadResumePort;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerResumeDetailResult execute(UUID accountId, Long userOwnerJobPostingId) {

//        // UserOwnerJobPostingAggregate 생성
//        UserOwnerJobPostingAggregate userOwnerJobPostingAggregate = getUserOwnerJobPostingAggregate(accountId, userOwnerJobPostingId);
//
//        // UserOwnerJobPosting 유효성 체크
//        userOwnerJobPostingAggregate.checkOwnerUserOwnerJobPostingValidation(accountId);
//
//        // ResumeAggregate 생성
//        Resume resume = getResumeAggregate(userOwnerJobPostingAggregate.getUserOwnerJobPosting().getUserId());
//
//        // EducationWithSchoolDtos 생성
//        List<EducationWithSchoolDto> educationWithSchoolDtos = getEducationWithSchoolDtos(resume.getEducations());
//
//        return ReadOwnerResumeDetailResult.of(userOwnerJobPostingAggregate, resume, educationWithSchoolDtos);
        return null;
    }
}
