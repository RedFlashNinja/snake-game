package com.snake.client.adapters;

import com.snake.client.managers.snake.SnakeManagerImpl;
import com.snake.model.Direction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class SnakeAdapter extends KeyAdapter {

    private SnakeManagerImpl snakeManager;

    @Autowired
    public void setSnakeManager(SnakeManagerImpl snakeManager) {
        this.snakeManager = snakeManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!snakeManager.isAbleDirectionChange()) {
            return;
        }

        Direction direction = snakeManager.getSnake().getHead().getDirection();
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT) && (direction != Direction.RIGHT)) {
            direction = Direction.LEFT;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != Direction.LEFT)) {
            direction = Direction.RIGHT;
        } else if ((key == KeyEvent.VK_UP) && (direction != Direction.DOWN)) {
            direction = Direction.UP;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != Direction.UP)) {
            direction = Direction.DOWN;
        }
        snakeManager.getSnake().getHead().setDirection(direction);
        snakeManager.disableDirectionChange();
    }
}
