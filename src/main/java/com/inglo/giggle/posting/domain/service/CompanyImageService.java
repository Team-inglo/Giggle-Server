package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.posting.domain.CompanyImage;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.stereotype.Service;

@Service
public class CompanyImageService {

    public CompanyImage createCompanyImage(String url, JobPosting jobPosting) {
        return CompanyImage.builder()
                .imgUrl(url)
                .jobPosting(jobPosting)
                .build();
    }
}
