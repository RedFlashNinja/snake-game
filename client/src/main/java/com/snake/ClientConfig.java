package com.snake;

import com.snake.adapters.GameAdapter;
import com.snake.adapters.SnakeAdapter;
import com.snake.managers.GameManager;
import com.snake.managers.gui.ViewManager;
import com.snake.view.GameFrame;
import com.snake.view.GamePanel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@ComponentScan
@EnableAsync
@EnableScheduling
@PropertySource({"classpath:application.properties"})
public class ClientConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    @Bean
//    public StartPanel startPanel(ViewManager viewManager) throws IOException {
//        StartPanel startPanel = new StartPanel(viewManager::draw);
//        return startPanel;
//    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        return scheduler;
    }

    @Bean
    public GamePanel gamePanel(SnakeAdapter snakeAdapter, GameManager gameManager,
                               ViewManager viewManager, GameAdapter gameAdapter) {
        GamePanel panel = new GamePanel(viewManager::draw);
        panel.addKeyListener(snakeAdapter);
        panel.addKeyListener(gameAdapter);
        panel.setPreferredSize(gameManager.getField().getBorders().getSize());
        return panel;
    }

    @Bean
    public GameFrame frame(GamePanel gamePanel) {
        return new GameFrame(gamePanel);
    }

}
