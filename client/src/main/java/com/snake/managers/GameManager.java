package com.snake.managers;

import com.snake.model.game.GameField;

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
