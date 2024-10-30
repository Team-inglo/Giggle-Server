package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class PostWorkDayTimeService {

    public PostingWorkDayTime createPostingWorkDayTime(
            EDayOfWeek dayOfWeek,
            LocalTime workStartTime,
            LocalTime workEndTime,
            JobPosting jobPosting
    ) {
        return PostingWorkDayTime.builder()
                .dayOfWeek(dayOfWeek)
                .workStartTime(workStartTime)
                .workEndTime(workEndTime)
                .jobPosting(jobPosting)
                .build();
    }
}
