package com.snake.client.collision;

import java.awt.geom.Area;

public interface Collision {

    void refreshCollisionArea();

    Area getCollisionArea();
}
