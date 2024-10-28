package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class JobPostingService {

    public Map<String, Integer> calculateWorkHours(JobPosting jobPosting) {
        return jobPosting.calculateWorkHours();
    }

    public JobPosting createJobPosting(
            String title,
            EJobCategory jobCategory,
            Integer hourlyRate,
            LocalDate recruitmentDeadLine,
            EWorkPeriod workPeriod,
            Integer recruitmentNumber,
            EGender gender,
            Integer ageRestriction,
            EEducationLevel educationLevel,
            EVisa visa,
            String recruiterName,
            String recruiterEmail,
            String recruiterPhoneNumber,
            String description,
            String preferredConditions,
            EEmploymentType employmentType,
            Owner owner,
            Address address
    ) {
        return JobPosting.builder()
                .title(title)
                .jobCategory(jobCategory)
                .hourlyRate(hourlyRate)
                .recruitmentDeadLine(recruitmentDeadLine)
                .workPeriod(workPeriod)
                .recruitmentNumber(recruitmentNumber)
                .gender(gender)
                .ageRestriction(ageRestriction)
                .educationLevel(educationLevel)
                .visa(visa)
                .recruiterName(recruiterName)
                .recruiterEmail(recruiterEmail)
                .recruiterPhoneNumber(recruiterPhoneNumber)
                .description(description)
                .preferredConditions(preferredConditions)
                .employmentType(employmentType)
                .owner(owner)
                .address(address)
                .build();
    }

    public void updateJobPosting(
            JobPosting jobPosting,
            String title,
            EJobCategory jobCategory,
            Integer hourlyRate,
            LocalDate recruitmentDeadLine,
            EWorkPeriod workPeriod,
            Integer recruitmentNumber,
            EGender gender,
            Integer ageRestriction,
            EEducationLevel educationLevel,
            EVisa visa,
            String recruiterName,
            String recruiterEmail,
            String recruiterPhoneNumber,
            String description,
            String preferredConditions,
            EEmploymentType employmentType
    ) {
        jobPosting.updateJobPosting(
                title,
                jobCategory,
                hourlyRate,
                recruitmentDeadLine,
                workPeriod,
                recruitmentNumber,
                gender,
                ageRestriction,
                educationLevel,
                visa,
                recruiterName,
                recruiterEmail,
                recruiterPhoneNumber,
                description,
                preferredConditions,
                employmentType
        );
    }

}
