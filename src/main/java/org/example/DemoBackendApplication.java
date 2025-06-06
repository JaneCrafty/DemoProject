package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.model")
public class DemoBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoBackendApplication.class, args);
    }
}