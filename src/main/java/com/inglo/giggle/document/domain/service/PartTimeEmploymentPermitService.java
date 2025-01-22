package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PartTimeEmploymentPermitService {

    @Value("${template.part-time-employment-permit.word.path}")
    private String wordTemplatePath;

    public PartTimeEmploymentPermit updateStatusByUserSubmission(PartTimeEmploymentPermit document) {
        document.updateEmployeeStatus(EEmployeeStatus.SUBMITTED);
        document.updateEmployerStatus(EEmployerStatus.TEMPORARY_SAVE);

        return document;
    }

    public PartTimeEmploymentPermit updateStatusByOwnerSubmission(PartTimeEmploymentPermit document) {
        document.updateEmployeeStatus(EEmployeeStatus.BEFORE_CONFIRMATION);
        document.updateEmployerStatus(EEmployerStatus.SUBMITTED);

        return document;
    }

    public PartTimeEmploymentPermit updateStatusByRequest(PartTimeEmploymentPermit document) {
        document.updateEmployeeStatus(EEmployeeStatus.REQUEST);
        document.updateEmployerStatus(EEmployerStatus.REWRITING);

        return document;
    }

    public PartTimeEmploymentPermit updateStatusByConfirmation(PartTimeEmploymentPermit document) {
        document.updateEmployeeStatus(EEmployeeStatus.CONFIRMATION);
        document.updateEmployerStatus(EEmployerStatus.CONFIRMATION);

        return document;
    }

    public PartTimeEmploymentPermit createPartTimeEmploymentPermit(
            UserOwnerJobPosting userOwnerJobPosting,
            String employeeFirstName,
            String employeeLastName,
            String major,
            Integer termOfCompletion,
            String employeePhoneNumber,
            String employeeEmail
    ) {
        return PartTimeEmploymentPermit.builder()
                .userOwnerJobPosting(userOwnerJobPosting)
                .employeeFirstName(employeeFirstName)
                .employeeLastName(employeeLastName)
                .major(major)
                .termOfCompletion(termOfCompletion)
                .employeePhoneNumber(employeePhoneNumber)
                .employeeEmail(employeeEmail)
                .employeeStatus(EEmployeeStatus.TEMPORARY_SAVE)
                .build();
    }

    public PartTimeEmploymentPermit updateUserPartTimeEmploymentPermit(
            PartTimeEmploymentPermit document,
            String employeeFirstName,
            String employeeLastName,
            String major,
            Integer termOfCompletion,
            String employeePhoneNumber,
            String employeeEmail
    ) {
        document.updateEmployeeFirstName(employeeFirstName);
        document.updateEmployeeLastName(employeeLastName);
        document.updateMajor(major);
        document.updateTermOfCompletion(termOfCompletion);
        document.updateEmployeePhoneNumber(employeePhoneNumber);
        document.updateEmployeeEmail(employeeEmail);

        return document;
    }

    public PartTimeEmploymentPermit updateOwnerPartTimeEmploymentPermit(
            PartTimeEmploymentPermit document,
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
            Address employerAddress
    )
    {
        document.updateCompanyName(companyName);
        document.updateCompanyRegistrationNumber(companyRegistrationNumber);
        document.updateJobType(jobType);
        document.updateEmployerName(employerName);
        document.updateEmployerPhoneNumber(employerPhoneNumber);
        document.updateEmployerSignatureBase64(employerSignatureBase64);
        document.updateWorkPeriod(workPeriod);
        document.updateHourlyRate(hourlyRate);
        document.updateWorkDaysWeekDays(workDaysWeekDays);
        document.updateWorkDaysWeekends(workDaysWeekends);
        document.updateEmployerAddress(employerAddress);

        return document;
    }

    public void checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation(PartTimeEmploymentPermit document) {

        // 유학생이 TEMPORARY_SAVE 가 아니거나 고용주가 null 이 아니면 접근 거부
        if ((!document.getEmployeeStatus().equals(EEmployeeStatus.TEMPORARY_SAVE)) || !(document.getEmployerStatus() == null))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkUpdateOrSubmitOwnerPartTimeEmploymentPermitValidation(PartTimeEmploymentPermit document) {

        // 고용주가 TEMPORARY_SAVE 이면서 유학생이 SUBMITTED 인 경우와, 고용주가 REWRITING 이면서 유학생이 REQUEST 인 경우를 제외하고 접근 거부
        if (!((document.getEmployerStatus().equals(EEmployerStatus.TEMPORARY_SAVE) && document.getEmployeeStatus().equals(EEmployeeStatus.SUBMITTED)) ||
                (document.getEmployerStatus().equals(EEmployerStatus.REWRITING) && document.getEmployeeStatus().equals(EEmployeeStatus.REQUEST))))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public void checkRequestPartTimeEmploymentPermitValidation(PartTimeEmploymentPermit document) {

        // 유학생이 BEFORE_CONFIRMATION 이면서 고용주가 SUBMITTED 인 경우를 제외하고 접근 거부
        if (!(document.getEmployeeStatus().equals(EEmployeeStatus.BEFORE_CONFIRMATION) && document.getEmployerStatus().equals(EEmployerStatus.SUBMITTED)))
            throw new CommonException(ErrorCode.ACCESS_DENIED);
    }

    public ByteArrayInputStream createPartTimeEmploymentPermitDocxFile(PartTimeEmploymentPermit document) {
        try (FileInputStream fis = new FileInputStream(wordTemplatePath);
             XWPFDocument template = new XWPFDocument(fis);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 텍스트 치환
            Map<String, String> variables = prepareVariables(document);
            replacePlaceholders(template, variables);

            // 서명 이미지 삽입
            String base64Svg = document.getEmployerSignatureBase64();
            if (base64Svg != null && !base64Svg.isEmpty()) {
                byte[] pngBytes = convertSvgToPng(base64Svg);
                if (pngBytes != null) {
                    // 1. "(인 또는 서명)" 텍스트 위치를 찾습니다.
                    XWPFParagraph signatureParagraph = findPlaceholderParagraph(template, "(인 또는 서명)");

                    if (signatureParagraph != null) {
                        // 2. 이미지를 Anchor로 삽입합니다.
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

    private Map<String, String> prepareVariables(PartTimeEmploymentPermit document) {
        Map<String, String> variables = new HashMap<>();
        variables.put(Constants.KEMPLOYEE_FULL_NAME, document.getEmployeeFullName());
        variables.put(Constants.KMAJOR, document.getMajor());
        variables.put(Constants.KTERM_OF_COMPLETION, document.getTermOfCompletion().toString());
        variables.put(Constants.KEMPLOYEE_PHONE_NUMBER, document.getEmployeePhoneNumber());
        variables.put(Constants.KEMPLOYEE_EMAIL, document.getEmployeeEmail());
        variables.put(Constants.KCOMPANY_NAME, document.getCompanyName());
        variables.put(Constants.KCOMPANY_REGISTRATION_NUMBER, document.getCompanyRegistrationNumber());
        variables.put(Constants.KJOB_TYPE, document.getJobType());
        variables.put(Constants.KEMPLOYER_ADDRESS, document.getEmployerAddress().getAddressName() + " " + document.getEmployerAddress().getAddressDetail());
        variables.put(Constants.KEMPLOYER_NAME, document.getEmployerName());
        variables.put(Constants.KEMPLOYER_PHONE_NUMBER, document.getEmployerPhoneNumber());
        variables.put(Constants.KWORK_PERIOD, document.getWorkPeriod().getKrName());
        variables.put(Constants.KHOURLY_RATE, document.getHourlyRate().toString());
        variables.put(Constants.KWORK_DAYS_WEEKDAYS, document.getWorkDaysWeekDays());
        variables.put(Constants.KWORK_DAYS_WEEKENDS, document.getWorkDaysWeekends());
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
            if (!updatedText.equals(paragraphText.toString())) {
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
