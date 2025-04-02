package com.inglo.giggle.resume.domain;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.BaseDomain;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeAggregate extends BaseDomain {

    private User user;
    private Resume resume;
    private Education education;

    @Builder
    public ResumeAggregate(User user, Resume resume, Education education) {
        this.user = user;
        this.resume = resume;
        this.education = education;
    }

    public Map<String,Integer> calculateWorkHours() {

        LanguageSkill languageSkill = resume.getLanguageSkill();
        Integer topicLevel = languageSkill.getTopikLevel();
        Integer socialIntegrationLevel = languageSkill.getSocialIntegrationLevel();
        Integer sejongInstituteLevel = languageSkill.getSejongInstituteLevel();
        int weekendWorkHour = 0;
        int weekdayWorkHour = 0;

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();

        if(education != null && education.getGpa()<=2.0){
            stringIntegerHashMap.put("weekendWorkHours", weekendWorkHour);
            stringIntegerHashMap.put("weekdayWorkHours", weekdayWorkHour);

            return stringIntegerHashMap;
        }

        switch (this.user.getVisa()) {
            case D_2_1 -> {
                if (topicLevel >= 3 && socialIntegrationLevel >= 3 && sejongInstituteLevel >= 3) { // 한국어 능력기준 달성 시
                    if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                        weekendWorkHour = 168;
                        weekdayWorkHour = 30;
                    } else { // 성적우수자 혜택 미적용
                        weekendWorkHour = 168;
                        weekdayWorkHour = 25;
                    }
                } else { // 한국어 능력기준 미달성 시
                    weekendWorkHour = 10;
                    weekdayWorkHour = 10;
                }
            }
            case D_2_2 -> {
                if (education != null && education.getGrade() < 3) { // 학년이 3학년 미만일 시
                    if (topicLevel >= 3 && socialIntegrationLevel >= 3 && sejongInstituteLevel >= 3) { // 한국어 능력기준 달성 시
                        if (education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                            weekendWorkHour = 168;
                            weekdayWorkHour = 30;
                        } else { // 성적우수자 혜택 미적용
                            weekendWorkHour = 168;
                            weekdayWorkHour = 25;
                        }
                    } else { // 한국어 능력기준 미달성 시
                        weekendWorkHour = 10;
                        weekdayWorkHour = 10;
                    }
                } else { // 학년이 3학년 이상일 시
                    if (topicLevel >= 4 && socialIntegrationLevel >= 4 && sejongInstituteLevel >= 4) { // 한국어 능력기준 달성 시
                        if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                            weekendWorkHour = 168;
                            weekdayWorkHour = 30;
                        } else { // 성적우수자 혜택 미적용
                            weekendWorkHour = 168;
                            weekdayWorkHour = 25;
                        }
                    } else { // 한국어 능력기준 미달성 시
                        weekendWorkHour = 10;
                        weekdayWorkHour = 10;
                    }
                }
            }
            case D_2_3, D_2_4 -> {
                if (topicLevel >= 4 && socialIntegrationLevel >= 4 && sejongInstituteLevel >= 4) { // 한국어 능력기준 달성 시
                    if (education != null && education.getGpa() >= 3.5) { // 성적우수자 혜택(학점 3.5 이상)
                        weekendWorkHour = 168;
                        weekdayWorkHour = 35;
                    } else { // 성적우수자 혜택 미적용
                        weekendWorkHour = 168;
                        weekdayWorkHour = 30;
                    }
                } else { // 한국어 능력기준 미달성 시
                    weekendWorkHour = 15;
                    weekdayWorkHour = 15;
                }
            }
        }
        stringIntegerHashMap.put("weekendWorkHours", weekendWorkHour);
        stringIntegerHashMap.put("weekdayWorkHours", weekdayWorkHour);

        return stringIntegerHashMap;
    }
}
