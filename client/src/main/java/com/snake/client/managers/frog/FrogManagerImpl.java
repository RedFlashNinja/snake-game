package com.snake.client.managers.frog;

import com.snake.client.Constants;
import com.snake.client.collision.GameCollision;
import com.snake.client.listener.FrogPosition;
import com.snake.model.client.Frog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class FrogManagerImpl implements FrogManager {

    private Set<Frog> frogs;
    private Dimension frogDimension;
    private int frogNumber;
    private FrogPosition frogPosition;
    private GameCollision collisionController;

    @Autowired
    public void setFrogPosition(FrogPosition frogPosition) {
        this.frogPosition = frogPosition;
    }

    @Autowired
    public void setCollisionController(GameCollision collisionController) {
        this.collisionController = collisionController;
    }

    public FrogManagerImpl(@Value("${frog.number}") int frogNumber,
                           @Value("${frog.scale}") float frogDimension) {
        if (frogNumber < Constants.MIN_FROG_NUMBER) {
            throw new IllegalArgumentException("Number of frogs can't be lesser than " +
                    Constants.MIN_FROG_NUMBER + ".");
        }
        int width = (int) (Constants.FIELD_CELL_SIZE * frogDimension);
        int height = width;
        this.frogNumber = frogNumber;
        this.frogDimension = new Dimension(width, height);
        this.frogs = new HashSet<>();
    }

    @Override
    public Frog createFrog() {
        try {
            Point position = frogPosition.getFreeSpots().take();
            Rectangle frogArea = new Rectangle(position.x, position.y, frogDimension.width, frogDimension.height);
            Frog frog = new Frog(frogArea);
            this.frogs.add(frog);
            collisionController.refreshCollisionArea();

            log.debug("Frog was created at {}.", frogArea);

            return frog;
        } catch (InterruptedException e) {
            log.error("Error occurred during frog creation.", e);
            throw new RuntimeException();
        }
    }

    @Override
    public Set<Frog> createFrogs() {
        frogs = new HashSet<>();
        for (int i = 0; i < frogNumber; i++) {
            frogs.add(createFrog());
        }
        return frogs;
    }

    @Override
    public void removeFrog(Frog frog) {
        log.debug("Remove frog at {}.", frog.getRect());
        this.frogs.remove(frog);
    }

    @Override
    public Set<Frog> getFrogs() {
        return frogs;
    }

    @Override
    public Dimension getFrogDimension() {
        return frogDimension;
    }

    @Override
    public int getFrogNumber() {
        return frogNumber;
    }
}
