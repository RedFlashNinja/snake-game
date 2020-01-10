package com.snake.model.client;

import com.snake.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class SnakePart {
    private Rectangle rect;
    private SnakePart nextPart;
    private SnakePart previousPart;
    private Direction direction;
}