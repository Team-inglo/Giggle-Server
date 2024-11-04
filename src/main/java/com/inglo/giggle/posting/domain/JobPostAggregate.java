package com.inglo.giggle.posting.domain;

import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.domain.Resume;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPostAggregate {

    private JobPosting jobPosting;

    private Resume resume;

    @Builder
    public JobPostAggregate(JobPosting jobPosting, Resume resume) {
        this.jobPosting = jobPosting;
        this.resume = resume;
    }

    public Boolean isUserApplicableByWorkTime(Map<String, Integer> userWorkHours, Map<String, Integer> jobWorkHours) {
        return userWorkHours.get("weekendWorkHours") >= jobWorkHours.get("weekendWorkHours")
                && userWorkHours.get("weekdayWorkHours") >= jobWorkHours.get("weekdayWorkHours");
    }

    public Boolean isUserApplicableByJobCategory() {
        if(jobPosting.getJobCategory().equals(EJobCategory.MANUFACTURING)){
            return (this.resume.getLanguageSkill().getTopikLevel() >= 4 && this.resume.getLanguageSkill().getSocialIntegrationLevel() >= 4);
        }
        return true;
    }
}
