package com.snake.client;

import com.snake.client.managers.GameManager;
import com.snake.view.GameFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
        GameFrame mainFrame = context.getBean(GameFrame.class);
        GameManager gameManager = context.getBean(GameManager.class);
        mainFrame.setVisible(true);
//        new DownloadUser().preconfigure();
        gameManager.startGame();
    }
}