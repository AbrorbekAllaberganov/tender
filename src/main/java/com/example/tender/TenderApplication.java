package com.example.tender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenderApplication.class, args);
    }

}
