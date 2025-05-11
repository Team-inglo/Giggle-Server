package com.inglo.giggle.school.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.school.adapter.out.persistence.entity.SchoolEntity;
import com.inglo.giggle.school.adapter.out.persistence.mapper.SchoolMapper;
import com.inglo.giggle.school.adapter.out.persistence.repository.mysql.SchoolJpaRepository;
import com.inglo.giggle.school.application.port.out.CreateSchoolPort;
import com.inglo.giggle.school.application.port.out.DeleteSchoolPort;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.application.port.out.UpdateSchoolPort;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.school.persistence.entity.QSchoolEntity;
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
public class SchoolPersistenceAdapter implements LoadSchoolPort, CreateSchoolPort, UpdateSchoolPort, DeleteSchoolPort {

    private final SchoolJpaRepository schoolJpaRepository;
    private final SchoolMapper schoolMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public School loadSchool(Long id) {
        return schoolMapper.toDomain(schoolJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHOOL)));
    }

    @Override
    public List<School> loadSchools(List<Long> ids) {
        return schoolJpaRepository.findAllById(ids)
                .stream()
                .map(schoolMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<School> loadSchoolOptional(UUID userId) {
        return schoolJpaRepository.findTopByUserIdOrderByGraduationDateDesc(userId)
                .map(schoolMapper::toDomain);
    }

    @Override
    public List<Object[]> loadUserIdsAndSchools(List<UUID> userIds) {
        return schoolJpaRepository.findUserIdsWithMostRecentSchoolNames(userIds);
    }

    @Override
    public School loadSchool(String schoolName) {
        return schoolMapper.toDomain(schoolJpaRepository.findBySchoolName(schoolName)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SCHOOL)));
    }

    @Override
    public Page<School> loadSchool(Pageable pageable, String search) {

        QSchoolEntity school = QSchoolEntity.schoolEntity;

        JPAQuery<SchoolEntity> baseQuery = queryFactory
                .selectFrom(school);


        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 학교명, 기관명, 담당자명, 주소명
            baseQuery.where(
                    school.schoolName.containsIgnoreCase(search)
                            .or(school.instituteName.containsIgnoreCase(search))
                            .or(school.coordinatorName.containsIgnoreCase(search))
                            .or(school.addressEntity.addressName.containsIgnoreCase(search))
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
                .fetch()
                .stream()
                .map(schoolMapper::toDomain)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void createSchool(School school) {
        SchoolEntity schoolEntity = schoolMapper.toEntity(school);
        schoolJpaRepository.save(schoolEntity);
    }

    @Override
    public void updateSchool(School school) {
        SchoolEntity schoolEntity = schoolMapper.toEntity(school);
        schoolJpaRepository.save(schoolEntity);
    }

    @Override
    public void deleteSchool(School school) {
        SchoolEntity schoolEntity = schoolMapper.toEntity(school);
        schoolJpaRepository.delete(schoolEntity);
    }
}
