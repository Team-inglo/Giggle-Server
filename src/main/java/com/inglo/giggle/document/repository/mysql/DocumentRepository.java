package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Document;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{

    @EntityGraph(attributePaths = {"userOwnerJobPosting"})
    Optional<Document> findWithUserOwnerJobPostingById(Long id);
}
