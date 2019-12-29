package com.snake;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootConfiguration
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ClientConfig.class).headless(false).run(args);
    }
}