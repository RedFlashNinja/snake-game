package com.snake.client.managers;

import com.snake.model.client.GameField;

public interface GameManager {

    GameField getField();

    boolean isGameStopped();

    void stopGame();

    void continueGame();

    void startGame();

    void restartGame();

    boolean isGameLost();

    void setGameLost(boolean gameLost);

}
