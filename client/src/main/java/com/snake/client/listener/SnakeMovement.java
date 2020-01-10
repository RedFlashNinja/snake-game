package com.snake.client.listener;

import com.snake.client.Constants;
import com.snake.client.collision.SnakeCollision;
import com.snake.client.managers.GameManagerImpl;
import com.snake.client.managers.frog.FrogManagerImpl;
import com.snake.client.managers.snake.SnakeManagerImpl;
import com.snake.model.client.Frog;
import com.snake.model.client.GameField;
import com.snake.model.client.Snake;
import com.snake.view.GamePanel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Area;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SnakeMovement {

    private int tickNumber = 0;
    private GameManagerImpl gameController;
    private SnakeManagerImpl snakeController;
    private FrogManagerImpl frogController;
    private SnakeCollision collisionController;
    private GamePanel gamePanel;

    @Value("${snake.movement.speed}")
    private int snakeMovementSpeed;

    @Autowired
    public void setGameController(GameManagerImpl gameController) {
        this.gameController = gameController;
    }

    @Autowired
    public void setSnakeController(SnakeManagerImpl snakeController) {
        this.snakeController = snakeController;
    }

    @Autowired
    public void setFrogController(FrogManagerImpl frogController) {
        this.frogController = frogController;
    }

    @Autowired
    public void setCollisionController(SnakeCollision collisionController) {
        this.collisionController = collisionController;
    }

    @Autowired
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Scheduled(fixedDelay = Constants.SNAKE_MOVEMENT_RATE)
    public void nextStep() {
        if (gameController.isGameStopped()) {
            return;
        }
        if (tickNumber < Constants.SNAKE_MOVEMENT_SPEED / snakeMovementSpeed) {
            tickNumber++;
            return;
        } else {
            tickNumber = 0;
        }

        snakeController.moveSnake();
        collisionController.refreshCollisionArea();

        Snake snake = snakeController.getSnake();
        Set<Frog> frogs = frogController.getFrogs();
        GameField field = gameController.getField();
        Area snakeCollisionArea = collisionController.getBodyCollisionArea();
        Rectangle snakeHead = snake.getHead().getRect();
        if ((!field.contains(snakeHead) || snakeCollisionArea.contains(snakeHead))
                && !gameController.isGameStopped()) {
            log.info("Stopping game. Snake collides with something.");

            gameController.setGameLost(true);
            gameController.stopGame();
            return;
        }
        List<Frog> consumedFrogs = frogs.stream()
                .filter(frog -> snakeCollisionArea.contains(frog.getRect()))
                .collect(Collectors.toList());
        for (Frog consumedFrog : consumedFrogs) {
            log.info("Snake eats the frog at {}.", consumedFrog.getRect());

            frogController.removeFrog(consumedFrog);
            frogController.createFrog();
            snakeController.addSnakePart();
        }
        gamePanel.repaint();
    }
}
