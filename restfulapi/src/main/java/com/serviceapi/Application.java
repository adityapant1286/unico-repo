package com.serviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 */

@ComponentScan(basePackages = "com.serviceapi")
@SpringBootApplication
public class Application {

//    private static final String JMS_URL = "vm://embedded?broker.persistent=false";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }



}
