package com.inglo.giggle.school.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.resume.application.port.in.query.ReadUserResumeSummaryQuery;
import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeSummaryResult;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolDetailQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolDetailResult;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSchoolDetailService implements ReadUserSchoolDetailQuery {

    private final LoadSchoolPort loadSchoolPort;

    private final ReadUserResumeSummaryQuery readUserResumeSummaryQuery;

    @Override
    public ReadUserSchoolDetailResult execute(UUID accountId) {

        // 유저의 이력서 정보 조회
        ReadUserResumeSummaryResult resumeSummaryResult = readUserResumeSummaryQuery.execute(accountId);

        // 유저의 학력 정보 조회
        School school = loadSchoolPort.loadSchool(resumeSummaryResult.getSchoolId());

        return ReadUserSchoolDetailResult.of(
                school.getSchoolName(),
                school.getInstituteName(),
                school.getCoordinatorName(),
                school.getCoordinatorPhoneNumber(),
                AddressResponseDto.from(school.getAddress()),
                school.getIsMetropolitan()
        );
    }


}
