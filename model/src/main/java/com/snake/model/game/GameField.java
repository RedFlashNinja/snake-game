package com.snake.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class GameField {
    private Rectangle borders;

    public boolean contains(Rectangle rect) {
        return borders.contains(rect);
    }
}
