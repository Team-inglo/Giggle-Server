package com.inglo.giggle.career.repository.impl;

import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.QBookMarkCareer;
import com.inglo.giggle.career.domain.QCareer;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.career.repository.CareerRepository;
import com.inglo.giggle.career.repository.mysql.CareerJpaRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CareerRepositoryImpl implements CareerRepository {

    private final CareerJpaRepository careerJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Career> findCareersOrderByBookMarks(String search, List<ECareerCategory> categories, Pageable pageable) {
        QCareer career = QCareer.career;
        QBookMarkCareer bookmark = QBookMarkCareer.bookMarkCareer;

        List<Career> results = queryFactory
                .select(career)
                .from(career)
                .leftJoin(career.bookMarkCareers, bookmark)
                .where(applyFilters(career, search, categories))
                .groupBy(career.id)
                .orderBy(bookmark.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(career.count())
                        .from(career)
                        .where(applyFilters(career, search, categories))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Career> findCareersOrderByCreatedAt(String search, List<ECareerCategory> categories, Pageable pageable) {
        QCareer career = QCareer.career;

        List<Career> results = queryFactory
                .selectFrom(career)
                .where(applyFilters(career, search, categories))
                .orderBy(career.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(career.count())
                        .from(career)
                        .where(applyFilters(career, search, categories))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Career> findBookmarkedCareersByUser(UUID userId, Pageable pageable) {
        QCareer career = QCareer.career;
        QBookMarkCareer bookmark = QBookMarkCareer.bookMarkCareer;

        List<Career> content = queryFactory
                .select(career)
                .from(bookmark)
                .join(bookmark.career, career)
                .where(bookmark.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = Optional.ofNullable(
                queryFactory
                        .select(bookmark.count())
                        .from(bookmark)
                        .where(bookmark.user.id.eq(userId))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Career findByIdOrElseThrow(Long careerId) {
        return careerJpaRepository.findById(careerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_CAREER));
    }

    @Override
    public void save(Career career) {
        careerJpaRepository.save(career);
    }

    private BooleanExpression applyFilters(QCareer career, String search, List<ECareerCategory> categories) {
        BooleanExpression predicate = career.deletedAt.isNull();

        if (search != null && !search.isBlank()) {
            predicate = predicate.and(career.title.containsIgnoreCase(search));
        }

        if (categories != null && !categories.isEmpty()) {
            predicate = predicate.and(career.category.in(categories));
        }

        return predicate;
    }
}
