package com.snake.client.managers.snake;

import com.snake.client.Constants;
import com.snake.client.SnakeCreationException;
import com.snake.client.collision.CollisionException;
import com.snake.client.managers.GameManager;
import com.snake.model.Direction;
import com.snake.model.client.GameField;
import com.snake.model.client.Snake;
import com.snake.model.client.SnakePart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Area;
import java.util.HashSet;

@Slf4j
@Service
public class SnakeManagerImpl implements SnakeManager {

    private Snake snake;
    private Dimension snakePartDimension;
    private int snakeLength;
    private boolean ableDirectionChange;
    private GameManager gameManager;

    @Autowired
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public SnakeManagerImpl(@Value("${snake.part.scale}") float snakePartDimension,
                            @Value("${snake.length}") int snakeLength) {
        if (snakeLength < Constants.MIN_SNAKE_LENGTH) {
            throw new IllegalArgumentException("Snake length should not be less than " +
                    Constants.MIN_SNAKE_LENGTH + ".");
        }
        this.snakeLength = snakeLength;
        int width = (int) (snakePartDimension * Constants.FIELD_CELL_SIZE);
        int height = width;
        this.snakePartDimension = new Dimension(width, height);
    }

    @Override
    public Snake createSnake() {
        GameField field = gameManager.getField();
        Direction currentDirection = Direction.RIGHT;
        snake = new Snake(new HashSet<>());
        Point topLeftPosition = new Point();
        Rectangle currentPosition = new Rectangle(topLeftPosition, snakePartDimension);
        SnakePart nextSnakePart = null;
        Area snakeCollisionArea = new Area();

        for (int i = 0; i < snakeLength; i++) {
            SnakePart currentSnakePart =
                    new SnakePart(currentPosition, nextSnakePart, null, currentDirection);
            if (nextSnakePart != null) {
                nextSnakePart.setPreviousPart(currentSnakePart);
            }
            if (!field.getBorders().contains(currentSnakePart.getRect())) {
                throw new CollisionException("Could not locate snake part. Not enough field space.");
            }
            snakeCollisionArea.add(new Area(currentSnakePart.getRect()));
            snake.getParts().add(currentSnakePart);

            Rectangle newPosition = new Rectangle(currentPosition);
            Direction newDirection = currentDirection;
            boolean completed = false;
            do {
                Point delta = newDirection.delta(snakePartDimension);
                newPosition.translate(delta.x, delta.y);
                if ((!field.getBorders().contains(newPosition.x, newPosition.y) ||
                        snakeCollisionArea.contains(newPosition.x, newPosition.y))) {
                    newPosition.translate(-delta.x, -delta.y);
                    newDirection = newDirection.rotate();
                    if (newDirection == currentDirection) {
                        break;
                    }
                    continue;
                }
                completed = true;
            } while (!completed);
            if (!completed) {
                throw new SnakeCreationException("Could not find direction for new snake part.");
            }
            currentSnakePart.setDirection(newDirection);
            currentDirection = newDirection;
            currentPosition = newPosition;

            nextSnakePart = currentSnakePart;
        }

        log.debug("Snake was created at {}.", snake.getHead().getRect());

        return snake;
    }

    @Override
    public void addSnakePart() {
        SnakePart tail = snake.getTail();
        SnakePart newTail = new SnakePart(new Rectangle(tail.getRect()), null, tail, tail.getDirection());
        Point delta = tail.getDirection().delta(snakePartDimension);
        newTail.getRect().translate(-delta.x, -delta.y);
        tail.setNextPart(newTail);
        snake.getParts().add(newTail);
    }

    @Override
    public void moveSnake() {
        SnakePart currentPart = snake.getHead();
        while (currentPart != null) {
            Direction currentDirection = currentPart.getDirection();
            Point delta = currentDirection.delta(snakePartDimension);

            if (currentPart.getPreviousPart() == null) {
                log.debug("Moving from {} to {},{}.", currentPart.getRect(),
                        (currentPart.getRect().x + delta.x), (currentPart.getRect().y + delta.y));
            }

            currentPart.getRect().translate(delta.x, delta.y);
            currentPart = currentPart.getNextPart();
        }
        currentPart = snake.getTail();
        while (currentPart != null) {
            if (currentPart.getPreviousPart() != null) {
                currentPart.setDirection(currentPart.getPreviousPart().getDirection());
            }
            currentPart = currentPart.getPreviousPart();
        }
        enableDirectionChange();
    }

    @Override
    public Snake getSnake() {
        return snake;
    }

    @Override
    public Dimension getSnakePartDimension() {
        return snakePartDimension;
    }

    public boolean isAbleDirectionChange() {
        return ableDirectionChange;
    }

    public void enableDirectionChange() {
        this.ableDirectionChange = true;
    }

    public void disableDirectionChange() {
        this.ableDirectionChange = false;
    }
}
