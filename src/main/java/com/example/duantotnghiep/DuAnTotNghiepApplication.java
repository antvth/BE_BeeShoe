package com.example.duantotnghiep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DuAnTotNghiepApplication {

    public static void main(String[] args) {
        SpringApplication.run(DuAnTotNghiepApplication.class, args);
    }

}
