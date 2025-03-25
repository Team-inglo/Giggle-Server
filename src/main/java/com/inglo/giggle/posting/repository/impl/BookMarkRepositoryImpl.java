package com.inglo.giggle.posting.repository.impl;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.repository.BookMarkRepository;
import com.inglo.giggle.posting.repository.mysql.BookMarkJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookMarkRepositoryImpl implements BookMarkRepository {

    private final BookMarkJpaRepository bookMarkJpaRepository;

    @Override
    public BookMark findByIdOrElseThrow(Long id) {
        return bookMarkJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_BOOK_MARK));
    }

    @Override
    public Optional<BookMark> findByUserIdAndJobPostingId(UUID userId, Long jobPostingId) {
        return bookMarkJpaRepository.findByUserIdAndJobPostingId(userId, jobPostingId);
    }

    @Override
    public Page<BookMark> findWithOwnerAndWorkDaysTimesByUser(
            User user,
            Pageable pageable
    ) {
        return bookMarkJpaRepository.findWithOwnerAndWorkDaysTimesByUser(user, pageable);
    }

    @Override
    public List<BookMark> findByUser(User user) {
        return bookMarkJpaRepository.findByUser(user);
    }

    @Override
    public void save(BookMark bookMark) {
        bookMarkJpaRepository.save(bookMark);
    }

    @Override
    public void delete(BookMark bookMark) {
        bookMarkJpaRepository.delete(bookMark);
    }

    @Override
    public void deleteAll(List<BookMark> bookMarks) {
        bookMarkJpaRepository.deleteAll(bookMarks);
    }
}
