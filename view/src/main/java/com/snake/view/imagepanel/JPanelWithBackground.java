package com.snake.view.imagepanel;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class JPanelWithBackground extends JPanel {

    private Image backgroundImage;

    public JPanelWithBackground(String fileName) throws IOException {
        backgroundImage = ImageIO.read(new File(fileName));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }
}