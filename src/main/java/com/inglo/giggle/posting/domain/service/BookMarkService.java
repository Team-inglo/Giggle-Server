package com.inglo.giggle.posting.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.stereotype.Service;

@Service
public class BookMarkService {

    public BookMark createBookMark(User user, JobPosting jobPosting){
        return BookMark.builder()
                .user(user)
                .jobPosting(jobPosting)
                .build();
    }
}
