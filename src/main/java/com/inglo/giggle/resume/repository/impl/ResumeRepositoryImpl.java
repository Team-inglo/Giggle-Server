package com.inglo.giggle.resume.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.domain.QResume;
import com.inglo.giggle.resume.domain.QWorkPreference;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.type.EKorean;
import com.inglo.giggle.resume.repository.ResumeRepository;
import com.inglo.giggle.resume.repository.mysql.ResumeJpaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ResumeRepositoryImpl implements ResumeRepository {

    private final ResumeJpaRepository resumeJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Resume findByIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithWorkExperiencesAndLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findWithWorkExperiencesAndLanguageSkillByAccountId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithLanguageSkillByAccountIdOrElseThrow(UUID id) {
        return resumeJpaRepository.findWithLanguageSkillByAccountId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithEducationsAndLanguageSkillByAccountIdOrElseThrow(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Optional<Resume> findWithEducationsAndLanguageSkillByAccountIdOptional(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndLanguageSkillByAccountId(accountId);
    }

    @Override
    public Resume findWithEducationsByAccountIdOrElseThrow(UUID accountId) {
        return resumeJpaRepository.findWithEducationsByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public Resume findWithEducationsAndBookmarksByAccountIdOrElseThrow(UUID accountId) {
        return resumeJpaRepository.findWithEducationsAndBookmarksByAccountId(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));
    }

    @Override
    public void save(Resume resume) {
        resumeJpaRepository.save(resume);
    }

    @Override
    public Page<Resume> findResumesOrderByBookMarks(
            UUID accountId,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Pageable pageable
    ) {
        QResume resume = QResume.resume;
        QWorkPreference workPreference = QWorkPreference.workPreference;

        List<Resume> results = queryFactory
                .select(resume)
                .from(resume)
                .leftJoin(resume.user).fetchJoin()
                .leftJoin(resume.bookMarks).fetchJoin()
                .leftJoin(resume.workPreference, workPreference).fetchJoin()
                .leftJoin(workPreference.jobCategories).fetchJoin()
                .where(applyFilters(resume, visa, korean, major, nationality, industry))
                .orderBy(resume.bookMarks.size().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();
        long total = Optional.ofNullable(
                queryFactory
                        .select(resume.count())
                        .from(resume)
                        .where(applyFilters(resume, visa, korean, major, nationality, industry))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<Resume> findResumesOrderByCreatedAt(
            UUID accountId,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry,
            Pageable pageable
    ) {
        QResume resume = QResume.resume;
        QWorkPreference workPreference = QWorkPreference.workPreference;

        List<Resume> results = queryFactory
                .selectFrom(resume)
                .where(applyFilters(resume, visa, korean, major, nationality, industry))
                .leftJoin(resume.user).fetchJoin()
                .leftJoin(resume.bookMarks).fetchJoin()
                .leftJoin(resume.workPreference, workPreference).fetchJoin()
                .leftJoin(workPreference.jobCategories).fetchJoin()
                .orderBy(resume.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(resume.count())
                        .from(resume)
                        .where(applyFilters(resume, visa, korean, major, nationality, industry))
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression applyFilters(
            QResume resume,
            List<EVisa> visa,
            List<EKorean> korean,
            List<EMajor> major,
            List<ENationality> nationality,
            List<EJobCategory> industry
    ) {
        BooleanExpression predicate = resume.isPublic.isTrue();

        if (visa != null && !visa.isEmpty()) {
            predicate = predicate.and(resume.user.visa.in(visa));
        }
        if (korean != null && !korean.isEmpty()) {
            BooleanExpression koreanPredicate = null;
            for (EKorean k : korean) {
                BooleanExpression condition = getKoreanOrCondition(resume, k);
                koreanPredicate = (koreanPredicate == null) ? condition : koreanPredicate.or(condition);
            }
            if (koreanPredicate != null) {
                predicate = predicate.and(koreanPredicate);
            }
        }
        if (major != null && !major.isEmpty()) {
            predicate = predicate.and(resume.educations.any().major.in(major));
        }
        if (nationality != null && !nationality.isEmpty()) {
            log.info("[applyFilters] nationality: {}", nationality); // 리스트 자체
            for (ENationality n : nationality) {
                log.info("[applyFilters] nationality item: {} ({})", n.name(), n.getEnName());
            }
            predicate = predicate.and(resume.user.nationality.in(nationality));
        }
        if (industry != null && !industry.isEmpty()) {
            predicate = predicate.and(resume.workPreference.jobCategories.any().in(industry));
        }

        return predicate;
    }

    private BooleanExpression getKoreanOrCondition(QResume resume, EKorean korean) {
        // EKorean별로 포함해야 하는 TOPIK/사회통합/세종학당 레벨 범위 지정
        return switch (korean) {
            case NONE -> resume.languageSkill.topikLevel.eq(0)
                    .or(resume.languageSkill.socialIntegrationLevel.eq(0))
                    .or(resume.languageSkill.sejongInstituteLevel.eq(0));
            case BASIC -> resume.languageSkill.topikLevel.eq(1)
                    .or(resume.languageSkill.socialIntegrationLevel.eq(1))
                    .or(resume.languageSkill.sejongInstituteLevel.eq(1));
            case COMMUNICATE -> resume.languageSkill.topikLevel.eq(2)
                    .or(resume.languageSkill.socialIntegrationLevel.eq(2))
                    .or(resume.languageSkill.sejongInstituteLevel.eq(2));
            case WORKING -> (resume.languageSkill.topikLevel.between(3, 4))
                    .or(resume.languageSkill.socialIntegrationLevel.eq(3))
                    .or(resume.languageSkill.sejongInstituteLevel.between(3, 4));
            case ADVANCED -> resume.languageSkill.topikLevel.eq(5)
                    .or(resume.languageSkill.socialIntegrationLevel.eq(4))
                    .or(resume.languageSkill.sejongInstituteLevel.eq(5));
            case NATIVE -> resume.languageSkill.topikLevel.eq(6)
                    .or(resume.languageSkill.socialIntegrationLevel.eq(5))
                    .or(resume.languageSkill.sejongInstituteLevel.eq(6));
            default -> throw new IllegalArgumentException("한국어 능력 분류 오류");
        };
    }
}
