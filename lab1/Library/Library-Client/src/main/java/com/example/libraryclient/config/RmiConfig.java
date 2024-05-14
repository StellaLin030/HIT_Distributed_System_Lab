package com.example.libraryclient.config;

import lombok.extern.slf4j.Slf4j;
import com.example.librarycommon.service.LibraryService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RmiConfig {
    @Bean(name="libraryService")
    public LibraryService libraryService() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/LibraryService");
        rmiProxyFactoryBean.setServiceInterface(LibraryService.class);
        rmiProxyFactoryBean.afterPropertiesSet();
        log.info("libraryService bean created successfully.");
        return (LibraryService) rmiProxyFactoryBean.getObject();
    }
}
