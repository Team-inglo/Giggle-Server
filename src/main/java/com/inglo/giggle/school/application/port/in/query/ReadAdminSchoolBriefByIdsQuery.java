package com.inglo.giggle.school.application.port.in.query;

import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolBriefByIdsResult;

import java.util.List;

public interface ReadAdminSchoolBriefByIdsQuery {

    ReadAdminSchoolBriefByIdsResult execute(List<Long> ids);
}
