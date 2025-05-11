package com.inglo.giggle.resume.adapter;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.resume.adapter.out.persistence.entity.AdditionalLanguageEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.LanguageSkillEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.ResumeEntity;
import com.inglo.giggle.resume.adapter.out.persistence.entity.WorkExperienceEntity;
import com.inglo.giggle.resume.adapter.out.persistence.mapper.AdditionalLanguageMapper;
import com.inglo.giggle.resume.adapter.out.persistence.mapper.EducationMapper;
import com.inglo.giggle.resume.adapter.out.persistence.mapper.LanguageSkillMapper;
import com.inglo.giggle.resume.adapter.out.persistence.mapper.ResumeMapper;
import com.inglo.giggle.resume.adapter.out.persistence.mapper.WorkExperienceMapper;
import com.inglo.giggle.resume.adapter.out.persistence.repository.mysql.AdditionalLanguageJpaRepository;
import com.inglo.giggle.resume.adapter.out.persistence.repository.mysql.EducationJpaRepository;
import com.inglo.giggle.resume.adapter.out.persistence.repository.mysql.LanguageSkillJpaRepository;
import com.inglo.giggle.resume.adapter.out.persistence.repository.mysql.ResumeJpaRepository;
import com.inglo.giggle.resume.adapter.out.persistence.repository.mysql.WorkExperienceJpaRepository;
import com.inglo.giggle.resume.application.port.out.CreateResumePort;
import com.inglo.giggle.resume.application.port.out.LoadResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateAdditionalLanguagePort;
import com.inglo.giggle.resume.application.port.out.UpdateEducationPort;
import com.inglo.giggle.resume.application.port.out.UpdateLanguageSkillPort;
import com.inglo.giggle.resume.application.port.out.UpdateResumePort;
import com.inglo.giggle.resume.application.port.out.UpdateWorkExperiencePort;
import com.inglo.giggle.resume.domain.AdditionalLanguage;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.WorkExperience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ResumePersistenceAdapter implements
        LoadResumePort, CreateResumePort,
        UpdateAdditionalLanguagePort, UpdateEducationPort, UpdateWorkExperiencePort, UpdateLanguageSkillPort, UpdateResumePort {
    private final ResumeJpaRepository resumeJpaRepository;
    private final AdditionalLanguageJpaRepository additionalLanguageJpaRepository;
    private final EducationJpaRepository educationJpaRepository;
    private final LanguageSkillJpaRepository languageSkillJpaRepository;
    private final WorkExperienceJpaRepository workExperienceJpaRepository;
    private final ResumeMapper resumeMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EducationMapper educationMapper;
    private final LanguageSkillMapper languageSkillMapper;
    private final AdditionalLanguageMapper additionalLanguageMapper;

    @Override
    public Resume loadResume(UUID id) {

        // 애그리거트 루트인 Resume 의 엔티티를 조회
        ResumeEntity resumeEntity = resumeJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESUME));

        // 매퍼를 이용하여 자식인 WorkExperience 의 도메인 리스트를 가져옴 (부모를 도메인으로 변환하기 위해선 자식 도메인 그 자체의 리스트가 필요)
        List<WorkExperience> workExperiences = workExperienceJpaRepository.findAllByResumeEntityAccountId(id)
                .stream()
                .map(workExperienceMapper::toDomain)
                .toList();

        // 매퍼를 이용하여 자식인 Education 의 도메인 리스트를 가져옴 (부모를 도메인으로 변환하기 위해선 자식 도메인 그 자체의 리스트가 필요)
        List<Education> educations = educationJpaRepository.findAllByResumeEntityAccountId(id)
                .stream()
                .map(educationMapper::toDomain)
                .toList();

        // LanguageSkill 또한 AdditionalLanguage 의 부모이기 때문에, 우선 부모인 LanguageSkill 의 엔티티를 조회 (-> 이후 자식을 합쳐 LanguageSkill 도메인으로 변환하여 Resume 만들 것)
        LanguageSkillEntity languageSkillEntity = languageSkillJpaRepository.findById(resumeEntity.getAccountId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL));

        // 매퍼를 이용하여 LanguageSkill 의 자식인 AdditionalLanguage 의 도메인 리스트를 가져옴 (부모를 도메인으로 변환하기 위해선 자식 도메인 그 자체의 리스트가 필요)
        List<AdditionalLanguage> additionalLanguages = additionalLanguageJpaRepository.findAllByLanguageSkillsId(id)
                .stream()
                .map(additionalLanguageMapper::toDomain)
                .toList();

        // LanguageSkillEntity 를 LanguageSkill 도메인으로 변환
        LanguageSkill languageSkill = languageSkillMapper.toDomain(languageSkillEntity, additionalLanguages);

        // ResumeEntity 를 Resume 도메인으로 변환
        return resumeMapper.toDomain(resumeEntity, workExperiences, educations, languageSkill);
    }

    @Override
    public void createResume(Resume resume) {
        // ResumeEntity 를 DB 에 저장
        ResumeEntity resumeEntity = resumeMapper.toEntity(resume);
        resumeJpaRepository.save(resumeEntity);

        // LanguageSkillEntity 를 DB 에 저장
        LanguageSkillEntity languageSkillEntity = languageSkillMapper.toEntity(resume.getLanguageSkill());
        languageSkillJpaRepository.save(languageSkillEntity);
    }

    @Override
    public void updateResume(Resume resume) {

        ResumeEntity resumeEntity = resumeMapper.toEntity(resume);

        // ResumeEntity 를 DB 에 저장
        resumeJpaRepository.save(resumeEntity);
    }

    @Override
    public void updateAdditionalLanguages(Resume resume) {
        UUID resumeId = resume.getAccountId();

        // Mapper 에서 toEntity 를 위해 사용하기 위한 LanguageSkillEntity 조회
        LanguageSkillEntity languageSkillEntity = languageSkillMapper.toEntity(resume.getLanguageSkill());

        // 기존에 DB 에 저장된 AdditionalLanguageEntity 조회
        List<AdditionalLanguageEntity> existingEntities = additionalLanguageJpaRepository.findAllByLanguageSkillsId(resumeId);

        // Map 으로 변환
        Map<Long, AdditionalLanguageEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(AdditionalLanguageEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<AdditionalLanguageEntity> toSave = new ArrayList<>();
        List<AdditionalLanguageEntity> toUpdate = new ArrayList<>();

        for (AdditionalLanguage input : resume.getLanguageSkill().getAdditionalLanguages()) {
            if (input.getId() == null) { // 생성의 경우
                toSave.add(additionalLanguageMapper.toEntity(input, languageSkillEntity));
            } else { // 수정의 경우
                inputIds.add(input.getId()); // inputIds 에 추가(이후 삭제 대상인지 판단을 위함)
                AdditionalLanguageEntity existing = existingMap.get(input.getId()); // 기존 DB 엔티티로 만든 Map 에서, Application Layer 에서 받은 객체의 id 가 존재하는지 확인
                if (existing == null) { // 존재하지 않는다면, CommonException 발생(ID 가 존재하므로 생성은 아닌데, DB 에는 존재하지 않음. 정합성 문제)
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                AdditionalLanguageEntity updated = additionalLanguageMapper.toEntity(input, languageSkillEntity);
                // equals 메서드를 사용하여 기존 엔티티와 업데이트된 엔티티를 비교. 다르다면 업데이트 리스트에 추가
                if (!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<AdditionalLanguageEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();

        if (!toSave.isEmpty()) additionalLanguageJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) additionalLanguageJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) additionalLanguageJpaRepository.deleteAll(toDelete);
    }

    @Override
    public void updateEducations(Resume resume) {

        ResumeEntity resumeEntity = resumeMapper.toEntity(resume);

        // 기존에 DB 에 저장된 EducationEntity 조회
        List<EducationEntity> existingEntities = educationJpaRepository.findAllByResumeEntityAccountId(resume.getAccountId());

        // Map 으로 변환
        Map<Long, EducationEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(EducationEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<EducationEntity> toSave = new ArrayList<>();
        List<EducationEntity> toUpdate = new ArrayList<>();

        for (Education input : resume.getEducations()) {
            if (input.getId() == null) { // 생성의 경우
                toSave.add(educationMapper.toEntity(input, resumeEntity));
            } else { // 수정의 경우
                inputIds.add(input.getId()); // inputIds 에 추가(이후 삭제 대상인지 판단을 위함)
                EducationEntity existing = existingMap.get(input.getId()); // 기존 DB 엔티티로 만든 Map 에서, Application Layer 에서 받은 객체의 id 가 존재하는지 확인
                if (existing == null) { // 존재하지 않는다면, CommonException 발생(ID 가 존재하므로 생성은 아닌데, DB 에는 존재하지 않음. 정합성 문제)
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                EducationEntity updated = educationMapper.toEntity(input, resumeEntity);
                // equals 메서드를 사용하여 기존 엔티티와 업데이트된 엔티티를 비교. 다르다면 업데이트 리스트에 추가
                if (!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<EducationEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();
        if (!toSave.isEmpty()) educationJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) educationJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) educationJpaRepository.deleteAll(toDelete);
    }

    @Override
    public void updateWorkExperiences(Resume resume) {
        // Mapper 에서 toEntity 를 위해 사용하기 위한 ResumeEntity 조회
        ResumeEntity resumeEntity = resumeMapper.toEntity(resume);

        // 기존에 DB 에 저장된 WorkExperienceEntity 조회
        List<WorkExperienceEntity> existingEntities = workExperienceJpaRepository.findAllByResumeEntityAccountId(resume.getAccountId());

        // Map 으로 변환
        Map<Long, WorkExperienceEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(WorkExperienceEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<WorkExperienceEntity> toSave = new ArrayList<>();
        List<WorkExperienceEntity> toUpdate = new ArrayList<>();

        for (WorkExperience input : resume.getWorkExperiences()) {
            if (input.getId() == null) { // 생성의 경우
                toSave.add(workExperienceMapper.toEntity(input, resumeEntity));
            } else { // 수정의 경우
                inputIds.add(input.getId()); // inputIds 에 추가(이후 삭제 대상인지 판단을 위함)
                WorkExperienceEntity existing = existingMap.get(input.getId()); // 기존 DB 엔티티로 만든 Map 에서, Application Layer 에서 받은 객체의 id 가 존재하는지 확인
                if (existing == null) { // 존재하지 않는다면, CommonException 발생(ID 가 존재하므로 생성은 아닌데, DB 에는 존재하지 않음. 정합성 문제)
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                WorkExperienceEntity updated = workExperienceMapper.toEntity(input, resumeEntity);
                // equals 메서드를 사용하여 기존 엔티티와 업데이트된 엔티티를 비교. 다르다면 업데이트 리스트에 추가
                if (!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<WorkExperienceEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();
        if (!toSave.isEmpty()) workExperienceJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) workExperienceJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) workExperienceJpaRepository.deleteAll(toDelete);
    }

    @Override
    public void updateLanguageSkill(Resume resume) {

        // 기존에 DB 에 저장된 LanguageSkillEntity 조회
        LanguageSkillEntity existingEntity = languageSkillJpaRepository.findById(resume.getAccountId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_LANGUAGE_SKILL));

        // LanguageSkillEntity 를 업데이트
        LanguageSkillEntity updatedEntity = languageSkillMapper.toEntity(resume.getLanguageSkill());

        // equals 메서드를 사용하여 기존 엔티티와 업데이트된 엔티티를 비교. 다르다면 업데이트
        if (!existingEntity.equals(updatedEntity)) {
            languageSkillJpaRepository.save(updatedEntity);
        }
    }
}
