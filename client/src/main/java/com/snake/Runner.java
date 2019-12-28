package com.snake;

import com.snake.managers.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Runner implements CommandLineRunner {

    private GameFrame frame;
    private GameManager gameManager;

    @Autowired
    public void setFrame(GameFrame frame) {
        this.frame = frame;
    }

    @Autowired
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run(String... args) throws Exception {
        java.awt.EventQueue.invokeLater(() -> {
            frame.setVisible(true);
            gameManager.startGame();
        });
    }
}
