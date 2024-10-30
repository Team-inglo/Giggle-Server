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
import jakarta.xml.bind.JAXBElement;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.HWPCharNormal;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.docx4j.dml.wordprocessingDrawing.Inline;
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
import java.io.UnsupportedEncodingException;
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

    @Value("${template.integrated-application.hwp.path}")
    private String hwpTemplatePath;

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

    public ByteArrayInputStream createIntegratedApplicationDocxFile(IntegratedApplication document) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(wordTemplatePath));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            VariablePrepare.prepare(wordMLPackage);

            HashMap<String, String> variables = new HashMap<>();

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

            documentPart.variableReplace(variables);

            // Base64로 인코딩된 서명 이미지를 SVG 파일로 변환
            String base64Svg = document.getEmployeeSignatureBase64();
            byte[] svgBytes = Base64.getDecoder().decode(base64Svg);
            Path tempSvgFile = Files.createTempFile("signature", ".svg");
            Files.write(tempSvgFile, svgBytes, StandardOpenOption.WRITE);

            // SVG 파일을 PNG로 변환
            BufferedImage signatureImage = convertSvgToPng(tempSvgFile.toFile());

            if (signatureImage != null) {
                byte[] imageBytes = bufferedImageToByteArray(signatureImage);
                assert imageBytes != null;
                BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, imageBytes);
                // Inline 이미지 생성
                Inline inlineImage = imagePart.createImageInline("Signature", "User Signature", 1, 2, 600000L, 300000L, false);
                List<Object> paragraphNodes = documentPart.getJAXBNodesViaXPath("//w:p", true);
                for (Object paragraphNode : paragraphNodes) {
                    if (paragraphNode instanceof P paragraph) {
                        StringBuilder combinedText = new StringBuilder();

                        for (Object content : paragraph.getContent()) {
                            if (content instanceof R run) {
                                for (Object runContent : run.getContent()) {
                                    if (runContent instanceof JAXBElement<?> element) {
                                        if (element.getValue() instanceof Text textElement) {
                                            log.info("textElement: {}", textElement.getValue());
                                            combinedText.append(textElement.getValue());
                                        }
                                    }
                                }
                            }
                        }
                        log.info("combinedText: {}", combinedText.toString());
                        if (combinedText.toString().contains("signature")) {
                            Drawing drawing = new Drawing();
                            drawing.getAnchorOrInline().add(inlineImage);
                            paragraph.getContent().clear();
                            paragraph.getContent().add(drawing);
                        }
                    }
                }
            }
            // 파일을 ByteArrayOutputStream에 저장
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wordMLPackage.save(outputStream);

            // ByteArrayInputStream으로 변환하여 반환
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ByteArrayInputStream createIntegratedApplicationHwpFile(IntegratedApplication document) {
        try {
            HWPFile hwpFile = HWPReader.fromFile(hwpTemplatePath);

            // 텍스트 필드 삽입
            HashMap<String, String> variables = new HashMap<>();

            variables.put("${" + Constants.SURNAME + "}", document.getLastName());
            variables.put("${" + Constants.GIVEN_NAME + "}", document.getFirstName());
            variables.put("${" + Constants.BIRTHYEAR + "}", String.valueOf(document.getBirth().getYear()));
            variables.put("${" + Constants.BIRTHMONTH + "}", String.valueOf(document.getBirth().getMonthValue()));
            variables.put("${" + Constants.BIRTHDAY + "}", String.valueOf(document.getBirth().getDayOfMonth()));
            if (document.getGender().equals(EGender.MALE)) {
                variables.put("${" + Constants.MALE + "}", Constants.V);
                variables.put("${" + Constants.FEMALE + "}", Constants.BLANK);
            } else {
                variables.put("${" + Constants.MALE + "}", Constants.BLANK);
                variables.put("${" + Constants.FEMALE + "}", Constants.V);
            }
            variables.put("${" + Constants.NATIONALITY + "}", document.getNationality());
            variables.put("${" + Constants.ADDR + "}", document.getEmployeeAddress().getFullAddress());
            variables.put("${" + Constants.TELENUM + "}", document.getTelePhoneNumber());
            variables.put("${" + Constants.CELLNUM + "}", document.getCellPhoneNumber());
            variables.put("${" + Constants.SCHOOLNAME + "}", document.getSchool().getSchoolName());
            variables.put("${" + Constants.SCHOOLPHONE + "}", document.getSchool().getSchoolPhoneNumber());
            if (document.getIsAccredited()) {
                variables.put("${" + Constants.KYESACC + "}", Constants.V);
                variables.put("${" + Constants.YESACC + "}", Constants.V);
                variables.put("${" + Constants.KNOACC + "}", Constants.BLANK);
                variables.put("${" + Constants.NOACC + "}", Constants.BLANK);
            } else {
                variables.put("${" + Constants.KYESACC + "}", Constants.BLANK);
                variables.put("${" + Constants.YESACC + "}", Constants.BLANK);
                variables.put("${" + Constants.KNOACC + "}", Constants.V);
                variables.put("${" + Constants.NOACC + "}", Constants.V);
            }
            variables.put("${" + Constants.WORKPLACE + "}", document.getNewWorkPlaceName());
            variables.put("${" + Constants.REGNUM + "}", document.getNewWorkPlaceRegistrationNumber());
            variables.put("${" + Constants.WORKNUM + "}", document.getNewWorkPlacePhoneNumber());
            variables.put("${" + Constants.ANNUAL + "}", document.getAnnualIncomeAmount().toString());
            variables.put("${" + Constants.OCCUPATION + "}", document.getOccupation());
            variables.put("${" + Constants.EMAIL + "}", document.getEmail());

            modifyHwpContent(hwpFile, variables);

            // HWP 파일을 ByteArrayOutputStream에 저장
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HWPWriter.toStream(hwpFile, outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
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

    private void modifyHwpContent(HWPFile hwpFile, Map<String, String> fields) {
        fields.forEach((placeholder, replacement) -> {
            List<Section> sections = hwpFile.getBodyText().getSectionList();
            sections.forEach(section -> {
                for (Paragraph paragraph : section.getParagraphs()) {
                    try {
                        replaceTextInParagraph(paragraph, placeholder, replacement);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    if (paragraph.getControlList() != null) {  // Control 리스트가 존재하는지 확인
                        for (Control control : paragraph.getControlList()) {
                            if (control instanceof ControlTable table) {  // 표가 포함된 문단인지 확인
                                table.getRowList().forEach(row -> {
                                    row.getCellList().forEach(cell -> {
                                        try {
                                            readAndReplaceCellText(cell, placeholder, replacement);
                                        } catch (UnsupportedEncodingException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                });
                            }
                        }
                    }
                }
            });
        });
    }

    // 일반 텍스트 문단의 텍스트를 치환하는 메서드
    private void replaceTextInParagraph(Paragraph paragraph, String placeholder, String replacement) throws UnsupportedEncodingException {
        ParaText text = paragraph.getText();
        if (text != null) {
            // 현재 텍스트를 검색하여 placeholder의 시작 인덱스를 찾음
            String currentText = text.getNormalString(0);
            int startIndex = currentText.indexOf(placeholder);

            if (startIndex != -1) {
                // placeholder의 각 문자를 교체
                for (int i = 0; i < placeholder.length(); i++) {
                    HWPCharNormal charNormal = (HWPCharNormal) text.getCharList().get(startIndex + i);
                    if (i < replacement.length()) {
                        charNormal.setCode((short) replacement.codePointAt(i));
                    } else {
                        // placeholder가 replacement보다 긴 경우 남은 문자는 공백으로 처리
                        charNormal.setCode((short) '\u200B');  // zero-width space 사용
                    }
                }

                // replacement가 placeholder보다 긴 경우, 추가 문자를 삽입
                for (int i = placeholder.length(); i < replacement.length(); i++) {
                    HWPCharNormal newChar = text.insertNewNormalChar(startIndex + i);
                    newChar.setCode((short) replacement.codePointAt(i));
                }
            }
        }
    }



    private void readAndReplaceCellText(Cell cell, String placeholder, String replacement) throws UnsupportedEncodingException {
        for (Paragraph cellParagraph : cell.getParagraphList()) { // 셀 내부의 문단들을 순회
            ParaText text = cellParagraph.getText();
            if (text != null) {
                // 현재 텍스트를 가져와 placeholder의 위치를 찾음
                String currentText = text.getNormalString(0);
                int startIndex = currentText.indexOf(placeholder);

                if (startIndex != -1) {
                    // placeholder의 각 문자를 replacement로 교체
                    for (int i = 0; i < placeholder.length(); i++) {
                        HWPCharNormal charNormal = (HWPCharNormal) text.getCharList().get(startIndex + i);
                        if (i < replacement.length()) {
                            charNormal.setCode((short) replacement.codePointAt(i));
                        } else {
                            // placeholder가 replacement보다 길다면 남은 부분은 공백으로 처리
                            charNormal.setCode((short) '\u200B');  // zero-width space 사용
                        }
                    }

                    // replacement가 placeholder보다 긴 경우, 추가 문자를 삽입
                    for (int i = placeholder.length(); i < replacement.length(); i++) {
                        HWPCharNormal newChar = text.insertNewNormalChar(startIndex + i);
                        newChar.setCode((short) replacement.codePointAt(i));
                    }
                }
            }
        }
    }
}
