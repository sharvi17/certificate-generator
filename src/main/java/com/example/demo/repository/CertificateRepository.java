package com.example.demo.repository;

import com.example.demo.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
    List<Certificate> findByUserEmail(String userEmail);
}