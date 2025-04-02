package com.inglo.giggle.posting.persistence.repository.mysql;

import com.inglo.giggle.posting.persistence.entity.CompanyImageEntity;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageJpaRepository extends JpaRepository<CompanyImageEntity, Long>{
    void deleteAllByIdIn(List<Long> ids);

    List<CompanyImageEntity> findAllByJobPostingEntityId(Long jobPostingId);

    List<CompanyImageEntity> findAllByJobPostingEntity(JobPostingEntity jobPostingEntity);
}
