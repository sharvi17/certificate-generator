package com.example.demo.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

import com.example.demo.model.Certificate;
import com.example.demo.repository.CertificateRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Service
public class certificateservice {

    @Autowired
    private CertificateRepository certificateRepository;

    // ✅ Generate PDF dynamically
    public byte[] generateCertificate(String name, String event, String type) throws Exception {

        InputStream inputStream = getClass().getResourceAsStream("/templates/certificate.html");
        String html = new String(inputStream.readAllBytes());

        html = html.replace("{{name}}", name);
        html = html.replace("{{event}}", event);
        html = html.replace("{{type}}", type);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }

    // ✅ Process Excel (STORE ONLY DATA)
    public void processExcel(MultipartFile file, String eventName, String certificateType) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String name = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();

                Certificate cert = new Certificate();
                cert.setName(name);
                cert.setUserEmail(email);
                cert.setEventName(eventName);
                cert.setCertificateType(certificateType);

                certificateRepository.save(cert);
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}