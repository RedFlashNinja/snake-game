package com.snake.web;

import com.snake.web.security.AppProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.snake.web")
@PropertySource({"classpath:application.properties"})
public class RestConfig {


    @Bean
    public WebApplicationContext springApplicationContext()
    {
        return new WebApplicationContext();
    }

    @Bean(name="AppProperties")
    public AppProperties getAppProperties()
    {
        return new AppProperties();
    }
}
