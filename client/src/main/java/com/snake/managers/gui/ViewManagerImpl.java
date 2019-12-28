package com.snake.managers.gui;

import com.snake.managers.frog.FrogManager;
import com.snake.managers.snake.SnakeManager;
import com.snake.model.game.Frog;
import com.snake.model.game.Snake;
import com.snake.model.game.SnakePart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

@Slf4j
@Service
public class ViewManagerImpl implements ViewManager {

    private SnakeManager snakeManager;
    private FrogManager frogManager;

    @Autowired
    public void setSnakeManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }

    @Autowired
    public void setFrogManager(FrogManager frogManager) {
        this.frogManager = frogManager;
    }

    @Override
    public void draw(Graphics2D g) {
        log.debug("Redrawing components...");

        Color color = g.getColor();
        drawFrogs(g);
        drawSnake(g);
        g.setColor(color);
    }

    private void drawFrogs(Graphics2D g) {
        g.setColor(Color.GREEN);
        Dimension d = frogManager.getFrogDimension();
        RoundRectangle2D.Double rs = new RoundRectangle2D.Double(0, 0, d.getWidth() / 3, d.getHeight() / 3,
                d.getWidth() / 3, d.getHeight() / 3);
        for (Frog frog : frogManager.getFrogs()) {
            Rectangle2D r = frog.getRect();
            rs.x = r.getX() + r.getWidth() / 2 - rs.getWidth() / 3;
            rs.y = r.getY() + r.getHeight() / 2 - rs.getHeight() / 3;
            g.draw(rs);
            g.fill(rs);
        }
    }

    private void drawSnake(Graphics2D g) {
        g.setColor(Color.BLUE);
        Snake snake = snakeManager.getSnake();
        SnakePart snakeHead = snake.getHead();
        SnakePart snakeTail = snake.getTail();
        Dimension d = snakeManager.getSnakePartDimension();
        RoundRectangle2D rs = new RoundRectangle2D.Double(0, 0, d.width, d.height, d.width, d.height);
        RoundRectangle2D.Double rhead = new RoundRectangle2D.Double(0, 0,
                rs.getWidth() / 2, rs.getHeight() / 2, rs.getArcWidth() / 2, rs.getArcHeight() / 2);
        RoundRectangle2D.Double rbody = new RoundRectangle2D.Double(0, 0,
                rs.getWidth() / 3, rs.getHeight() / 3, rs.getArcWidth() / 3, rs.getArcHeight() / 3);
        RoundRectangle2D.Double rtail = new RoundRectangle2D.Double(0, 0,
                rs.getWidth() / 4, rs.getHeight() / 4, rs.getArcWidth() / 4, rs.getArcHeight() / 4);

        for (SnakePart snakePart : snake.getParts()) {
            Rectangle r = snakePart.getRect();
            if (snakePart == snakeHead) {
                rhead.x = r.getX() + r.getWidth() / 2 - rhead.getWidth() / 2;
                rhead.y = r.getY() + r.getHeight() / 2 - rhead.getHeight() / 2;
                g.draw(rhead);
                g.fill(rhead);
            } else if (snakePart == snakeTail) {
                rtail.x = r.getX() + r.getWidth() / 2 - rtail.getWidth() / 4;
                rtail.y = r.getY() + r.getHeight() / 2 - rtail.getHeight() / 4;
                g.draw(rtail);
                g.fill(rtail);
            } else {
                rbody.x = r.getX() + r.getWidth() / 2 - rbody.getWidth() / 3;
                rbody.y = r.getY() + r.getHeight() / 2 - rbody.getHeight() / 3;
                g.draw(rbody);
                g.fill(rbody);
            }
        }
    }
}