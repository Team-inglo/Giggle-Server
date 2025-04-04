package com.inglo.giggle.posting.persistence.repository.impl;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.mapper.UserMapper;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.persistence.entity.BookMarkEntity;
import com.inglo.giggle.posting.persistence.mapper.BookMarkMapper;
import com.inglo.giggle.posting.persistence.repository.BookMarkRepository;
import com.inglo.giggle.posting.persistence.repository.mysql.BookMarkJpaRepository;
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
    public Optional<BookMark> findByUserIdAndJobPostingId(UUID userId, Long jobPostingId) {
        return bookMarkJpaRepository.findByUserIdAndJobPostingId(userId, jobPostingId)
                        .map(BookMarkMapper::toDomain);
    }

    @Override
    public Page<BookMark> findWithOwnerAndWorkDaysTimesByUser(
            User user,
            Pageable pageable
    ) {
        return bookMarkJpaRepository.findWithOwnerAndWorkDaysTimesByUser(UserMapper.toEntity(user), pageable)
                        .map(BookMarkMapper::toDomain);
    }

    @Override
    public List<BookMark> findByUser(User user) {
        return BookMarkMapper.toDomains(bookMarkJpaRepository.findByUserEntity(UserMapper.toEntity(user)));
    }

    @Override
    public BookMark save(BookMark bookMark) {
        BookMarkEntity entity = bookMarkJpaRepository.save(BookMarkMapper.toEntity(bookMark));
        return BookMarkMapper.toDomain(entity);
    }

    @Override
    public void delete(BookMark bookMark) {
        bookMarkJpaRepository.delete(BookMarkMapper.toEntity(bookMark));
    }
}
