package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import jakarta.persistence.DiscriminatorValue;
import jakarta.xml.bind.JAXBElement;
import lombok.Builder;
import lombok.Getter;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.docx4j.dml.CTPoint2D;
import org.docx4j.dml.wordprocessingDrawing.Anchor;
import org.docx4j.dml.wordprocessingDrawing.CTPosH;
import org.docx4j.dml.wordprocessingDrawing.CTPosV;
import org.docx4j.dml.wordprocessingDrawing.CTWrapNone;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.dml.wordprocessingDrawing.STAlignH;
import org.docx4j.dml.wordprocessingDrawing.STAlignV;
import org.docx4j.dml.wordprocessingDrawing.STRelFromH;
import org.docx4j.dml.wordprocessingDrawing.STRelFromV;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@DiscriminatorValue("STANDARD_LABOR_CONTRACT")
public class StandardLaborContract extends Document {

    @Value("${template.standard-labor-contract.word.path}")
    private String wordTemplatePath;

    private String employeeFirstName;
    private String employeeLastName;
    private String employeePhoneNumber;
    private String employeeSignatureBase64;
    private EEmployeeStatus employeeStatus;
    private Address employeeAddress;

    private String companyName;
    private String companyRegistrationNumber;
    private String employerName;
    private String employerPhoneNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address employerAddress;
    private String description;

    private Set<EDayOfWeek> weeklyRestDays;
    private Integer hourlyRate;
    private Integer bonus;
    private Integer additionalSalary;
    private Double wageRate;
    private Integer paymentDay;
    private EPaymentMethod paymentMethod;
    private Set<EInsurance> insurances;
    private String employerSignatureBase64;
    private EEmployerStatus employerStatus;

    private List<ContractWorkDayTime> workDayTimes;

    @Builder
    public StandardLaborContract(Long id, String wordUrl, Long userOwnerJobPostingId, List<Reject> rejects,
                                 String employeeFirstName, String employeeLastName, String employeePhoneNumber,
                                 String employeeSignatureBase64, EEmployeeStatus employeeStatus, Address employeeAddress,
                                 String companyName, String companyRegistrationNumber, String employerName, String employerPhoneNumber,
                                 LocalDate startDate, LocalDate endDate, Address employerAddress, String description,
                                 Set<EDayOfWeek> weeklyRestDays, Integer hourlyRate, Integer bonus, Integer additionalSalary,
                                 Double wageRate, Integer paymentDay, EPaymentMethod paymentMethod, Set<EInsurance> insurances,
                                 String employerSignatureBase64, EEmployerStatus employerStatus, List<ContractWorkDayTime> workDayTimes,
                                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        super(id, wordUrl, userOwnerJobPostingId, rejects, createdAt, updatedAt, deletedAt);
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeStatus = employeeStatus;
        this.employeeAddress = employeeAddress;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.employerName = employerName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employerAddress = employerAddress;
        this.description = description;
        this.weeklyRestDays = weeklyRestDays;
        this.hourlyRate = hourlyRate;
        this.bonus = bonus;
        this.additionalSalary = additionalSalary;
        this.wageRate = wageRate;
        this.paymentDay = paymentDay;
        this.paymentMethod = paymentMethod;
        this.insurances = insurances;
        this.employerSignatureBase64 = employerSignatureBase64;
        this.employerStatus = employerStatus;
        this.workDayTimes = workDayTimes;
    }

    public String getEmployeeFullName() {
        return this.employeeFirstName + " " + this.employeeLastName;
    }

    public String getRestDays() {
        StringBuilder restDays = new StringBuilder();

        List<EDayOfWeek> sortedRestDays = new ArrayList<>(this.weeklyRestDays);
        sortedRestDays.sort(Comparator.comparing(EDayOfWeek::getOrder));

        for (EDayOfWeek day : sortedRestDays) {
            restDays.append(day.getKrName()).append(", ");
        }
        restDays.delete(restDays.length() - 2, restDays.length());
        return restDays.toString();
    }

