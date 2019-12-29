//package com.snake;
//
//import com.snake.view.imagepanel.JPanelWithBackground;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.function.Consumer;
//
//public class StartPanel {
//
//    private Consumer<Graphics2D> graphics;
//
//    public StartPanel(Consumer<Graphics2D> graphics) throws IOException {
//        String backgroundImage = ("resources/snake.jpg");
//
//        JFrame frame = new JFrame("Snake");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(new JPanelWithBackground(backgroundImage));
//        frame.pack();
//        frame.setLocationByPlatform(true);
//        frame.setVisible(true);
//
//        this.graphics = graphics;
//    }
//
//}
