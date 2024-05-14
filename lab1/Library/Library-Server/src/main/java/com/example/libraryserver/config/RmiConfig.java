package com.example.libraryserver.config;

import com.example.librarycommon.service.LibraryService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;


@Configuration
public class RmiConfig {

    @Bean
    public RmiServiceExporter rmiServiceExporter(LibraryService libraryService) {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("LibraryService");
        exporter.setService(libraryService);
        exporter.setServiceInterface(LibraryService.class);
        exporter.setRegistryPort(1099);
        return exporter;
    }
}