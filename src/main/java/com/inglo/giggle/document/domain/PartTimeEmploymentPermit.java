package com.inglo.giggle.document.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
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
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
public class PartTimeEmploymentPermit extends Document {
    @Value("${template.part-time-employment-permit.word.path}")
    private String wordTemplatePath;

    private String employeeFirstName;
    private String employeeLastName;
    private String major;
    private Integer termOfCompletion;
    private String employeePhoneNumber;
    private String employeeEmail;
    private EEmployeeStatus employeeStatus;

    private String companyName;
    private String companyRegistrationNumber;
    private String jobType;
    private String employerName;
    private String employerPhoneNumber;
    private String employerSignatureBase64;
    private EWorkPeriod workPeriod;
    private Integer hourlyRate;
    private String workDaysWeekDays;
    private String workDaysWeekends;
    private EEmployerStatus employerStatus;
    private Address employerAddress;

    @Builder
    public PartTimeEmploymentPermit(Long id, String wordUrl, Long userOwnerJobPostingId,
                                    String employeeFirstName, String employeeLastName, String major, Integer termOfCompletion,
                                    String employeePhoneNumber, String employeeEmail, EEmployeeStatus employeeStatus,
                                    String companyName, String companyRegistrationNumber, String jobType,
                                    String employerName, String employerPhoneNumber, String employerSignatureBase64,
                                    EWorkPeriod workPeriod, Integer hourlyRate, String workDaysWeekDays, String workDaysWeekends,
                                    EEmployerStatus employerStatus, Address employerAddress,
                                    LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        super(id, wordUrl, userOwnerJobPostingId, createdAt, updatedAt, deletedAt);
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.major = major;
        this.termOfCompletion = termOfCompletion;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeEmail = employeeEmail;
        this.employeeStatus = employeeStatus;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.jobType = jobType;
        this.employerName = employerName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.employerSignatureBase64 = employerSignatureBase64;
        this.workPeriod = workPeriod;
        this.hourlyRate = hourlyRate;
        this.workDaysWeekDays = workDaysWeekDays;
        this.workDaysWeekends = workDaysWeekends;
        this.employerStatus = employerStatus;
        this.employerAddress = employerAddress;
    }

    public String getEmployeeFullName() {
        return this.employeeFirstName + " " + this.employeeLastName;
    }

    public void updateByOwner(
            String companyName,
            String companyRegistrationNumber,
            String jobType,
            String employerName,
            String employerPhoneNumber,
            String employerSignatureBase64,
            EWorkPeriod workPeriod,
            Integer hourlyRate,
            String workDaysWeekDays,
            String workDaysWeekends,
            Address employerAddressEntity
    ) {
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.jobType = jobType;
        this.employerName = employerName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.employerSignatureBase64 = employerSignatureBase64;
        this.workPeriod = workPeriod;
        this.hourlyRate = hourlyRate;
        this.workDaysWeekDays = workDaysWeekDays;
        this.workDaysWeekends = workDaysWeekends;
        this.employerAddress = employerAddressEntity;
    }

    public void updateByUser(
            String employeeFirstName,
            String employeeLastName,
            String major,
            Integer termOfCompletion,
            String employeePhoneNumber,
            String employeeEmail
    ) {
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.major = major;
        this.termOfCompletion = termOfCompletion;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeEmail = employeeEmail;
    }

    public void checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation() {
        if (!((employerStatus.equals(EEmployerStatus.TEMPORARY_SAVE) && employeeStatus.equals(EEmployeeStatus.SUBMITTED)) ||
                (employerStatus.equals(EEmployerStatus.REWRITING) && employeeStatus.equals(EEmployeeStatus.REQUEST))))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkRequestPartTimeEmploymentPermitValidation() {
        // 유학생이 BEFORE_CONFIRMATION 이면서 고용주가 SUBMITTED 인 경우를 제외하고 접근 거부
        if (!(employeeStatus.equals(EEmployeeStatus.BEFORE_CONFIRMATION) && employerStatus.equals(EEmployerStatus.SUBMITTED)))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation() {
        // 유학생이 TEMPORARY_SAVE 가 아니거나 고용주가 null 이 아니면 접근 거부
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

    public ByteArrayInputStream createPartTimeEmploymentPermitDocxFile() {
        try (FileInputStream fis = new FileInputStream(wordTemplatePath);
             XWPFDocument template = new XWPFDocument(fis);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 텍스트 치환
            Map<String, String> variables = prepareVariables();
            replacePlaceholders(template, variables);

            // 서명 이미지 삽입
            String base64Svg = employerSignatureBase64;
            if (base64Svg != null && !base64Svg.isEmpty()) {
                byte[] pngBytes = convertSvgToPng(base64Svg);
                if (pngBytes != null) {
                    // 1. 서명 텍스트 위치를 찾음
                    XWPFParagraph signatureParagraph = findPlaceholderParagraph(template, Constants.SIGNATURE);

                    if (signatureParagraph != null) {
                        // 2. 이미지를 Anchor로 삽입
                        String blipId = addImageToDocument(template, pngBytes);
                        insertAnchorImage(signatureParagraph, blipId, Units.toEMU(100), Units.toEMU(15));
                    } else {
                        log.warn("Signature placeholder not found in the document.");
                    }
                }
            }

            // 결과 파일 저장
            template.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (Exception e) {
            log.error("Error creating PartTimeEmploymentPermit docx file: ", e);
            return null;
        }
    }

    private Map<String, String> prepareVariables() {
        Map<String, String> variables = new HashMap<>();
        variables.put(Constants.KEMPLOYEE_FULL_NAME, getEmployeeFullName());
        variables.put(Constants.KMAJOR, major);
        variables.put(Constants.KTERM_OF_COMPLETION, termOfCompletion.toString());
        variables.put(Constants.KEMPLOYEE_PHONE_NUMBER, employeePhoneNumber);
        variables.put(Constants.KEMPLOYEE_EMAIL, employeeEmail);
        variables.put(Constants.KCOMPANY_NAME, companyName);
        variables.put(Constants.KCOMPANY_REGISTRATION_NUMBER, companyRegistrationNumber);
        variables.put(Constants.KJOB_TYPE, jobType);
        variables.put(Constants.KEMPLOYER_ADDRESS, employerAddress.getAddressName() + " " + employerAddress.getAddressDetail());
        variables.put(Constants.KEMPLOYER_NAME, employerName);
        variables.put(Constants.KEMPLOYER_PHONE_NUMBER, employerPhoneNumber);
        variables.put(Constants.KWORK_PERIOD, workPeriod.getKrName());
        variables.put(Constants.KHOURLY_RATE, hourlyRate.toString());
        variables.put(Constants.KWORK_DAYS_WEEKDAYS, workDaysWeekDays);
        variables.put(Constants.KWORK_DAYS_WEEKENDS, workDaysWeekends);
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
        String blipId = document.addPictureData(imageBytes, pictureType);
        return blipId;
    }

    private void insertAnchorImage(XWPFParagraph paragraph, String blipId, int widthEMU, int heightEMU) {
        try {

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
                            "                       <a:off x=\"" + offsetX + "\" y=\"0\"/>" +
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
