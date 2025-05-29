package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.resume.repository.BookMarkResumeRepository;
import com.inglo.giggle.resume.repository.mysql.BookMarkResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookMarkResumeRepositoryImpl implements BookMarkResumeRepository {

    private final BookMarkResumeJpaRepository bookMarkResumeJpaRepository;
}
