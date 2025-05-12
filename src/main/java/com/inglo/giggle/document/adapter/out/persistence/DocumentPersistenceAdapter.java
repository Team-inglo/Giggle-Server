package com.inglo.giggle.document.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.adapter.out.persistence.entity.ContractWorkDayTimeEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.DocumentEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.IntegratedApplicationEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.PartTimeEmploymentPermitEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.RejectEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.StandardLaborContractEntity;
import com.inglo.giggle.document.adapter.out.persistence.mapper.ContractWorkDayTimeMapper;
import com.inglo.giggle.document.adapter.out.persistence.mapper.DocumentMapper;
import com.inglo.giggle.document.adapter.out.persistence.mapper.IntegratedApplicationMapper;
import com.inglo.giggle.document.adapter.out.persistence.mapper.PartTimeEmploymentPermitMapper;
import com.inglo.giggle.document.adapter.out.persistence.mapper.RejectMapper;
import com.inglo.giggle.document.adapter.out.persistence.mapper.StandardLaborContractMapper;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.ContractWorkDayTimeJpaRepository;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.DocumentJpaRepository;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.IntegratedApplicationJpaRepository;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.PartTimeEmploymentPermitJpaRepository;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.RejectJpaRepository;
import com.inglo.giggle.document.adapter.out.persistence.repository.mysql.StandardLaborContractJpaRepository;
import com.inglo.giggle.document.application.port.out.CreateIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.CreatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.CreateStandardLaborContractPort;
import com.inglo.giggle.document.application.port.out.LoadDocumentPort;
import com.inglo.giggle.document.application.port.out.LoadIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.application.port.out.UpdateContractWorkDayTimePort;
import com.inglo.giggle.document.application.port.out.UpdateIntegratedApplicationPort;
import com.inglo.giggle.document.application.port.out.UpdatePartTimeEmploymentPermitPort;
import com.inglo.giggle.document.application.port.out.UpdateRejectPort;
import com.inglo.giggle.document.application.port.out.UpdateStandardLaborContractPort;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.Reject;
import com.inglo.giggle.document.domain.StandardLaborContract;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DocumentPersistenceAdapter implements
        LoadDocumentPort,
        LoadStandardLaborContractPort, CreateStandardLaborContractPort, UpdateStandardLaborContractPort,
        UpdateContractWorkDayTimePort,
        LoadIntegratedApplicationPort, CreateIntegratedApplicationPort, UpdateIntegratedApplicationPort,
        LoadPartTimeEmploymentPermitPort, CreatePartTimeEmploymentPermitPort, UpdatePartTimeEmploymentPermitPort,
        UpdateRejectPort
{

    private final DocumentJpaRepository documentJpaRepository;
    private final DocumentMapper documentMapper;
    private final StandardLaborContractJpaRepository standardLaborContractJpaRepository;
    private final StandardLaborContractMapper standardLaborContractMapper;
    private final ContractWorkDayTimeJpaRepository contractWorkDayTimeJpaRepository;
    private final ContractWorkDayTimeMapper contractWorkDayTimeMapper;
    private final IntegratedApplicationJpaRepository integratedApplicationJpaRepository;
    private final IntegratedApplicationMapper integratedApplicationMapper;
    private final PartTimeEmploymentPermitJpaRepository partTimeEmploymentPermitJpaRepository;
    private final PartTimeEmploymentPermitMapper partTimeEmploymentPermitMapper;
    private final RejectJpaRepository rejectJpaRepository;
    private final RejectMapper rejectMapper;

    @Override
    public Document loadAllDocumentOrElseThrow(Long id) {

        List<Reject> rejects = rejectJpaRepository.findAllByDocumentEntityId(id)
                .stream()
                .map(rejectMapper::toDomain)
                .toList();

        DocumentEntity documentEntity = documentJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DOCUMENT));

        if (documentEntity instanceof StandardLaborContractEntity standardLaborContractEntity) {

            List<ContractWorkDayTime> workDayTimes = contractWorkDayTimeJpaRepository.findByStandardLaborContractEntityId(id)
                    .stream()
                    .map(contractWorkDayTimeMapper::toDomain)
                    .toList();

            // Lazy Loading 걸려있는 필드들 초기화
            Hibernate.initialize(standardLaborContractEntity.getInsurances());
            Hibernate.initialize(standardLaborContractEntity.getWeeklyRestDays());

            return documentMapper.toDomain(standardLaborContractEntity, workDayTimes, rejects);

        } else if (documentEntity instanceof IntegratedApplicationEntity integratedApplicationEntity) {
            return documentMapper.toDomain(integratedApplicationEntity);
        } else if (documentEntity instanceof PartTimeEmploymentPermitEntity partTimeEmploymentPermitEntity) {
            return documentMapper.toDomain(partTimeEmploymentPermitEntity, rejects);
        }
        else {
            throw new CommonException(ErrorCode.INVALID_DOCUMENT_TYPE);
        }
    }

    @Override
    public StandardLaborContract loadAllStandardLaborContractOrElseThrow(Long id) {

        List<Reject> rejects = rejectJpaRepository.findAllByDocumentEntityId(id)
                .stream()
                .map(rejectMapper::toDomain)
                .toList();

        List<ContractWorkDayTime> workDayTimes = contractWorkDayTimeJpaRepository.findByStandardLaborContractEntityId(id)
                .stream()
                .map(contractWorkDayTimeMapper::toDomain)
                .toList();

        return standardLaborContractMapper.toDomain(standardLaborContractJpaRepository.findWithWeeklyRestDaysAndInsurancesById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STANDARD_LABOR_CONTRACT)), workDayTimes, rejects);
    }

    @Override
    public StandardLaborContract loadStandardLaborContractWithContractWorkDayTimesOrElseThrow(Long id) {

        List<ContractWorkDayTime> workDayTimes = contractWorkDayTimeJpaRepository.findByStandardLaborContractEntityId(id)
                .stream()
                .map(contractWorkDayTimeMapper::toDomain)
                .toList();

        return standardLaborContractMapper.toDomainWithContractWorkDayTimes(standardLaborContractJpaRepository.findWithWeeklyRestDaysAndInsurancesById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_STANDARD_LABOR_CONTRACT)), workDayTimes);
    }

    @Override
    public StandardLaborContract loadStandardLaborContractWithRejectsByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {

        StandardLaborContractEntity standardLaborContractEntity = standardLaborContractJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null);

        if (standardLaborContractEntity == null) {
            return null;
        }
        else {
            List<Reject> rejects = rejectJpaRepository.findAllByDocumentEntityId(standardLaborContractEntity.getId())
                    .stream()
                    .map(rejectMapper::toDomain)
                    .toList();

            return standardLaborContractMapper.toDomainWithRejects(standardLaborContractEntity, rejects);
        }
    }

    @Override
    public StandardLaborContract loadStandardLaborContractByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return standardLaborContractMapper.toDomainAlone(standardLaborContractJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null));
    }

    @Override
    public void createStandardLaborContract(StandardLaborContract standardLaborContract) {
        standardLaborContractJpaRepository.save(standardLaborContractMapper.toEntity(standardLaborContract));
    }

    @Override
    public void updateStandardLaborContract(StandardLaborContract standardLaborContract) {
        standardLaborContractJpaRepository.save(standardLaborContractMapper.toEntity(standardLaborContract));
    }

    @Override
    public void updateContractWorkDayTime(StandardLaborContract standardLaborContract) {
        // Mapper 에서 toEntity 를 위해 사용하기 위한 StandardLaborContractEntity 조회
        StandardLaborContractEntity standardLaborContractEntity = standardLaborContractMapper.toEntity(standardLaborContract);

        // 기존에 DB에 저장된 ContractWorkDayTimeEntity 조회
        List<ContractWorkDayTimeEntity> existingEntities = contractWorkDayTimeJpaRepository.findByStandardLaborContractEntityId(standardLaborContract.getId());

        // Map 으로 변환
        Map<Long, ContractWorkDayTimeEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(ContractWorkDayTimeEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<ContractWorkDayTimeEntity> toSave = new ArrayList<>();
        List<ContractWorkDayTimeEntity> toUpdate = new ArrayList<>();

        for (ContractWorkDayTime input : standardLaborContract.getWorkDayTimes()) {
            if (input.getId() == null) {
                toSave.add(contractWorkDayTimeMapper.toEntity(input, standardLaborContractEntity));
            } else {
                inputIds.add(input.getId());
                ContractWorkDayTimeEntity existing = existingMap.get(input.getId());
                if (existing == null) {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                ContractWorkDayTimeEntity updated = contractWorkDayTimeMapper.toEntity(input, standardLaborContractEntity);

                if(!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<ContractWorkDayTimeEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();

        if (!toSave.isEmpty()) contractWorkDayTimeJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) contractWorkDayTimeJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) contractWorkDayTimeJpaRepository.deleteAll(toDelete);
    }

    @Override
    public IntegratedApplication loadIntegratedApplicationOrElseThrow(Long id) {
        return integratedApplicationMapper.toDomain(integratedApplicationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_INTEGRATED_APPLICATION)));
    }

    @Override
    public IntegratedApplication loadIntegratedApplicationByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {
        return integratedApplicationMapper.toDomain(integratedApplicationJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId)
                .orElse(null));
    }

    @Override
    public void createIntegratedApplication(IntegratedApplication integratedApplication) {
        integratedApplicationMapper.toEntity(integratedApplication);
    }

    @Override
    public void updateIntegratedApplication(IntegratedApplication integratedApplication) {
        integratedApplicationMapper.toEntity(integratedApplication);
    }

    @Override
    public PartTimeEmploymentPermit loadPartTimeEmploymentPermitOrElseThrow(Long id) {

        return partTimeEmploymentPermitMapper.toDomainAlone(partTimeEmploymentPermitJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PART_TIME_EMPLOYMENT_PERMIT)));
    }

    @Override
    public PartTimeEmploymentPermit loadAllPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {

        PartTimeEmploymentPermitEntity partTimeEmploymentPermitEntity = partTimeEmploymentPermitJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId).
                orElse(null);
        if (partTimeEmploymentPermitEntity == null) {
            return null;
        } else {
            List<Reject> rejects = rejectJpaRepository.findAllByDocumentEntityId(partTimeEmploymentPermitEntity.getId())
                    .stream()
                    .map(rejectMapper::toDomain)
                    .toList();

            return partTimeEmploymentPermitMapper.toDomain(partTimeEmploymentPermitEntity, rejects);
        }
    }

    @Override
    public PartTimeEmploymentPermit loadPartTimeEmploymentPermitByUserOwnerJobPostingIdOrElseNull(Long userOwnerJobPostingId) {

        PartTimeEmploymentPermitEntity partTimeEmploymentPermitEntity = partTimeEmploymentPermitJpaRepository.findByUserOwnerJobPostingId(userOwnerJobPostingId).
                orElse(null);
        if (partTimeEmploymentPermitEntity == null) {
            return null;
        } else {
            return partTimeEmploymentPermitMapper.toDomainAlone(partTimeEmploymentPermitEntity);
        }
    }

    @Override
    public void createPartTimeEmploymentPermit(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        partTimeEmploymentPermitJpaRepository.save(partTimeEmploymentPermitMapper.toEntity(partTimeEmploymentPermit));
    }

    @Override
    public void updatePartTimeEmploymentPermit(PartTimeEmploymentPermit partTimeEmploymentPermit) {
        partTimeEmploymentPermitJpaRepository.save(partTimeEmploymentPermitMapper.toEntity(partTimeEmploymentPermit));
    }

    @Override
    public void updateReject(Document document) {

        // Mapper 에서 toEntity 를 위해 사용하기 위한 DocumentEntity 조회
        DocumentEntity documentEntity = documentMapper.toEntity(document);

        // 기존에 DB에 저장된 RejectEntity 조회
        List<RejectEntity> existingEntities = rejectJpaRepository.findAllByDocumentEntityId(document.getId());

        // Map 으로 변환
        Map<Long, RejectEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(RejectEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<RejectEntity> toSave = new ArrayList<>();
        List<RejectEntity> toUpdate = new ArrayList<>();

        for (Reject input : document.getRejects()) {
            if (input.getId() == null) {
                toSave.add(rejectMapper.toEntity(input, documentEntity));
            } else {
                inputIds.add(input.getId());
                RejectEntity existing = existingMap.get(input.getId());
                if (existing == null) {
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                RejectEntity updated = rejectMapper.toEntity(input, documentEntity);

                if(!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<RejectEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();
        if (!toSave.isEmpty()) rejectJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) rejectJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) rejectJpaRepository.deleteAll(toDelete);
    }

}