    public void updateByOwner(
            String companyName,
            String companyRegistrationNumber,
            String phoneNumber,
            String employerName,
            LocalDate startDate,
            LocalDate endDate,
            Address employerAddressEntity,
            String description,
            Set<EDayOfWeek> weeklyRestDays,
            Integer hourlyRate,
            Integer bonus,
            Integer additionalSalary,
            Double wageRate,
            Integer paymentDay,
            EPaymentMethod paymentMethod,
            Set<EInsurance> insurance,
            String employerSignatureBase64
    ) {
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.employerPhoneNumber = phoneNumber;
        this.employerName = employerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employerAddress = employerAddressEntity;
        this.description = description;
        this.weeklyRestDays = weeklyRestDays;
        this.hourlyRate = hourlyRate;
        this.bonus = bonus;
        this.additionalSalary = additionalSalary;
        this.wageRate = wageRate;
        this.paymentDay = paymentDay;
        this.paymentMethod = paymentMethod;
        this.insurances = insurance;
        this.employerSignatureBase64 = employerSignatureBase64;
    }

    public void updateByUser(
            String employeeFirstName,
            String employeeLastName,
            Address employeeAddress,
            String employeePhoneNumber,
            String employeeSignatureBase64
    ) {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeAddress = employeeAddress;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeSignatureBase64 = employeeSignatureBase64;
    }

