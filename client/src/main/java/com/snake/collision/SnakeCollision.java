package com.snake.collision;

import com.snake.managers.snake.SnakeManager;
import com.snake.model.game.SnakePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;

@Service
public class SnakeCollision implements Collision {

    private SnakeManager snakeManager;
    private Area collisionArea;

    @Autowired
    public SnakeCollision(SnakeManager snakeManager) {
        collisionArea = new Area();
        this.snakeManager = snakeManager;
    }

    @Override
    public void refreshCollisionArea() {
        collisionArea = new Area();
        snakeManager.getSnake().getParts().stream()
                .map(SnakePart::getRect)
                .map(Area::new)
                .forEach(collisionArea::add);
    }

    @Override
    public Area getCollisionArea() {
        return collisionArea;
    }

    public Area getBodyCollisionArea() {
        Area area = new Area();
        snakeManager.getSnake().getBody().stream()
                .map(SnakePart::getRect)
                .map(Area::new)
                .forEach(area::add);
        return area;
    }
}
