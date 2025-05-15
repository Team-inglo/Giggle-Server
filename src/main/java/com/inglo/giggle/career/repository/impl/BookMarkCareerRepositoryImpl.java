package com.inglo.giggle.career.repository.impl;

import com.inglo.giggle.career.domain.BookMarkCareer;
import com.inglo.giggle.career.repository.BookMarkCareerRepository;
import com.inglo.giggle.career.repository.mysql.BookMarkCareerJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookMarkCareerRepositoryImpl implements BookMarkCareerRepository {

    private final BookMarkCareerJpaRepository bookMarkCareerJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByCareerIdAndUserId(Long careerId, UUID userId) {
        return bookMarkCareerJpaRepository.existsByCareerIdAndUserId(careerId, userId);
    }

    @Override
    public void save(BookMarkCareer bookMarkCareer) {
        bookMarkCareerJpaRepository.save(bookMarkCareer);
    }

    @Override
    public void deleteByCareerIdAndUserId(Long careerId, UUID accountId) {
        bookMarkCareerJpaRepository.deleteByCareerIdAndUserId(careerId, accountId);
    }

}
