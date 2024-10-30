package com.inglo.giggle.resume.domain.service;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import org.springframework.stereotype.Service;

@Service
public class EducationService {

    public EEducationLevel getEducationLevelByVisa(EVisa visa) {
        switch (visa) {
            case D_2_1 -> {
                return EEducationLevel.ASSOCIATE;
            }
            case D_2_2 -> {
                return EEducationLevel.BACHELOR;
            }
            case D_2_3 -> {
                return EEducationLevel.MASTER;
            }
            case D_2_4 -> {
                return EEducationLevel.DOCTOR;
            }
            default -> {
                return EEducationLevel.HIGHSCHOOL;
            }
        }
    }
}
