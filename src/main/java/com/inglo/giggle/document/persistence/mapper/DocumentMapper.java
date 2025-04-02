package com.inglo.giggle.document.persistence.mapper;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.entity.DocumentEntity;
import com.inglo.giggle.document.persistence.entity.IntegratedApplicationEntity;
import com.inglo.giggle.document.persistence.entity.PartTimeEmploymentPermitEntity;
import com.inglo.giggle.document.persistence.entity.StandardLaborContractEntity;

import java.util.List;

public class DocumentMapper {
    public static Document toDomain(DocumentEntity entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof PartTimeEmploymentPermitEntity partTimeEmploymentPermit) {
            return PartTimeEmploymentPermitMapper.toDomain(partTimeEmploymentPermit);
        } else if (entity instanceof StandardLaborContractEntity standardLaborContract) {
            return StandardLaborContractMapper.toDomain(standardLaborContract);
        } else if (entity instanceof IntegratedApplicationEntity integratedApplication) {
            return IntegratedApplicationMapper.toDomain(integratedApplication);
        } else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    public static DocumentEntity toEntity(Document domain) {
        if (domain == null) {
            return null;
        }
        if (domain instanceof PartTimeEmploymentPermit partTimeEmploymentPermit) {
            return PartTimeEmploymentPermitMapper.toEntity(partTimeEmploymentPermit);
        } else if (domain instanceof StandardLaborContract standardLaborContract) {
            return StandardLaborContractMapper.toEntity(standardLaborContract);
        } else if (domain instanceof IntegratedApplication integratedApplication) {
            return IntegratedApplicationMapper.toEntity(integratedApplication);
        } else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    public static List<Document> toDomains(List<DocumentEntity> entities) {
        return entities.stream()
                .map(DocumentMapper::toDomain)
                .toList();
    }

    public static List<DocumentEntity> toEntities(List<Document> domains) {
        return domains.stream()
                .map(DocumentMapper::toEntity)
                .toList();
    }

}
