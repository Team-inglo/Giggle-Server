package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.resume.domain.BookMarkResume;
import com.inglo.giggle.resume.repository.BookMarkResumeRepository;
import com.inglo.giggle.resume.repository.mysql.BookMarkResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookMarkResumeRepositoryImpl implements BookMarkResumeRepository {

    private final BookMarkResumeJpaRepository bookMarkResumeJpaRepository;

    @Override
    public Optional<BookMarkResume> findByOwnerIdAndResumeId(UUID ownerId, UUID resumeId) {
        return bookMarkResumeJpaRepository.findByOwnerIdAndResumeId(ownerId, resumeId);
    }

    @Override
    public void delete(BookMarkResume bookMarkResume) {
        bookMarkResumeJpaRepository.delete(bookMarkResume);
    }

    @Override
    public void save(BookMarkResume bookMarkResume) {
        bookMarkResumeJpaRepository.save(bookMarkResume);
    }

    @Override
    public List<BookMarkResume> findAllByResumeAccountIdIn(Set<UUID> resumeAccountIds) {
        return bookMarkResumeJpaRepository.findAllByResumeAccountIdIn(resumeAccountIds);
    }
}
