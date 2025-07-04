package com.inglo.giggle.resume.repository;

import com.inglo.giggle.resume.domain.BookMarkResume;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BookMarkResumeRepository {

    Optional<BookMarkResume> findByOwnerIdAndResumeId(UUID userId, UUID resumeId);

    void delete(BookMarkResume bookMarkResume);

    void save(BookMarkResume bookMarkResume);

    List<BookMarkResume> findAllByResumeAccountIdIn(Set<UUID> resumeAccountIds);
}
