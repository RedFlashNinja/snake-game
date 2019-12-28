package com.snake.collision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;

@Service
public class GameCollision implements Collision {

    private Area collisionArea;
    private SnakeCollision snakeCollision;
    private FrogCollision frogCollision;

    @Autowired
    public void setSnakeCollision(SnakeCollision snakeCollision) {
        this.snakeCollision = snakeCollision;
    }

    @Autowired
    public void setFrogCollision(FrogCollision frogCollision) {
        this.frogCollision = frogCollision;
    }

    public GameCollision() {
        collisionArea = new Area();
    }

    @Override
    public void refreshCollisionArea() {
        Area frogCollisionArea = frogCollision.getCollisionArea();
        Area snakeCollisionArea = snakeCollision.getCollisionArea();

        collisionArea = new Area();
        collisionArea.add(frogCollisionArea);
        collisionArea.add(snakeCollisionArea);
    }

    @Override
    public Area getCollisionArea() {
        return collisionArea;
    }
}
