package com.snake.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RestAccess {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RestConfig.class);
    }
}
