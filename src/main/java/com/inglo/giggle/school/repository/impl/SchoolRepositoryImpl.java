package com.inglo.giggle.school.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.school.domain.QSchool;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.repository.SchoolRepository;
import com.inglo.giggle.school.repository.mysql.SchoolJpaRepository;
import com.querydsl.jpa.impl.JPAQuery;
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
public class SchoolRepositoryImpl implements SchoolRepository {

    private final SchoolJpaRepository schoolJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public School findByIdOrElseThrow(Long id) {
        return schoolJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHOOL));
    }

    @Override
    public Optional<School> findTopByUserIdOrderByGraduationDateDescOptional(UUID userId) {
        return schoolJpaRepository.findTopByUserIdOrderByGraduationDateDesc(userId);
    }

    @Override
    public List<Object[]> findUserIdsWithMostRecentSchoolNames(List<UUID> userIds) {
        return schoolJpaRepository.findUserIdsWithMostRecentSchoolNames(userIds);
    }

    @Override
    public School findBySchoolNameOrElseThrow(String schoolName) {
        return schoolJpaRepository.findBySchoolName(schoolName).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHOOL));
    }

    @Override
    public Page<School> findBySchoolNameContaining(String search, Pageable pageable) {
        return schoolJpaRepository.findBySchoolNameContaining(search, pageable);
    }

    @Override
    public void save(School school) {
        schoolJpaRepository.save(school);
    }

    @Override
    public void delete(School school) {
        schoolJpaRepository.delete(school);
    }

    @Override
    public Page<School> findAllBySearch(Pageable pageable, String search) {

        QSchool school = QSchool.school;

        JPAQuery<School> baseQuery = queryFactory
                .selectFrom(school);


        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 학교명, 기관명, 담당자명, 주소명
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
