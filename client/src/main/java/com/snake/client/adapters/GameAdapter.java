package com.snake.client.adapters;

import com.snake.client.managers.GameManagerImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class GameAdapter extends KeyAdapter {

    private GameManagerImpl gameManager;

    @Autowired
    public void setGameManager(GameManagerImpl gameManager) {
        this.gameManager = gameManager;
    }

    @SneakyThrows
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            if (gameManager.isGameStopped()) {
                if (gameManager.isGameLost()) {
                    gameManager.setGameLost(false);
                    gameManager.restartGame();
                } else {
                    gameManager.continueGame();
                }
            } else {
                gameManager.stopGame();
            }
        }
    }
}
