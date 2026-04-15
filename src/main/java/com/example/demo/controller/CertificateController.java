package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import com.example.demo.model.Certificate;
import com.example.demo.repository.CertificateRepository;
import com.example.demo.service.certificateservice;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private certificateservice certificateService;

    // ✅ Get certificates for logged-in user
    @GetMapping("/user/{email}")
    public List<Certificate> getUserCertificates(@PathVariable String email) {
        return certificateRepository.findByUserEmail(email);
    }

    // ✅ Generate PDF
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] generateCertificate(
            @RequestParam String name,
            @RequestParam String event,
            @RequestParam String type) throws Exception {

        return certificateService.generateCertificate(name, event, type);
    }

    // ✅ Upload Excel
    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String eventName,
            @RequestParam String type
    ) {

        certificateService.processExcel(file, eventName, type);

        return "Certificates issued successfully!";
    }
}