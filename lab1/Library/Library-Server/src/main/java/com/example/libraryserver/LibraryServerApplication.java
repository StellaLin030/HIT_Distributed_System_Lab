package com.example.libraryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class LibraryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryServerApplication.class, args);
        log.info("Library system server start successfully!");
    }
}
