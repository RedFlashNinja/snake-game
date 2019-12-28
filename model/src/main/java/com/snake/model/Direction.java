package com.snake.model;

import java.awt.*;

public enum Direction {
    UP {
        @Override
        public Point delta(Dimension d) {
            return new Point(0, -d.height);
        }

        @Override
        public Direction rotate() {
            return Direction.RIGHT;
        }
    },
    DOWN {
        @Override
        public Point delta(Dimension d) {
            return new Point(0, d.height);
        }

        @Override
        public Direction rotate() {
            return Direction.LEFT;
        }
    },
    LEFT {
        @Override
        public Point delta(Dimension d) {
            return new Point(-d.width, 0);
        }

        @Override
        public Direction rotate() {
            return Direction.UP;
        }
    },
    RIGHT {
        @Override
        public Point delta(Dimension d) {
            return new Point(d.width, 0);
        }

        @Override
        public Direction rotate() {
            return Direction.DOWN;
        }
    };

    public abstract Point delta(Dimension dimension);

    public abstract Direction rotate();
}
