package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.xml.bind.JAXBElement;
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
import org.springframework.stereotype.Service;

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
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StandardLaborContractService {

    @Value("${template.standard-labor-contract.word.path}")
    private String wordTemplatePath;

    public StandardLaborContract updateStatusByUserSubmission(StandardLaborContract document) {
        document.updateEmployeeStatus(EEmployeeStatus.SUBMITTED);
        document.updateEmployerStatus(EEmployerStatus.TEMPORARY_SAVE);

        return document;
    }

    public StandardLaborContract updateStatusByOwnerSubmission(StandardLaborContract document) {
        document.updateEmployeeStatus(EEmployeeStatus.BEFORE_CONFIRMATION);
        document.updateEmployerStatus(EEmployerStatus.SUBMITTED);

        return document;
    }

    public StandardLaborContract updateStatusByRequest(StandardLaborContract document) {
        document.updateEmployeeStatus(EEmployeeStatus.REQUEST);
        document.updateEmployerStatus(EEmployerStatus.REWRITING);

        return document;
    }

    public StandardLaborContract updateStatusByConfirmation(StandardLaborContract document) {
        document.updateEmployeeStatus(EEmployeeStatus.CONFIRMATION);
        document.updateEmployerStatus(EEmployerStatus.CONFIRMATION);

        return document;
    }

    public StandardLaborContract createStandardLaborContract(
            UserOwnerJobPosting userOwnerJobPosting,
            String employeeFirstName,
            String employeeLastName,
            Address employeeAddress,
            String employeePhoneNumber,
            String employeeSignatureBase64
    ) {
        return StandardLaborContract.builder()
                .userOwnerJobPosting(userOwnerJobPosting)
                .employeeFirstName(employeeFirstName)
                .employeeLastName(employeeLastName)
                .employeeAddress(employeeAddress)
                .employeePhoneNumber(employeePhoneNumber)
                .employeeSignatureBase64(employeeSignatureBase64)
                .employeeStatus(EEmployeeStatus.TEMPORARY_SAVE)
                .build();
    }

    public StandardLaborContract updateUserStandardLaborContract(
            StandardLaborContract document,
            String employeeFirstName,
            String employeeLastName,
            Address employeeAddress,
            String employeePhoneNumber,
            String employeeSignatureBase64
    ) {
        document.updateEmployeeFirstName(employeeFirstName);
        document.updateEmployeeLastName(employeeLastName);
        document.updateEmployeeAddress(employeeAddress);
        document.updateEmployeePhoneNumber(employeePhoneNumber);
        document.updateEmployeeSignatureBase64(employeeSignatureBase64);
        return document;
    }

    public StandardLaborContract updateOwnerStandardLaborContract(
            StandardLaborContract document,
            String companyName,
            String companyRegistrationNumber,
            String phoneNumber,
            String employerName,
            LocalDate startDate,
            LocalDate endDate,
            Address employerAddress,
            String description,
            Set<EDayOfWeek> weeklyLastDays,
            Integer hourlyRate,
            Integer bonus,
            Integer additionalSalary,
            Double wageRate,
            Integer paymentDay,
            EPaymentMethod paymentMethod,
            Set<EInsurance> insurance,
            String employerSignatureBase64
    ) {
        document.updateCompanyName(companyName);
        document.updateCompanyRegistrationNumber(companyRegistrationNumber);
        document.updateEmployerPhoneNumber(phoneNumber);
        document.updateEmployerName(employerName);
        document.updateStartDate(startDate);
        document.updateEndDate(endDate);
        document.updateEmployerAddress(employerAddress);
        document.updateDescription(description);
        document.updateWeeklyRestDays(weeklyLastDays);
        document.updateHourlyRate(hourlyRate);
        document.updateBonus(bonus);
        document.updateAdditionalSalary(additionalSalary);
        document.updateWageRate(wageRate);
        document.updatePaymentDay(paymentDay);
        document.updatePaymentMethod(paymentMethod);
        document.updateInsurances(insurance);
        document.updateEmployerSignatureBase64(employerSignatureBase64);
        return document;
    }

    public void checkUpdateOrSubmitUserStandardLaborContractValidation(StandardLaborContract document) {

        // 유학생이 TEMPORARY_SAVE 가 아니거나 고용주가 not null 이면 접근 거부
        if ((!document.getEmployeeStatus().equals(EEmployeeStatus.TEMPORARY_SAVE)) || !(document.getEmployerStatus() == null))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkUpdateOrSubmitOwnerStandardLaborContractValidation(StandardLaborContract document) {

        // 고용주가 TEMPORARY_SAVE 이면서 유학생이 SUBMITTED 인 경우와, 고용주가 REWRITING 이면서 유학생이 REQUEST 인 경우를 제외하고 접근 거부
        if (!((document.getEmployerStatus().equals(EEmployerStatus.TEMPORARY_SAVE) && document.getEmployeeStatus().equals(EEmployeeStatus.SUBMITTED)) ||
                (document.getEmployerStatus().equals(EEmployerStatus.REWRITING) && document.getEmployeeStatus().equals(EEmployeeStatus.REQUEST))))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkRequestStandardLaborContractValidation(StandardLaborContract document) {

        // 유학생이 BEFORE_CONFIRMATION 이면서 고용주가 SUBMITTED 인 경우를 제외하고 접근 거부
        if (!(document.getEmployeeStatus().equals(EEmployeeStatus.BEFORE_CONFIRMATION) && document.getEmployerStatus().equals(EEmployerStatus.SUBMITTED)))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public ByteArrayInputStream createStandardLaborContractDocxFile(StandardLaborContract document) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(wordTemplatePath));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            VariablePrepare.prepare(wordMLPackage);

            // 텍스트 필드 삽입
            Map<String, String> variables = new HashMap<>();
            variables.put(Constants.EMPLOYER_NAME1, document.getEmployerName());
            variables.put(Constants.EMPLOYEE_NAME1, document.getEmployeeFullName());
            variables.put(Constants.START_YEAR, String.valueOf(document.getStartDate().getYear()));
            variables.put(Constants.START_MONTH, String.valueOf(document.getStartDate().getMonthValue()));
            variables.put(Constants.START_DAY, String.valueOf(document.getStartDate().getDayOfMonth()));
            variables.put(Constants.ADDRESS, document.getEmployerAddress().getFullAddress());
            variables.put(Constants.DESCRIPTION, document.getDescription());
            List<ContractWorkDayTime> workDayTime = document.getContractWorkDayTimes();
            workDayTime.sort(Comparator.comparing(
                    dayTime -> dayTime.getDayOfWeek().getOrder()
            ));
            int dayCount = Math.min(workDayTime.size(), 6);  // 최대 6일만 처리

            for (int i = 0; i < dayCount; i++) {
                ContractWorkDayTime dayTime = workDayTime.get(i);
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

            variables.put(Constants.REST_DAY, document.getRestDays());
            variables.put(Constants.S_HOURLY_RATE, String.valueOf(document.getHourlyRate()));
            if (document.getBonus() != null) {
                variables.put(Constants.YES_BONUS, Constants.V);
                variables.put(Constants.BONUS, String.valueOf(document.getBonus()));
                variables.put(Constants.NO_BONUS, Constants.BLANK);
            } else {
                variables.put(Constants.YES_BONUS, Constants.BLANK);
                variables.put(Constants.BONUS, Constants.BLANK);
                variables.put(Constants.NO_BONUS, Constants.V);
            }
            if (document.getAdditionalSalary() != null) {
                variables.put(Constants.YES_ADD, String.valueOf(document.getAdditionalSalary()));
                variables.put(Constants.NO_ADD, Constants.BLANK);
            } else {
                variables.put(Constants.YES_ADD, Constants.BLANK);
                variables.put(Constants.NO_ADD, Constants.V);
            }
            variables.put(Constants.WAGE_RATE, String.valueOf(document.getWageRate()));
            variables.put(Constants.PAYMENT_DAY, String.valueOf(document.getPaymentDay()));
            if (document.getPaymentMethod().equals(EPaymentMethod.DIRECT)) {
                variables.put(Constants.DIRECT_PAYMENT, Constants.V);
                variables.put(Constants.BANK_PAYMENT, Constants.BLANK);
            } else {
                variables.put(Constants.DIRECT_PAYMENT, Constants.BLANK);
                variables.put(Constants.BANK_PAYMENT, Constants.V);
            }
            if (document.getInsurances().contains(EInsurance.EMPLOYMENT_INSURANCE)) {
                variables.put(Constants.EMPLOYMENT_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.EMPLOYMENT_INSURANCE, Constants.NONECHECK);
            }
            if (document.getInsurances().contains(EInsurance.WORKERS_COMPENSATION_INSURANCE)) {
                variables.put(Constants.INDUSTRIAL_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.INDUSTRIAL_INSURANCE, Constants.NONECHECK);
            }
            if (document.getInsurances().contains(EInsurance.NATIONAL_PENSION)) {
                variables.put(Constants.NATIONAL_PENSION, Constants.CHECK);
            } else {
                variables.put(Constants.NATIONAL_PENSION, Constants.NONECHECK);
            }
            if (document.getInsurances().contains(EInsurance.HEALTH_INSURANCE)) {
                variables.put(Constants.HEALTH_INSURANCE, Constants.CHECK);
            } else {
                variables.put(Constants.HEALTH_INSURANCE, Constants.NONECHECK);
            }

            variables.put(Constants.COMPANY_NAME_1, document.getCompanyName());
            variables.put(Constants.EMPLOYER_PHONE, document.getEmployerPhoneNumber());
            variables.put(Constants.ADDRESS_2, document.getEmployerAddress().getFullAddress());
            variables.put(Constants.EMPLOYER_NAME_2, document.getEmployerName());
            variables.put(Constants.ADDRESS_3, document.getEmployeeAddress().getFullAddress());
            variables.put(Constants.EMPLOYEE_PHONE, document.getEmployeePhoneNumber());
            variables.put(Constants.EMPLOYEE_NAME_2, document.getEmployeeFullName());

            documentPart.variableReplace(variables);

            // Base64로 인코딩된 고용주 서명 이미지를 SVG 파일로 변환
            String employerBase64Svg = document.getEmployerSignatureBase64();
            byte[] employerSvgBytes = Base64.getDecoder().decode(employerBase64Svg);
            Path tempEmployerSvgFile = Files.createTempFile("signature", ".svg");
            Files.write(tempEmployerSvgFile, employerSvgBytes, StandardOpenOption.WRITE);

            // SVG 파일을 PNG로 변환
            BufferedImage employerSignatureImage = convertSvgToPng(tempEmployerSvgFile.toFile());

            // Base64로 인코딩된 유학생 서명 이미지를 SVG 파일로 변환
            String employeeBase64Svg = document.getEmployeeSignatureBase64();
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
