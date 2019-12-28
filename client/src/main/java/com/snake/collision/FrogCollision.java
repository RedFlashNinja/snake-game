package com.snake.collision;

import com.snake.managers.frog.FrogManager;
import com.snake.model.game.Frog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;

@Service
public class FrogCollision implements Collision {

    private Area collisionArea;
    private FrogManager frogManager;

    @Autowired
    public void setFrogManager(FrogManager frogManager) {
        this.frogManager = frogManager;
    }

    public FrogCollision() {
        collisionArea = new Area();
    }

    @Override
    public Area getCollisionArea() {
        return collisionArea;
    }

    @Override
    public void refreshCollisionArea() {
        collisionArea = new Area();
        frogManager.getFrogs().stream()
                .map(Frog::getRect)
                .map(Area::new)
                .forEach(collisionArea::add);
    }
}
