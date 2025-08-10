package com.gamesavemanager;

import com.gamesavemanager.ui.Window;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Checkpoints");
        frame.setContentPane(new Window().mainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
