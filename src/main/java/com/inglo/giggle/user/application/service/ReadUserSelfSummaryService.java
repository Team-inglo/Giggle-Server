package com.inglo.giggle.user.application.service;

import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.port.in.query.ReadUserResumeSummaryQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeSummaryResult;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolDetailQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolDetailResult;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.user.application.port.in.query.ReadUserSelfSummaryQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfSummaryResult;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSelfSummaryService implements ReadUserSelfSummaryQuery {

    private final LoadUserPort loadUserPort;


    private final ReadUserSchoolDetailQuery readUserSchoolDetailQuery;
    private final ReadUserResumeSummaryQuery readUserResumeSummaryQuery;

    @Override
    @Transactional(readOnly = true)
    public ReadUserSelfSummaryResult execute(CustomUserPrincipal principal, UUID accountId) {

        // 유저 정보 조회
        User user = loadUserPort.loadUserOrElseThrow(accountId);

        // 이력서 정보 조회
        ReadUserResumeSummaryResult resumeSummaryResult = readUserResumeSummaryQuery.execute(accountId);

        // School 정보 조회
        if (resumeSummaryResult.getSchoolId() != null) {
            ReadUserSchoolDetailResult schoolDetailResult = readUserSchoolDetailQuery.execute(accountId);

            return ReadUserSelfSummaryResult.of(
                    user.getProfileImgUrl(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirth() != null ? DateTimeUtil.convertLocalDateToString(user.getBirth()) : null,
                    schoolDetailResult.getSchoolName(),
                    resumeSummaryResult.getGrade(),
                    resumeSummaryResult.getGpa(),
                    user.getNotificationAllowed(),
                    resumeSummaryResult.getTopikLevel(),
                    resumeSummaryResult.getKiipLevel(),
                    resumeSummaryResult.getSejongLevel(),
                    resumeSummaryResult.getWeekendWorkHour(),
                    resumeSummaryResult.getWeekdayWorkHour(),
                    resumeSummaryResult.getIsLanguageSkill4OrMore(),
                    schoolDetailResult.getIsMetropolitanArea()
            );
        }
        // 학력 정보가 없을 경우 null 처리
        return ReadUserSelfSummaryResult.of(
                user.getProfileImgUrl(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirth() != null ? DateTimeUtil.convertLocalDateToString(user.getBirth()) : null,
                " - ",
                resumeSummaryResult.getGrade(),
                resumeSummaryResult.getGpa(),
                user.getNotificationAllowed(),
                resumeSummaryResult.getTopikLevel(),
                resumeSummaryResult.getKiipLevel(),
                resumeSummaryResult.getSejongLevel(),
                resumeSummaryResult.getWeekendWorkHour(),
                resumeSummaryResult.getWeekdayWorkHour(),
                resumeSummaryResult.getIsLanguageSkill4OrMore(),
                null
        );
    }

}
