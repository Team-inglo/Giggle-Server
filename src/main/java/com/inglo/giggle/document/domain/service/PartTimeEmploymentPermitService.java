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
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.docx4j.dml.CTPoint2D;
import org.docx4j.dml.wordprocessingDrawing.*;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;
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
import java.util.*;

@Service
@Slf4j
public class PartTimeEmploymentPermitService {
    @Value("${template.part-time-employment-permit.word.path}")
    private String wordTemplatePath;

    @Value("${template.part-time-employment-permit.hwp.path}")
    private String hwpTemplatePath;

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
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(wordTemplatePath));
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            VariablePrepare.prepare(wordMLPackage);

            HashMap<String, String> variables = new HashMap<>();

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

            documentPart.variableReplace(variables);

            // Base64로 인코딩된 서명 이미지를 SVG 파일로 변환
            String base64Svg = document.getEmployerSignatureBase64();
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
                Inline inlineImage = imagePart.createImageInline("Signature", "User Signature", 1, 2, 1000000L, 300000L, false);
                Anchor anchor = new Anchor();

                // Inline의 extent 속성 값을 Anchor에 설정
                anchor.setExtent(inlineImage.getExtent());

                // effectExtent가 Inline과 Anchor에 모두 정의되어 있으면 설정
                if (inlineImage.getEffectExtent() != null) {
                    anchor.setEffectExtent(inlineImage.getEffectExtent());
                }

                // Inline의 docPr 속성 값을 Anchor에 설정
                anchor.setDocPr(inlineImage.getDocPr());

                // Inline의 cNvGraphicFramePr 속성 값을 Anchor에 설정
                if (inlineImage.getCNvGraphicFramePr() != null) {
                    anchor.setCNvGraphicFramePr(inlineImage.getCNvGraphicFramePr());
                }

                // Inline의 graphic 속성 값을 Anchor에 설정
                anchor.setGraphic(inlineImage.getGraphic());

                // Inline의 거리 설정 속성(distT, distB, distL, distR)을 Anchor에 설정
                if (inlineImage.getDistT() != null) {
                    anchor.setDistT(inlineImage.getDistT());
                }
                if (inlineImage.getDistB() != null) {
                    anchor.setDistB(inlineImage.getDistB());
                }
                if (inlineImage.getDistL() != null) {
                    anchor.setDistL(inlineImage.getDistL());
                }
                if (inlineImage.getDistR() != null) {
                    anchor.setDistR(inlineImage.getDistR());
                }

                // Anchor의 고유 속성 값 설정
                anchor.setRelativeHeight(0);       // 기본 높이
                anchor.setBehindDoc(false);         // 문서 뒤 배치 여부
                anchor.setLocked(false);            // 잠금 여부
                anchor.setLayoutInCell(true);       // 셀 내 레이아웃 여부
                anchor.setAllowOverlap(true);       // 겹침 허용 여부

                // 랩 설정 제거
                anchor.setWrapNone(null);
                anchor.setWrapSquare(null);
                anchor.setWrapTight(null);
                anchor.setWrapThrough(null);
                anchor.setWrapTopAndBottom(null);

                // simplePos 속성 설정
                CTPoint2D simplePos = new CTPoint2D();
                simplePos.setX(0L);
                simplePos.setY(0L);
                anchor.setSimplePos(simplePos);
                anchor.setSimplePosAttr(false); // Word 호환성을 위해 simplePos 속성 false로 설정

                // 수평 위치 설정
                CTPosH positionH = new CTPosH();
                positionH.setAlign(STAlignH.RIGHT); // 수평 위치: 오른쪽 정렬
                positionH.setRelativeFrom(STRelFromH.MARGIN); // 기준: 페이지 여백
                anchor.setPositionH(positionH);

                // 수직 위치 설정
                CTPosV positionV = new CTPosV();
                positionV.setAlign(STAlignV.TOP); // 수직 위치: 상단 정렬
                positionV.setRelativeFrom(STRelFromV.MARGIN); // 기준: 페이지 여백
                anchor.setPositionV(positionV);

                anchor.setWrapNone(new CTWrapNone()); // 기본 랩 설정 (없음)

                // '인 또는 서명' 위치에 이미지 삽입
                insertImageAtPlaceholder(documentPart, anchor);
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

    public ByteArrayInputStream createPartTimeEmploymentPermitHwpFile(PartTimeEmploymentPermit document) {
        try {
            HWPFile hwpFile = HWPReader.fromFile(hwpTemplatePath);

            // 텍스트 필드 삽입
            Map<String, String> variables = new HashMap<>();
            variables.put("${" + Constants.KEMPLOYEE_FULL_NAME + "}", document.getEmployeeFullName());
            variables.put("${" + Constants.KMAJOR + "}", document.getMajor());
            variables.put("${" + Constants.KTERM_OF_COMPLETION + "}", document.getTermOfCompletion().toString());
            variables.put("${" + Constants.KEMPLOYEE_PHONE_NUMBER + "}", document.getEmployeePhoneNumber());
            variables.put("${" + Constants.KEMPLOYEE_EMAIL + "}", document.getEmployeeEmail());
            variables.put("${" + Constants.KCOMPANY_NAME + "}", document.getCompanyName());
            variables.put("${" + Constants.KCOMPANY_REGISTRATION_NUMBER + "}", document.getCompanyRegistrationNumber());
            variables.put("${" + Constants.KJOB_TYPE + "}", document.getJobType());
            variables.put("${" + Constants.KEMPLOYER_ADDRESS + "}", document.getEmployerAddress().getAddressName() + " " + document.getEmployerAddress().getAddressDetail());
            variables.put("${" + Constants.KEMPLOYER_NAME + "}", document.getEmployerName());
            variables.put("${" + Constants.KEMPLOYER_PHONE_NUMBER + "}", document.getEmployerPhoneNumber());
            variables.put("${" + Constants.KWORK_PERIOD + "}", document.getWorkPeriod().getKrName());
            variables.put("${" + Constants.KHOURLY_RATE + "}", document.getHourlyRate().toString());
            variables.put("${" + Constants.KWORK_DAYS_WEEKDAYS + "}", document.getWorkDaysWeekDays());
            variables.put("${" + Constants.KWORK_DAYS_WEEKENDS + "}", document.getWorkDaysWeekends());

            modifyHwpContent(hwpFile, variables);

//            TODO: 한글파일 사진 삽입 로직 추가
//
//            // Base64로 인코딩된 서명 이미지를 SVG에서 PNG로 변환
//            String base64Svg = document.getEmployerSignatureBase64();
//            byte[] svgBytes = Base64.getDecoder().decode(base64Svg);
//            Path tempSvgFile = Files.createTempFile("signature", ".svg");
//            Files.write(tempSvgFile, svgBytes, StandardOpenOption.WRITE);
//
//            BufferedImage signatureImage = convertSvgToPng(tempSvgFile.toFile());
//            if (signatureImage != null) {
//                byte[] imageBytes = bufferedImageToByteArray(signatureImage, "png");
//                insertImageAtPlaceholderForHwp(hwpFile, imageBytes, "(인 또는 서명)");
//            } else {
//                throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
//            }

            // HWP 파일을 ByteArrayOutputStream에 저장
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HWPWriter.toStream(hwpFile, outputStream);

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

    private void insertImageAtPlaceholder(MainDocumentPart documentPart, Anchor anchor) {
        try {
            boolean isPlaceholerText1Inserted = false;
            String xpathExpression = "//w:p";
            List<Object> paragraphNodes = documentPart.getJAXBNodesViaXPath(xpathExpression, true);

            for (Object paragraphNode : paragraphNodes) {
                if (paragraphNode instanceof P paragraph) {
                    StringBuilder combinedText = new StringBuilder();

                    for (Object content : paragraph.getContent()) {
                        if (content instanceof R run) {
                            for (Object runContent : run.getContent()) {
                                if (runContent instanceof JAXBElement<?> element) {
                                    if (element.getValue() instanceof Text textElement) {
                                        combinedText.append(textElement.getValue());
                                    }
                                }
                            }
                        }
                    }

                    // 텍스트 일치 검사
                    if (combinedText.toString().contains("(인 또는 서명)") && !isPlaceholerText1Inserted) {
                        // 문단에 이미지 삽입 로직
                        Drawing drawing = new Drawing();
                        drawing.getAnchorOrInline().add(anchor);
                        paragraph.getContent().add(drawing);
                        isPlaceholerText1Inserted = true;
                    }
                }
            }
            if (!isPlaceholerText1Inserted) {
                throw new Exception("Placeholder text not found in document");
            }
        } catch (Exception e) {
            log.error("Error inserting image at placeholder: ", e);
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

//    private void insertImageAtPlaceholderForHwp(HWPFile hwpFile, byte[] imageBytes, String placeholderText) {
//        int streamIndex = hwpFile.getBinData().getEmbeddedBinaryDataList().size() + 1;
//        String streamName = "Bin" + String.format("%04X", streamIndex) + ".png";
//
//        hwpFile.getBinData().addNewEmbeddedBinaryData(streamName, imageBytes, BinDataCompress.ByStorageDefault);
//        int binDataID = addBinDataInDocInfo(hwpFile, streamIndex);
//
//        for (Section section : hwpFile.getBodyText().getSectionList()) {
//            for (Paragraph paragraph : section.getParagraphs()) {
//                if (paragraph.getControlList() != null) {
//                    for (Control control : paragraph.getControlList()) {
//                        if (control instanceof ControlTable) {
//                            ControlTable table = (ControlTable) control;
//                            table.getRowList().forEach(row -> row.getCellList().forEach(cell -> {
//                                try {
//                                    insertImageInCell(cell, placeholderText, binDataID);
//                                } catch (Exception e) {
//                                    log.error("Error inserting image in cell: ", e);
//                                }
//                            }));
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    // Cell에 이미지 삽입 함수
//    private void insertImageInCell(Cell cell, String placeholderText, int binDataID) throws UnsupportedEncodingException {
//        int instanceID = new Random().nextInt(); // 고유한 instanceID 생성
//
//        for (Paragraph cellParagraph : cell.getParagraphList()) {
//            ParaText text = cellParagraph.getText();
//            if (text != null && text.getNormalString(0).contains(placeholderText)) {
//                ControlRectangle rectangle = (ControlRectangle) cellParagraph.addNewGsoControl(GsoControlType.Rectangle);
//
//                // CtrlHeaderGso 설정
//                CtrlHeaderGso hdr = rectangle.getHeader();
//                GsoHeaderProperty prop = hdr.getProperty();
//                prop.setVertRelTo(VertRelTo.Para);
//                prop.setVertRelativeArrange(RelativeArrange.TopOrLeft);
//                prop.setHorzRelTo(HorzRelTo.Para);
//                prop.setHorzRelativeArrange(RelativeArrange.TopOrLeft);
//                hdr.setxOffset(3000);
//                hdr.setyOffset(3000);
//                hdr.setWidth(10000);
//                hdr.setHeight(5000);
//                hdr.setInstanceId(instanceID);
//
//                // ShapeComponentNormal 설정
//                ShapeComponentNormal shapeComponent = (ShapeComponentNormal) rectangle.getShapeComponent();
//
//                // LineInfo 설정: 외곽선 스타일 설정 (없음)
//                shapeComponent.createLineInfo();
//                LineInfo lineInfo = shapeComponent.getLineInfo();
//                lineInfo.getProperty().setLineType(LineType.None);  // 외곽선을 없앰
//                lineInfo.setOutlineStyle(OutlineStyle.Normal);
//
//                // FillInfo 설정: 이미지 채우기 스타일
//                shapeComponent.createFillInfo();
//                FillInfo fillInfo = shapeComponent.getFillInfo();
//                fillInfo.getType().setImageFill(true);
//                fillInfo.createImageFill();
//                ImageFill imgFill = fillInfo.getImageFill();
//                imgFill.setImageFillType(ImageFillType.FitSize);
//                imgFill.getPictureInfo().setBinItemID(binDataID);
//
//                // ShadowInfo 설정: 그림자 없음
//                shapeComponent.createShadowInfo();
//                shapeComponent.getShadowInfo().setType(ShadowType.None);
//
//                // ShapeComponentRectangle 설정
//                ShapeComponentRectangle scr = rectangle.getShapeComponentRectangle();
//                scr.setRoundRate((byte) 0);
//                scr.setX1(0);
//                scr.setY1(0);
//                scr.setX2(fromMM(10000));
//                scr.setY2(0);
//                scr.setX3(fromMM(10000));
//                scr.setY3(fromMM(5000));
//                scr.setX4(0);
//                scr.setY4(fromMM(5000));
//
//                log.info("Rectangle created with xOffset=3000, yOffset=3000, width=10000, height=5000 and image fill type set to FitSize with binDataID={}", binDataID);
//                return;
//            }
//        }
//    }
//
//    private int fromMM(int mm) {
//        if (mm == 0) {
//            return 1;
//        }
//        return (int) ((double) mm * 72000.0f / 254.0f + 0.5f);
//    }
//
//    private int addBinDataInDocInfo(HWPFile hwpFile, int streamIndex) {
//        BinData binData = new BinData();
//        binData.getProperty().setType(BinDataType.Embedding);
//        binData.getProperty().setCompress(BinDataCompress.ByStorageDefault);
//        binData.getProperty().setState(BinDataState.NotAccess);
//        binData.setBinDataID(streamIndex);
//        binData.setExtensionForEmbedding("png");
//
//        hwpFile.getDocInfo().getBinDataList().add(binData);
//        int binDataID = hwpFile.getDocInfo().getBinDataList().size();
//        // 로그 추가: BinData 설정 상태 확인
//        log.info("BinData 추가됨 - Type: Embedding, Compress: ByStorageDefault, State: NotAccess, StreamIndex: {}, Assigned BinDataID: {}, Extension: png",
//                streamIndex, binDataID);
//        return binDataID;
//    }
}
