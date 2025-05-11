package com.inglo.giggle.posting.domain;

import com.inglo.giggle.security.account.domain.Account;

public class BookMarkAggregate {

    private BookMark bookMark;
    private JobPosting jobPosting;
    private Account account;

    public BookMarkAggregate(BookMark bookMark, JobPosting jobPosting, Account account) {
        this.bookMark = bookMark;
        this.jobPosting = jobPosting;
        this.account = account;
    }
}
