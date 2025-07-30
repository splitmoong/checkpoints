package main.java.com.gamesavemanager.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    private JButton btnAddGame;
    private JPanel mainWindow;
    private JTable table1;
    private JPanel mainSouthJpnl;

    public Window() {
        btnAddGame.addActionListener(e -> {
            System.out.println("Opening Add Game Window with drag-and-drop support...");
            AddGameWindow addGameWindow = new AddGameWindow();
            addGameWindow.showWindow();
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CheckPoints");
        frame.setContentPane(new Window().mainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
