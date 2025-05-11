package com.inglo.giggle.resume.application.port.in.query;

import com.inglo.giggle.resume.application.port.in.result.ReadUserResumeSummaryResult;

import java.util.UUID;

public interface ReadUserResumeSummaryQuery {

    ReadUserResumeSummaryResult execute(UUID resumeId);
}
