package com.snake;

import com.snake.hibernate.HibernateSessionFactory;
import com.snake.hibernate.UpdateDB;
import com.snake.io.entity.PlayerEntity;
import com.snake.managers.GameManager;
import com.snake.view.GameFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
        GameFrame mainFrame = context.getBean(GameFrame.class);
        GameManager gameManager = context.getBean(GameManager.class);
        mainFrame.setVisible(true);
        preconfig();
        gameManager.startGame();
    }

    public static void preconfig() {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setUserId("1");
        playerEntity.setNickname("newPlayer");

        new UpdateDB().saveResults(playerEntity);
        HibernateSessionFactory.shutdown();
    }
}