    public void checkUpdateOrSubmitOwnerStandardLaborContractValidation() {
        if (!((employerStatus.equals(EEmployerStatus.TEMPORARY_SAVE) && employeeStatus.equals(EEmployeeStatus.SUBMITTED)) ||
                (employerStatus.equals(EEmployerStatus.REWRITING) && employeeStatus.equals(EEmployeeStatus.REQUEST))))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkRequestStandardLaborContractValidation() {
        // 유학생이 BEFORE_CONFIRMATION 이면서 고용주가 SUBMITTED 인 경우를 제외하고 접근 거부
        if (!(employeeStatus.equals(EEmployeeStatus.BEFORE_CONFIRMATION) && employerStatus.equals(EEmployerStatus.SUBMITTED)))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkUpdateOrSubmitUserStandardLaborContractValidation() {
        // 유학생이 TEMPORARY_SAVE 가 아니거나 고용주가 not null 이면 접근 거부
        if ((!employeeStatus.equals(EEmployeeStatus.TEMPORARY_SAVE)) || !(employerStatus == null))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void updateStatusByOwnerSubmission() {
        this.employeeStatus = EEmployeeStatus.BEFORE_CONFIRMATION;
        this.employerStatus = EEmployerStatus.SUBMITTED;
    }

    public void updateStatusByRequest() {
        this.employeeStatus = EEmployeeStatus.REQUEST;
        this.employerStatus = EEmployerStatus.REWRITING;
    }

    public void updateStatusByUserSubmission() {
        this.employeeStatus = EEmployeeStatus.SUBMITTED;
        this.employerStatus = EEmployerStatus.TEMPORARY_SAVE;
    }

    public void updateStatusByConfirmation() {
        this.employeeStatus = EEmployeeStatus.CONFIRMATION;
        this.employerStatus = EEmployerStatus.CONFIRMATION;
    }

    public void deleteAllContractWorkDayTime() {
        this.workDayTimes.clear();
    }

    public ByteArrayInputStream createStandardLaborContractDocxFile() {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(wordTemplatePath));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            VariablePrepare.prepare(wordMLPackage);

            // 텍스트 필드 삽입
            Map<String, String> variables = new HashMap<>();
            variables.put(Constants.EMPLOYER_NAME1, employerName);
            variables.put(Constants.EMPLOYEE_NAME1, getEmployeeFullName());
            variables.put(Constants.START_YEAR, String.valueOf(startDate.getYear()));
            variables.put(Constants.START_MONTH, String.valueOf(startDate.getMonthValue()));
            variables.put(Constants.START_DAY, String.valueOf(startDate.getDayOfMonth()));
            variables.put(Constants.ADDRESS, employerAddress.getFullAddress());
            variables.put(Constants.DESCRIPTION, description);
            this.workDayTimes.sort(Comparator.comparing(
                    dayTime -> dayTime.getDayOfWeek().getOrder()
            ));
            int dayCount = Math.min(this.workDayTimes.size(), 6);  // 최대 6일만 처리

            for (int i = 0; i < dayCount; i++) {
                ContractWorkDayTime dayTime = this.workDayTimes.get(i);
                String dayKey = (i + 1) + Constants.DAY_PREFIX;
                String startHourKey = (i + 1) + Constants.START_HOUR_PREFIX;
                String startMinuteKey = (i + 1) + Constants.START_MINUTE_PREFIX;
                String endHourKey = (i + 1) + Constants.END_HOUR_PREFIX;
                String endMinuteKey = (i + 1) + Constants.END_MINUTE_PREFIX;
                String timeKey = (i + 1) + Constants.TIME_PREFIX;
                String startRestHourKey = (i + 1) + Constants.START_REST_PREFIX;
                String startRestMinuteKey = (i + 1) + Constants.START_REST_MINUTE_PREFIX;
                String endRestHourKey = (i + 1) + Constants.END_REST_PREFIX;
                String endRestMinuteKey = (i + 1) + Constants.END_REST_MINUTE_PREFIX;

                variables.put(dayKey, dayTime.getDayOfWeek().getKrName());
                variables.put(startHourKey, String.valueOf(dayTime.getWorkStartTime().getHour()));
                variables.put(startMinuteKey, String.valueOf(dayTime.getWorkStartTime().getMinute()));
                variables.put(endHourKey, String.valueOf(dayTime.getWorkEndTime().getHour()));
                variables.put(endMinuteKey, String.valueOf(dayTime.getWorkEndTime().getMinute()));
                variables.put(startRestHourKey, String.valueOf(dayTime.getBreakStartTime().getHour()));
                variables.put(startRestMinuteKey, String.valueOf(dayTime.getBreakStartTime().getMinute()));
                variables.put(endRestHourKey, String.valueOf(dayTime.getBreakEndTime().getHour()));
                variables.put(endRestMinuteKey, String.valueOf(dayTime.getBreakEndTime().getMinute()));

                Duration duration = Duration.between(dayTime.getWorkStartTime(), dayTime.getWorkEndTime());
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                variables.put(timeKey, hours + "시간 " + minutes + "분");
            }

            for (int i= 0; i < 6 - dayCount; i++) {
                String dayKey = (dayCount + i + 1) + Constants.DAY_PREFIX;
                String startHourKey = (dayCount + i + 1) + Constants.START_HOUR_PREFIX;
                String startMinuteKey = (dayCount + i + 1) + Constants.START_MINUTE_PREFIX;
                String endHourKey = (dayCount + i + 1) + Constants.END_HOUR_PREFIX;
                String endMinuteKey = (dayCount + i + 1) + Constants.END_MINUTE_PREFIX;
                String timeKey = (dayCount + i + 1) + Constants.TIME_PREFIX;
                String startRestHourKey = (dayCount + i + 1) + Constants.START_REST_PREFIX;
                String startRestMinuteKey = (dayCount + i + 1) + Constants.START_REST_MINUTE_PREFIX;
                String endRestHourKey = (dayCount + i + 1) + Constants.END_REST_PREFIX;
                String endRestMinuteKey = (dayCount + i + 1) + Constants.END_REST_MINUTE_PREFIX;

                variables.put(dayKey, Constants.BLANK);
                variables.put(startHourKey, Constants.BLANK);
                variables.put(startMinuteKey, Constants.BLANK);
                variables.put(endHourKey, Constants.BLANK);
                variables.put(endMinuteKey, Constants.BLANK);
                variables.put(startRestHourKey, Constants.BLANK);
                variables.put(startRestMinuteKey, Constants.BLANK);
                variables.put(endRestHourKey, Constants.BLANK);
                variables.put(endRestMinuteKey, Constants.BLANK);
                variables.put(timeKey, Constants.BLANK);
            }

            variables.put(Constants.REST_DAY, getRestDays());
            variables.put(Constants.S_HOURLY_RATE, String.valueOf(hourlyRate));
            if (bonus != null) {
                variables.put(Constants.YES_BONUS, Constants.V);
                variables.put(Constants.BONUS, String.valueOf(bonus));
                variables.put(Constants.NO_BONUS, Constants.BLANK);
            } else {
                variables.put(Constants.YES_BONUS, Constants.BLANK);
                variables.put(Constants.BONUS, Constants.BLANK);
                variables.put(Constants.NO_BONUS, Constants.V);
            }
            if (additionalSalary != null) {
                variables.put(Constants.YES_ADD, String.valueOf(additionalSalary));
                variables.put(Constants.NO_ADD, Constants.BLANK);
            } else {
                variables.put(Constants.YES_ADD, Constants.BLANK);
                variables.put(Constants.NO_ADD, Constants.V);
            }
            variables.put(Constants.WAGE_RATE, String.valueOf(wageRate));
            variables.put(Constants.PAYMENT_DAY, String.valueOf(paymentDay));
            if (paymentMethod.equals(EPaymentMethod.DIRECT)) {
                variables.put(Constants.DIRECT_PAYMENT, Constants.V);
                variables.put(Constants.BANK_PAYMENT, Constants.BLANK);
            } else {
                variables.put(Constants.DIRECT_PAYMENT, Constants.BLANK);
                variables.put(Constants.BANK_PAYMENT, Constants.V);
            }
            if (insurances.contains(EInsurance.EMPLOYMENT_INSURANCE)) {
                variables.put(Constants.EMPLOYMENT_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.EMPLOYMENT_INSURANCE, Constants.NONECHECK);
            }
            if (insurances.contains(EInsurance.WORKERS_COMPENSATION_INSURANCE)) {
                variables.put(Constants.INDUSTRIAL_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.INDUSTRIAL_INSURANCE, Constants.NONECHECK);
            }
            if (insurances.contains(EInsurance.NATIONAL_PENSION)) {
                variables.put(Constants.NATIONAL_PENSION, Constants.CHECK);
            } else {
                variables.put(Constants.NATIONAL_PENSION, Constants.NONECHECK);
            }
            if (insurances.contains(EInsurance.HEALTH_INSURANCE)) {
                variables.put(Constants.HEALTH_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.HEALTH_INSURANCE, Constants.NONECHECK);
            }

            variables.put(Constants.COMPANY_NAME_1, companyName);
            variables.put(Constants.EMPLOYER_PHONE, employerPhoneNumber);
            variables.put(Constants.ADDRESS_2, employerAddress.getFullAddress());
            variables.put(Constants.EMPLOYER_NAME_2, employerName);
            variables.put(Constants.ADDRESS_3, employeeAddress.getFullAddress());
            variables.put(Constants.EMPLOYEE_PHONE, employeePhoneNumber);
            variables.put(Constants.EMPLOYEE_NAME_2, getEmployeeFullName());

            documentPart.variableReplace(variables);

            // Base64로 인코딩된 고용주 서명 이미지를 SVG 파일로 변환
            String employerBase64Svg = employerSignatureBase64;
            byte[] employerSvgBytes = Base64.getDecoder().decode(employerBase64Svg);
            Path tempEmployerSvgFile = Files.createTempFile("signature", ".svg");
            Files.write(tempEmployerSvgFile, employerSvgBytes, StandardOpenOption.WRITE);

            // SVG 파일을 PNG로 변환
            BufferedImage employerSignatureImage = convertSvgToPng(tempEmployerSvgFile.toFile());

            // Base64로 인코딩된 유학생 서명 이미지를 SVG 파일로 변환
            String employeeBase64Svg = employeeSignatureBase64;
            byte[] employeeSvgBytes = Base64.getDecoder().decode(employeeBase64Svg);
            Path tempEmployeeSvgFile = Files.createTempFile("Employee signature", ".svg");
            Files.write(tempEmployeeSvgFile, employeeSvgBytes, StandardOpenOption.WRITE);

            // SVG 파일을 PNG로 변환
            BufferedImage employeeSignatureImage = convertSvgToPng(tempEmployeeSvgFile.toFile());

            if (employeeSignatureImage != null && employerSignatureImage != null) {
                byte[] employerImageBytes = bufferedImageToByteArray(employerSignatureImage);
                assert employerImageBytes != null;
                BinaryPartAbstractImage employerImagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, employerImageBytes);
                // Inline 이미지 생성
                Inline employerInlineImage = employerImagePart.createImageInline("Signature", "Employer Signature", 1,2, 1000000L, 300000L, false);
                Anchor employerAnchor = new Anchor();

                // Inline의 extent 속성 값을 Anchor에 설정
                employerAnchor.setExtent(employerInlineImage.getExtent());

                // effectExtent가 Inline과 Anchor에 모두 정의되어 있으면 설정
                if (employerInlineImage.getEffectExtent() != null) {
                    employerAnchor.setEffectExtent(employerInlineImage.getEffectExtent());
                }

                // Inline의 docPr 속성 값을 Anchor에 설정
                employerAnchor.setDocPr(employerInlineImage.getDocPr());

                // Inline의 cNvGraphicFramePr 속성 값을 Anchor에 설정
                if (employerInlineImage.getCNvGraphicFramePr() != null) {
                    employerAnchor.setCNvGraphicFramePr(employerInlineImage.getCNvGraphicFramePr());
                }

                // Inline의 graphic 속성 값을 Anchor에 설정
                employerAnchor.setGraphic(employerInlineImage.getGraphic());

                // Inline의 거리 설정 속성(distT, distB, distL, distR)을 Anchor에 설정
                if (employerInlineImage.getDistT() != null) {
                    employerAnchor.setDistT(employerInlineImage.getDistT());
                }
                if (employerInlineImage.getDistB() != null) {
                    employerAnchor.setDistB(employerInlineImage.getDistB());
                }
                if (employerInlineImage.getDistL() != null) {
                    employerAnchor.setDistL(employerInlineImage.getDistL());
                }
                if (employerInlineImage.getDistR() != null) {
                    employerAnchor.setDistR(employerInlineImage.getDistR());
                }

                // Anchor의 고유 속성 값 설정
                employerAnchor.setRelativeHeight(0);       // 기본 높이
                employerAnchor.setBehindDoc(false);         // 문서 뒤 배치 여부
                employerAnchor.setLocked(false);            // 잠금 여부
                employerAnchor.setLayoutInCell(true);       // 셀 내 레이아웃 여부
                employerAnchor.setAllowOverlap(true);       // 겹침 허용 여부

                // 랩 설정 제거
                employerAnchor.setWrapNone(null);
                employerAnchor.setWrapSquare(null);
                employerAnchor.setWrapTight(null);
                employerAnchor.setWrapThrough(null);
                employerAnchor.setWrapTopAndBottom(null);

                // simplePos 속성 설정
                CTPoint2D employerSimplePos = new CTPoint2D();
                employerSimplePos.setX(0L);
                employerSimplePos.setY(0L);
                employerAnchor.setSimplePos(employerSimplePos);
                employerAnchor.setSimplePosAttr(false); // Word 호환성을 위해 simplePos 속성 false로 설정

                // 수평 위치 설정
                CTPosH employerPositionH = new CTPosH();
                employerPositionH.setAlign(STAlignH.LEFT); // 수평 위치: 왼쪽 정렬
                employerPositionH.setRelativeFrom(STRelFromH.MARGIN); // 기준: 페이지 여백
                employerAnchor.setPositionH(employerPositionH);

                // 수직 위치 설정
                CTPosV employerPositionV = new CTPosV();
                employerPositionV.setAlign(STAlignV.TOP); // 수직 위치: 상단 정렬
                employerPositionV.setRelativeFrom(STRelFromV.MARGIN); // 기준: 페이지 여백
                employerAnchor.setPositionV(employerPositionV);

                employerAnchor.setWrapNone(new CTWrapNone()); // 기본 랩 설정 (없음)

                byte[] employeeImageBytes = bufferedImageToByteArray(employeeSignatureImage);
                assert employeeImageBytes != null;
                BinaryPartAbstractImage employeeImagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, employeeImageBytes);
                // Inline 이미지 생성
                Inline employeeInlineImage = employeeImagePart.createImageInline("Signature", "Employee Signature", 3, 4, 1000000L, 300000L, false);
                Anchor employeeAnchor = new Anchor();

                // Inline의 extent 속성 값을 Anchor에 설정
                employeeAnchor.setExtent(employeeInlineImage.getExtent());

                // effectExtent가 Inline과 Anchor에 모두 정의되어 있으면 설정
                if (employeeInlineImage.getEffectExtent() != null) {
                    employeeAnchor.setEffectExtent(employeeInlineImage.getEffectExtent());
                }

                // Inline의 docPr 속성 값을 Anchor에 설정
                employeeAnchor.setDocPr(employeeInlineImage.getDocPr());

                // Inline의 cNvGraphicFramePr 속성 값을 Anchor에 설정
                if (employeeInlineImage.getCNvGraphicFramePr() != null) {
                    employeeAnchor.setCNvGraphicFramePr(employeeInlineImage.getCNvGraphicFramePr());
                }

                // Inline의 graphic 속성 값을 Anchor에 설정
                employeeAnchor.setGraphic(employeeInlineImage.getGraphic());

                // Inline의 거리 설정 속성(distT, distB, distL, distR)을 Anchor에 설정
                if (employeeInlineImage.getDistT() != null) {
                    employeeAnchor.setDistT(employeeInlineImage.getDistT());
                }
                if (employeeInlineImage.getDistB() != null) {
                    employeeAnchor.setDistB(employeeInlineImage.getDistB());
                }
                if (employeeInlineImage.getDistL() != null) {
                    employeeAnchor.setDistL(employeeInlineImage.getDistL());
                }
                if (employeeInlineImage.getDistR() != null) {
                    employeeAnchor.setDistR(employeeInlineImage.getDistR());
                }

                // Anchor의 고유 속성 값 설정
                employeeAnchor.setRelativeHeight(0);       // 기본 높이
                employeeAnchor.setBehindDoc(false);         // 문서 뒤 배치 여부
                employeeAnchor.setLocked(false);            // 잠금 여부
                employeeAnchor.setLayoutInCell(true);       // 셀 내 레이아웃 여부
                employeeAnchor.setAllowOverlap(true);       // 겹침 허용 여부

                // 랩 설정 제거
                employeeAnchor.setWrapNone(null);
                employeeAnchor.setWrapSquare(null);
                employeeAnchor.setWrapTight(null);
                employeeAnchor.setWrapThrough(null);
                employeeAnchor.setWrapTopAndBottom(null);

                // simplePos 속성 설정
                CTPoint2D employeeSimplePos = new CTPoint2D();
                employeeSimplePos.setX(0L);
                employeeSimplePos.setY(0L);
                employeeAnchor.setSimplePos(employeeSimplePos);
                employeeAnchor.setSimplePosAttr(false); // Word 호환성을 위해 simplePos 속성 false로 설정

                // 수평 위치 설정
                CTPosH employeePositionH = new CTPosH();
                employeePositionH.setAlign(STAlignH.LEFT); // 수평 위치: 오른쪽 정렬
                employeePositionH.setRelativeFrom(STRelFromH.MARGIN); // 기준: 페이지 여백
                employeeAnchor.setPositionH(employeePositionH);

                // 수직 위치 설정
                CTPosV employeePositionV = new CTPosV();
                employeePositionV.setAlign(STAlignV.BOTTOM); // 수직 위치: 상단 정렬
                employeePositionV.setRelativeFrom(STRelFromV.MARGIN); // 기준: 페이지 여백
                employeeAnchor.setPositionV(employeePositionV);

                employeeAnchor.setWrapNone(new CTWrapNone()); // 기본 랩 설정 (없음)

                // '인 또는 서명' 위치에 이미지 삽입
                insertImageAtPlaceholder(documentPart, employerAnchor, employeeAnchor);
            }

            // 파일을 ByteArrayOutputStream에 저장
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wordMLPackage.save(outputStream);

            // ByteArrayInputStream으로 변환하여 반환
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception e) {
            return null;
        }
    }

    private BufferedImage convertSvgToPng(File svgFile) {
        try {
            PNGTranscoder transcoder = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(svgFile.toURI().toString());

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(pngOutputStream);
            transcoder.transcode(input, output);

            return ImageIO.read(new ByteArrayInputStream(pngOutputStream.toByteArray()));
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] bufferedImageToByteArray(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    private void insertImageAtPlaceholder(MainDocumentPart documentPart, Anchor employerAnchor, Anchor employeeAnchor) {
        try {
            String xpathExpression = "//w:p";
            List<Object> paragraphNodes = documentPart.getJAXBNodesViaXPath(xpathExpression, true);
            boolean employerInserted = false;
            boolean employeeInserted = false;

            for (Object paragraphNode : paragraphNodes) {
                if (paragraphNode instanceof P) {
                    P paragraph = (P) paragraphNode;
                    boolean foundPlaceholder = false;

                    // Placeholder를 찾기 위해 텍스트를 결합
                    for (Object content : paragraph.getContent()) {
                        StringBuilder combinedText = new StringBuilder();
                        if (content instanceof R run) {
                            for (Object runContent : run.getContent()) {
                                if (runContent instanceof JAXBElement<?> element) {
                                    if (element.getValue() instanceof Text textElement) {
                                        combinedText.append(textElement.getValue());
                                    }
                                }
                            }
                        }

                        // Placeholder 확인
                        if (combinedText.toString().contains("서명)")) {
                            foundPlaceholder = true;
                            break;
                        }
                    }

                    // Placeholder가 발견되면 Run 객체 생성 후 Drawing 추가
                    if (foundPlaceholder) {
                        R imageRun = new R();
                        Drawing drawing = new Drawing();
                        if (!employerInserted) {
                            drawing.getAnchorOrInline().add(employerAnchor);
                            employerInserted = true;
                            foundPlaceholder = false;
                        } else {
                            drawing.getAnchorOrInline().add(employeeAnchor);
                            employeeInserted = true;
                        }
                        // Drawing 객체를 새로 생성한 Run에 추가하고 문단에 추가
                        imageRun.getContent().add(drawing);
                        paragraph.getContent().add(imageRun);

                        if (employeeInserted) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

