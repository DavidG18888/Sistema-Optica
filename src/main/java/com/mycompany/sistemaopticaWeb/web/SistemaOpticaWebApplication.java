package com.mycompany.sistemaoptica.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mycompany.sistemaoptica")
public class SistemaOpticaWebApplication {
    public static void main(String[] args) { SpringApplication.run(SistemaOpticaWebApplication.class, args); }
}
