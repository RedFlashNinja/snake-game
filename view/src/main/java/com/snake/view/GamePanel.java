package com.snake.view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.function.Consumer;

public class GamePanel extends JPanel {

    private Consumer<Graphics2D> graphics;

    public GamePanel(Consumer<Graphics2D> graphics) {
        setBackground(Color.BLACK);
        setFocusable(true);
        this.graphics = graphics;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.accept(g2d);
    }
}
