package com.snake.client.managers.frog;

import com.snake.model.client.Frog;

import java.awt.*;
import java.util.Set;

public interface FrogManager {

    Set<Frog> getFrogs();

    Frog createFrog();

    Set<Frog> createFrogs();

    void removeFrog(Frog frog);

    Dimension getFrogDimension();

    int getFrogNumber();
}
