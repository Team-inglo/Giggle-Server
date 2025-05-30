package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.domain.Resume;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResumeService {

    public Resume createResume(User user) {
        return Resume.builder()
                .user(user)
                .build();
    }

    public void checkResumeValidation(Resume resume, UUID accountId) {
        if (!resume.getUser().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }
}
