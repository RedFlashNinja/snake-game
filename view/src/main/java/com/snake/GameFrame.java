package com.snake;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(GamePanel gamePanel) {
        add(gamePanel);
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}