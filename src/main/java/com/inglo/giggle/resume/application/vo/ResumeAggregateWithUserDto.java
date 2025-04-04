package com.inglo.giggle.resume.application.vo;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.resume.domain.ResumeAggregate;

public record ResumeAggregateWithUserDto(
        ResumeAggregate resumeAggregate,
        User user
) {
}
