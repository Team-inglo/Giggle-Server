package com.inglo.giggle.posting.repository;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.BookMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookMarkRepository {

    Optional<BookMark> findByUserIdAndJobPostingId(UUID userId, Long jobPostingId);

    Page<BookMark> findWithOwnerAndWorkDaysTimesByUser(
            User user,
            Pageable pageable
    );

    List<BookMark> findByUser(User user);

    void save(BookMark bookMark);

    void delete(BookMark bookMark);
}
