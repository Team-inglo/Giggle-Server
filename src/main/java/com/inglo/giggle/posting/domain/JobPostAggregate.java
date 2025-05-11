package com.inglo.giggle.posting.domain;

import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.domain.LanguageSkill;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPostAggregate {

    private JobPosting jobPosting;

    private List<CompanyImage> companyImages;

    private List<PostingWorkDayTime> postingWorkDayTimes;

    @Builder
    public JobPostAggregate(
            JobPosting jobPosting,
            List<CompanyImage> companyImages,
            List<PostingWorkDayTime> postingWorkDayTimes
    ) {
        this.jobPosting = jobPosting;
        this.companyImages = companyImages;
        this.postingWorkDayTimes = postingWorkDayTimes;
    }

    public Boolean isUserApplicableByWorkTime(Map<String, Integer> userWorkHours, Map<String, Integer> jobWorkHours) {
        return userWorkHours.get("weekendWorkHours") >= jobWorkHours.get("weekendWorkHours")
                && userWorkHours.get("weekdayWorkHours") >= jobWorkHours.get("weekdayWorkHours");
    }

    public Boolean isUserApplicableByJobCategory(LanguageSkill languageSkill) {
        if(jobPosting.getJobCategory().equals(EJobCategory.MANUFACTURING)){
            return (languageSkill.getTopikLevel() >= 4 && languageSkill.getSocialIntegrationLevel() >= 4);
        }
        return true;
    }
}
