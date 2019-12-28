package com.snake.listener;

import com.snake.collision.GameCollision;
import com.snake.managers.GameManager;
import com.snake.managers.frog.FrogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Area;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class FrogPosition {

    private Random random;
    private BlockingQueue<Point> freeSpots;
    private GameCollision gameCollision;
    private GameManager gameManager;
    private FrogManager frogManager;

    @Autowired
    public void setGameCollision(GameCollision gameCollision) {
        this.gameCollision = gameCollision;
    }

    @Autowired
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Autowired
    public void setFrogManager(FrogManager frogManager) {
        this.frogManager = frogManager;
    }

    public FrogPosition() {
        this.freeSpots = new ArrayBlockingQueue<>(100);
        random = new Random();
    }

    @Async
    @Scheduled(fixedRate = 10)
    public void findFreePosition() {
        if (spotsReady()) {
            return;
        }

        Area collisionArea = gameCollision.getCollisionArea();
        Dimension frogScale = frogManager.getFrogDimension();
        Rectangle spot = new Rectangle(frogScale);
        Point position;
        do {
            if (spotsReady()) {
                return;
            }
            position = tryNewSpot();
            spot.setLocation(position);
        } while (collisionArea.contains(spot) || !gameManager.getField().contains(spot));
        boolean inserted = freeSpots.offer(position);

        if (inserted) {
            log.debug("Found new position for frog {}.", position);

        }
    }

    private Point tryNewSpot() {
        Dimension frogDimension = frogManager.getFrogDimension();
        Rectangle field = gameManager.getField().getBorders();
        Point newPosition = new Point(
                random.nextInt((field.width) / frogDimension.width) * frogDimension.width,
                random.nextInt((field.height) / frogDimension.height) * frogDimension.height);

        log.debug("Trying to find new spot for frog here: {}.", newPosition);

        if (freeSpots.stream().anyMatch(position -> position.equals(newPosition.getLocation()))) {
            tryNewSpot();
        }
        return newPosition;
    }

    public BlockingQueue<Point> getFreeSpots() {
        return freeSpots;
    }

    public boolean spotsReady() {
        return freeSpots.remainingCapacity() == 0;
    }
}
