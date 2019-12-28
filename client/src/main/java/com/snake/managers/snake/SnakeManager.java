package com.snake.managers.snake;

import com.snake.model.game.Snake;

import java.awt.*;

public interface SnakeManager {

    Snake getSnake();

    Snake createSnake();

    void addSnakePart();

    void moveSnake();

    Dimension getSnakePartDimension();

    boolean isAbleDirectionChange();

    void enableDirectionChange();

    void disableDirectionChange();
}
