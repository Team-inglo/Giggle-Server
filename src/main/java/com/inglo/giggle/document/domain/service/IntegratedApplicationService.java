package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.school.domain.School;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class IntegratedApplicationService {
    @Value("${template.integrated-application.word.path}")
    private String wordTemplatePath;

    public void updateEmployeeStatusConfirmation(IntegratedApplication document) {
        document.updateEmployeeStatus(EEmployeeStatus.CONFIRMATION);
    }

    public IntegratedApplication createIntegratedApplication(
            UserOwnerJobPosting userOwnerJobPosting,
            Address employeeAddress,
            String firstName,
            String lastName,
            LocalDate birth,
            EGender gender,
            String nationality,
            String telePhoneNumber,
            String cellPhoneNumber,
            Boolean isAccredited,
            String newWorkPlaceName,
            String newWorkPlaceRegistrationNumber,
            String newWorkPlacePhoneNumber,
            Integer annualIncomeAmount,
            String occupation,
            String email,
            String employeeSignatureBase64,
            School school
    ){
        return IntegratedApplication.builder()
                .userOwnerJobPosting(userOwnerJobPosting)
                .employeeAddress(employeeAddress)
                .firstName(firstName)
                .lastName(lastName)
                .birth(birth)
                .gender(gender)
                .nationality(nationality)
                .telePhoneNumber(telePhoneNumber)
                .cellPhoneNumber(cellPhoneNumber)
                .isAccredited(isAccredited)
                .newWorkPlaceName(newWorkPlaceName)
                .newWorkPlaceRegistrationNumber(newWorkPlaceRegistrationNumber)
                .newWorkPlacePhoneNumber(newWorkPlacePhoneNumber)
                .annualIncomeAmount(annualIncomeAmount)
                .occupation(occupation)
                .email(email)
                .employeeSignatureBase64(employeeSignatureBase64)
                .employeeStatus(EEmployeeStatus.TEMPORARY_SAVE)
                .school(school)
                .build();
    }

    public IntegratedApplication updateUserIntegratedApplication(
            IntegratedApplication document,
            String firstName,
            String lastName,
            LocalDate birth,
            EGender gender,
            String nationality,
            String telePhoneNumber,
            String cellPhoneNumber,
            Boolean isAccredited,
            String newWorkPlaceName,
            String newWorkPlaceRegistrationNumber,
            String newWorkPlacePhoneNumber,
            Integer annualIncomeAmount,
            String occupation,
            String email,
            String employeeSignatureBase64,
            School school,
            Address employeeAddress
    ) {
        document.updateFirstName(firstName);
        document.updateLastName(lastName);
        document.updateBirth(birth);
        document.updateGender(gender);
        document.updateNationality(nationality);
        document.updateTelePhoneNumber(telePhoneNumber);
        document.updateCellPhoneNumber(cellPhoneNumber);
        document.updateIsAccredited(isAccredited);
        document.updateNewWorkPlaceName(newWorkPlaceName);
        document.updateNewWorkPlaceRegistrationNumber(newWorkPlaceRegistrationNumber);
        document.updateNewWorkPlacePhoneNumber(newWorkPlacePhoneNumber);
        document.updateAnnualIncomeAmount(annualIncomeAmount);
        document.updateOccupation(occupation);
        document.updateEmail(email);
        document.updateEmployeeSignatureBase64(employeeSignatureBase64);
        document.updateSchool(school);
        document.updateEmployeeAddress(employeeAddress);

        return document;
    }

    public void checkUpdateOrSubmitUserIntegratedApplicationValidation(IntegratedApplication document) {

        // 유학생이 TEMPORARY_SAVE 가 아니면 접근 거부
        if (!document.getEmployeeStatus().equals(EEmployeeStatus.CONFIRMATION)){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

    public ByteArrayInputStream createIntegratedApplicationDocxFile(IntegratedApplication document) {
        try (FileInputStream fis = new FileInputStream(wordTemplatePath);
             XWPFDocument template = new XWPFDocument(fis);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 텍스트 치환
            Map<String, String> variables = prepareVariables(document);
            replacePlaceholders(template, variables);

            // 서명 이미지 삽입
            String base64Svg = document.getEmployeeSignatureBase64();
            if (base64Svg != null && !base64Svg.isEmpty()) {
                byte[] pngBytes = convertSvgToPng(base64Svg);
                if (pngBytes != null) {
                    // 서명 텍스트 위치를 찾음
                    XWPFParagraph signatureParagraph = findPlaceholderParagraph(template, Constants.SIGNATURE1);

                    if (signatureParagraph != null) {
                        // 이미지를 Anchor로 삽입
                        String blipId = addImageToDocument(template, pngBytes);
                        insertAnchorImage(signatureParagraph, blipId, Units.toEMU(100), Units.toEMU(15));
                        // 3. 텍스트만 삭제하고 이미지는 유지
                        List<XWPFRun> runs = signatureParagraph.getRuns();
                        if (runs != null && !runs.isEmpty()) {
                            for (XWPFRun run : runs) {
                                String text = run.getText(0);
                                if (text != null) {
                                    // 텍스트만 제거
                                    run.setText("", 0);
                                }
                            }
                        }
                    } else {
                        log.warn("Signature placeholder not found in the document.");
                    }
                }
                if (pngBytes != null) {
                    // 첫 서명이 들어갈 텍스트 위치를 찾음
                    XWPFParagraph signatureParagraph = findPlaceholderParagraph(template, Constants.SIGNATURE2);

                    if (signatureParagraph != null) {
                        // 이미지를 Anchor로 삽입
                        String blipId = addImageToDocument(template, pngBytes);
                        insertAnchorImage(signatureParagraph, blipId, Units.toEMU(100), Units.toEMU(15));
                        // 텍스트만 삭제하고 이미지는 유지
                        List<XWPFRun> runs = signatureParagraph.getRuns();
                        if (runs != null && !runs.isEmpty()) {
                            for (XWPFRun run : runs) {
                                String text = run.getText(0);
                                if (text != null && text.equals(Constants.SIGNATURE2)) {
                                    // 텍스트만 제거
                                    run.setText("", 0);
                                }
                            }
                        }
                    } else {
                        log.warn("Signature placeholder not found in the document.");
                    }
                }

            }

            // 결과 파일 저장
            template.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception e) {
            log.error("Error creating IntegratedApplication docx file: ", e);
            return null;
        }
    }

    private Map<String, String> prepareVariables(IntegratedApplication document) {
        Map<String, String> variables = new HashMap<>();
        variables.put(Constants.SURNAME, document.getLastName());
        variables.put(Constants.GIVEN_NAME, document.getFirstName());
        variables.put(Constants.BIRTHYEAR, String.valueOf(document.getBirth().getYear()));
        variables.put(Constants.BIRTHMONTH, String.valueOf(document.getBirth().getMonthValue()));
        variables.put(Constants.BIRTHDAY, String.valueOf(document.getBirth().getDayOfMonth()));
        if (document.getGender().equals(EGender.MALE)) {
            variables.put(Constants.MALE, Constants.V);
            variables.put(Constants.FEMALE, Constants.BLANK);
        } else {
            variables.put(Constants.MALE, Constants.BLANK);
            variables.put(Constants.FEMALE, Constants.V);
        }
        variables.put(Constants.NATIONALITY, document.getNationality());
        variables.put(Constants.ADDR, document.getEmployeeAddress().getFullAddress());
        variables.put(Constants.TELENUM, document.getTelePhoneNumber());
        variables.put(Constants.CELLNUM, document.getCellPhoneNumber());
        variables.put(Constants.SCHOOLNAME, document.getSchool().getSchoolName());
        variables.put(Constants.SCHOOLPHONE, document.getSchool().getSchoolPhoneNumber());
        if (document.getIsAccredited()) {
            variables.put(Constants.KYESACC, Constants.V);
            variables.put(Constants.YESACC, Constants.V);
            variables.put(Constants.KNOACC, Constants.BLANK);
            variables.put(Constants.NOACC, Constants.BLANK);
        } else {
            variables.put(Constants.KYESACC, Constants.BLANK);
            variables.put(Constants.YESACC, Constants.BLANK);
            variables.put(Constants.KNOACC, Constants.V);
            variables.put(Constants.NOACC, Constants.V);
        }
        variables.put(Constants.WORKPLACE, document.getNewWorkPlaceName());
        variables.put(Constants.REGNUM, document.getNewWorkPlaceRegistrationNumber());
        variables.put(Constants.WORKNUM, document.getNewWorkPlacePhoneNumber());
        variables.put(Constants.ANNUAL, document.getAnnualIncomeAmount().toString());
        variables.put(Constants.OCCUPATION, document.getOccupation());
        variables.put(Constants.EMAIL, document.getEmail());
        variables.put(Constants.NAME, document.getLastName() + document.getFirstName());
        return variables;
    }

    private void replacePlaceholders(XWPFDocument document, Map<String, String> variables) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replaceTextInParagraph(paragraph, variables);
        }
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceTextInParagraph(paragraph, variables);
                    }
                }
            }
        }
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, Map<String, String> variables) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs != null && !runs.isEmpty()) {
            StringBuilder paragraphText = new StringBuilder();

            // 1. 모든 Run의 텍스트 병합
            for (XWPFRun run : runs) {
                paragraphText.append(run.text());
            }

            // 2. 병합된 텍스트에서 플레이스홀더 치환
            String updatedText = paragraphText.toString();
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                updatedText = updatedText.replace("${" + entry.getKey() + "}", entry.getValue());
            }

            // 3. 기존 Run 제거 및 치환된 텍스트 삽입
            if (!updatedText.contentEquals(paragraphText)) {
                for (int i = runs.size() - 1; i >= 0; i--) {
                    paragraph.removeRun(i);
                }

                // 새로운 Run에 치환된 텍스트 추가
                XWPFRun newRun = paragraph.createRun();
                newRun.setText(updatedText);
            }
        }
    }

    private XWPFParagraph findPlaceholderParagraph(XWPFDocument document, String placeholder) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (paragraph.getText().contains(placeholder)) {
                return paragraph;
            }
        }
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        if (paragraph.getText().contains(placeholder)) {
                            return paragraph;
                        }
                    }
                }
            }
        }
        return null; // Placeholder를 찾을 수 없는 경우
    }

    private String addImageToDocument(XWPFDocument document, byte[] imageBytes) throws IOException, InvalidFormatException {
        int pictureType = XWPFDocument.PICTURE_TYPE_PNG;
        return document.addPictureData(imageBytes, pictureType);
    }

    private void insertAnchorImage(XWPFParagraph paragraph, String blipId, int widthEMU, int heightEMU) {
        try {
            long offsetY = -Units.toEMU(3);
            long offsetX = Units.toEMU(3);
            String anchorXml =
                    "<wp:anchor distT=\"0\" distB=\"0\" distL=\"0\" distR=\"0\" simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"false\" " +
                            "locked=\"false\" layoutInCell=\"true\" allowOverlap=\"true\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\">" +
                            "   <wp:simplePos x=\"0\" y=\"0\"/>" +
                            "   <wp:positionH relativeFrom=\"margin\">" +
                            "       <wp:align>right</wp:align>" +
                            "   </wp:positionH>" +
                            "   <wp:positionV relativeFrom=\"paragraph\">" +
                            "       <wp:align>top</wp:align>" +
                            "   </wp:positionV>" +
                            "   <wp:extent cx=\"" + widthEMU + "\" cy=\"" + heightEMU + "\"/>" +
                            "   <wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>" +
                            "   <wp:wrapNone/>" +
                            "   <wp:docPr id=\"1\" name=\"Picture 1\"/>" +
                            "   <wp:cNvGraphicFramePr/>" +
                            "   <a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                            "       <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                            "           <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                            "               <pic:nvPicPr>" +
                            "                   <pic:cNvPr id=\"0\" name=\"Signature\"/>" +
                            "                   <pic:cNvPicPr/>" +
                            "               </pic:nvPicPr>" +
                            "               <pic:blipFill>" +
                            "                   <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                            "                   <a:stretch>" +
                            "                       <a:fillRect/>" +
                            "                   </a:stretch>" +
                            "               </pic:blipFill>" +
                            "               <pic:spPr>" +
                            "                   <a:xfrm>" +
                            "                       <a:off x=\"" + offsetX + "\" y=\"" + offsetY + "\"/>" +
                            "                       <a:ext cx=\"" + widthEMU + "\" cy=\"" + heightEMU + "\"/>" +
                            "                   </a:xfrm>" +
                            "                   <a:prstGeom prst=\"rect\">" +
                            "                       <a:avLst/>" +
                            "                   </a:prstGeom>" +
                            "               </pic:spPr>" +
                            "           </pic:pic>" +
                            "       </a:graphicData>" +
                            "   </a:graphic>" +
                            "</wp:anchor>";

            // Anchor XML을 CTDrawing에 추가
            CTDrawing drawing = paragraph.createRun().getCTR().addNewDrawing();
            XmlToken xmlToken = XmlToken.Factory.parse(anchorXml);
            drawing.set(xmlToken);
        } catch (XmlException e) {
            throw new RuntimeException("Error creating anchor XML", e);
        }
    }

    private byte[] convertSvgToPng(String base64Svg) {
        try {
            byte[] svgBytes = Base64.getDecoder().decode(base64Svg);
            Path tempSvgFile = Files.createTempFile("signature", ".svg");
            Files.write(tempSvgFile, svgBytes, StandardOpenOption.WRITE);

            // SVG to PNG 변환
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            PNGTranscoder transcoder = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(tempSvgFile.toUri().toString());
            TranscoderOutput output = new TranscoderOutput(pngOutputStream);
            transcoder.transcode(input, output);

            Files.delete(tempSvgFile); // 임시 파일 삭제
            return pngOutputStream.toByteArray();

        } catch (Exception e) {
            log.error("Error converting SVG to PNG: ", e);
            return null;
        }
    }
}
