package com.example.libraryclient;

import com.example.libraryclient.client.LibraryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class LibraryClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryClientApplication.class, args);
        log.info("Library system client start successfully!");
        LibraryClient libraryClient = new LibraryClient();
        libraryClient.run();
    }
}
