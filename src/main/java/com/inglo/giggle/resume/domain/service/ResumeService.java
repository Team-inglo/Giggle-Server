package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.security.domain.mysql.Account;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {

    public Resume createResume(User user) {
        return Resume.builder()
                .user(user)
                .build();
    }
}
