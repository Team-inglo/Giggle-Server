package com.inglo.giggle.document.adapter.out.persistence.mapper;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.adapter.out.persistence.entity.DocumentEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.IntegratedApplicationEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.PartTimeEmploymentPermitEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.StandardLaborContractEntity;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentMapper {

    private final PartTimeEmploymentPermitMapper partTimeEmploymentPermitMapper;
    private final StandardLaborContractMapper standardLaborContractMapper;
    private final IntegratedApplicationMapper integratedApplicationMapper;

    public Document toDomain(DocumentEntity entity, List<ContractWorkDayTime> workDayTimes, List<Reject> rejects) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof StandardLaborContractEntity standardLaborContract) {
            return standardLaborContractMapper.toDomain(standardLaborContract, workDayTimes, rejects);
        }
        else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    public Document toDomain(DocumentEntity entity, List<Reject> rejects) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof PartTimeEmploymentPermitEntity partTimeEmploymentPermit) {
            return partTimeEmploymentPermitMapper.toDomain(partTimeEmploymentPermit, rejects);
        } else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    public Document toDomain(DocumentEntity entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof IntegratedApplicationEntity integratedApplication) {
            return integratedApplicationMapper.toDomain(integratedApplication);
        } else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    public DocumentEntity toEntity(Document domain) {
        if (domain == null) {
            return null;
        }
        if (domain instanceof PartTimeEmploymentPermit partTimeEmploymentPermit) {
            return partTimeEmploymentPermitMapper.toEntity(partTimeEmploymentPermit);
        } else if (domain instanceof StandardLaborContract standardLaborContract) {
            return standardLaborContractMapper.toEntity(standardLaborContract);
        } else if (domain instanceof IntegratedApplication integratedApplication) {
            return integratedApplicationMapper.toEntity(integratedApplication);
        } else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }
}
