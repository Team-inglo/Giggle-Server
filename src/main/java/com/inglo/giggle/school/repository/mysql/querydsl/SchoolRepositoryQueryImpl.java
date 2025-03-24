package com.inglo.giggle.school.repository.mysql.querydsl;

import com.inglo.giggle.school.domain.QSchool;
import com.inglo.giggle.school.domain.School;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SchoolRepositoryQueryImpl implements SchoolRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<School> findAllBySearch(Pageable pageable, String search) {

        QSchool school = QSchool.school;

        JPAQuery<School> baseQuery = queryFactory
                .selectFrom(school);


        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 이름 + 이메일 + 주소 + 전화번호
            baseQuery.where(
                    school.schoolName.containsIgnoreCase(search)
                            .or(school.instituteName.containsIgnoreCase(search))
                            .or(school.coordinatorName.containsIgnoreCase(search))
                            .or(school.address.addressName.containsIgnoreCase(search))
            );
        }

        long total = Optional.ofNullable(
                baseQuery.clone()
                        .select(school.id.count())
                        .fetchOne()
        ).orElse(0L);

        List<School> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }
}
