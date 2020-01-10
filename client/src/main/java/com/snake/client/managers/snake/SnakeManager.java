package com.snake.client.managers.snake;

import com.snake.model.client.Snake;

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
