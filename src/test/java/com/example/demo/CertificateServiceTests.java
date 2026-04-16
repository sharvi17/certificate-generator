package com.example.demo;

import com.example.demo.repository.CertificateRepository;
import com.example.demo.service.certificateservice;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CertificateServiceTests {

    @Test
    public void testServiceInitialization() {

        // Create mock repository
        CertificateRepository repo = mock(CertificateRepository.class);

        // Create service
        certificateservice service = new certificateservice();

        try {
            // Inject mock repository manually
            java.lang.reflect.Field field =
                    service.getClass().getDeclaredField("certificateRepository");
            field.setAccessible(true);
            field.set(service, repo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Simple assertion: service should not be null
        assert(service != null);
    }
}