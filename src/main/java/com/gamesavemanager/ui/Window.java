package main.java.com.gamesavemanager.ui;

import java.awt.*;
import javax.swing.*;

public class Window {
    private JButton btnAddGame;
    private JPanel mainWindow;
    private JTable table1;
    private JPanel mainSouthJpnl;

    public Window() {
        mainWindow = new JPanel();
        mainWindow.setLayout(new BorderLayout(10, 10));
        mainWindow.setPreferredSize(new Dimension(400, 300));

        // Table at the top (CENTER)
        table1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setPreferredSize(new Dimension(150, 50));
        mainWindow.add(scrollPane, BorderLayout.CENTER);

        // Button panel at the bottom (SOUTH)
        mainSouthJpnl = new JPanel();
        mainSouthJpnl.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnAddGame = new JButton("Add Game");
        mainSouthJpnl.add(btnAddGame);
        mainWindow.add(mainSouthJpnl, BorderLayout.SOUTH);

        btnAddGame.addActionListener(e -> {
            System.out.println("Opening Add Game Window with drag-and-drop support...");
            AddGameWindow addGameWindow = new AddGameWindow(game -> {
                System.out.println("Game received in main window: " + game.getName());
                try {
                    main.java.com.gamesavemanager.storage.GameStorer.saveGame(game);
                    System.out.println("Game saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainWindow, "Failed to save game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
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
