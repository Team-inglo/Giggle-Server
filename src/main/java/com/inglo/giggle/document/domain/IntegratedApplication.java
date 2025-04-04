package com.inglo.giggle.document.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.school.domain.School;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
public class IntegratedApplication extends Document {
    @Value("${template.integrated-application.word.path}")
    private String wordTemplatePath;

    private String firstName;
    private String lastName;
    private LocalDate birth;
    private EGender gender;
    private String nationality;
    private String telePhoneNumber;
    private String cellPhoneNumber;
    private Boolean isAccredited;
    private String newWorkPlaceName;
    private String newWorkPlaceRegistrationNumber;
    private String newWorkPlacePhoneNumber;
    private Integer annualIncomeAmount;
    private String occupation;
    private String email;
    private String employeeSignatureBase64;
    private EEmployeeStatus employeeStatus;
    private Address employeeAddress;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    private Long schoolId;

    @Builder
    public IntegratedApplication(Long id, String wordUrl, Long userOwnerJobPostingId,
                                 String firstName, String lastName, LocalDate birth, EGender gender, String nationality,
                                 String telePhoneNumber, String cellPhoneNumber, Boolean isAccredited,
                                 String newWorkPlaceName, String newWorkPlaceRegistrationNumber, String newWorkPlacePhoneNumber,
                                 Integer annualIncomeAmount, String occupation, String email,
                                 String employeeSignatureBase64, EEmployeeStatus employeeStatus,
                                 Address employeeAddress, Long schoolId,
                                 LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        super(id, wordUrl, userOwnerJobPostingId, createdAt, updatedAt, deletedAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isAccredited = isAccredited;
        this.newWorkPlaceName = newWorkPlaceName;
        this.newWorkPlaceRegistrationNumber = newWorkPlaceRegistrationNumber;
        this.newWorkPlacePhoneNumber = newWorkPlacePhoneNumber;
        this.annualIncomeAmount = annualIncomeAmount;
        this.occupation = occupation;
        this.email = email;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeStatus = employeeStatus;
        this.employeeAddress = employeeAddress;
        this.schoolId = schoolId;
    }
    public void updateByUser(
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
            Address employeeAddress,
            Long schoolId
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isAccredited = isAccredited;
        this.newWorkPlaceName = newWorkPlaceName;
        this.newWorkPlaceRegistrationNumber = newWorkPlaceRegistrationNumber;
        this.newWorkPlacePhoneNumber = newWorkPlacePhoneNumber;
        this.annualIncomeAmount = annualIncomeAmount;
        this.occupation = occupation;
        this.email = email;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeAddress = employeeAddress;
        this.schoolId = schoolId;
    }

    public String getEmployeeFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void checkUpdateOrSubmitUserIntegratedApplicationValidation() {
        // 유학생이 TEMPORARY_SAVE 가 아니면 접근 거부
        if (!employeeStatus.equals(EEmployeeStatus.CONFIRMATION)){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

    public void updateEmployeeStatusConfirmation() {
        this.employeeStatus = EEmployeeStatus.CONFIRMATION;
    }

    public ByteArrayInputStream createIntegratedApplicationDocxFile(School school) {
        try (FileInputStream fis = new FileInputStream(wordTemplatePath);
             XWPFDocument template = new XWPFDocument(fis);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 텍스트 치환
            Map<String, String> variables = prepareVariables(school);
            replacePlaceholders(template, variables);

            // 서명 이미지 삽입
            String base64Svg = employeeSignatureBase64;
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

    private Map<String, String> prepareVariables(School school) {
        Map<String, String> variables = new HashMap<>();
        variables.put(Constants.SURNAME, lastName);
        variables.put(Constants.GIVEN_NAME, firstName);
        variables.put(Constants.BIRTHYEAR, String.valueOf(birth.getYear()));
        variables.put(Constants.BIRTHMONTH, String.valueOf(birth.getMonthValue()));
        variables.put(Constants.BIRTHDAY, String.valueOf(birth.getDayOfMonth()));
        if (gender.equals(EGender.MALE)) {
            variables.put(Constants.MALE, Constants.V);
            variables.put(Constants.FEMALE, Constants.BLANK);
        } else {
            variables.put(Constants.MALE, Constants.BLANK);
            variables.put(Constants.FEMALE, Constants.V);
        }
        variables.put(Constants.NATIONALITY, nationality);
        variables.put(Constants.ADDR, employeeAddress.getFullAddress());
        variables.put(Constants.TELENUM, telePhoneNumber);
        variables.put(Constants.CELLNUM, cellPhoneNumber);
        variables.put(Constants.SCHOOLNAME, school.getSchoolName());
        variables.put(Constants.SCHOOLPHONE, school.getSchoolPhoneNumber());
        if (isAccredited) {
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
        variables.put(Constants.WORKPLACE, newWorkPlaceName);
        variables.put(Constants.REGNUM, newWorkPlaceRegistrationNumber);
        variables.put(Constants.WORKNUM, newWorkPlacePhoneNumber);
        variables.put(Constants.ANNUAL, annualIncomeAmount.toString());
        variables.put(Constants.OCCUPATION, occupation);
        variables.put(Constants.EMAIL, email);
        variables.put(Constants.NAME, lastName + firstName);
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

