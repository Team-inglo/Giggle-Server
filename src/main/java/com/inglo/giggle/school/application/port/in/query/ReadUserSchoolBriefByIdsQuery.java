package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefByIdsResult;

import java.util.List;

public interface ReadUserSchoolBriefByIdsQuery {

    ReadUserSchoolBriefByIdsResult execute(List<Long> ids);
}
