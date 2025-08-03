package com.gamesavemanager.ui;

import com.gamesavemanager.obj.Game;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Window {
    private JButton btnAddGame;
    private JPanel mainWindow;
    private JPanel mainSouthJpnl;
    private JPanel cardsPanel;

    public Window() {
        System.out.println("[DEBUG] Window constructor called");
        System.out.println("Initializing main window...");
        mainWindow = new JPanel();
        mainWindow.setLayout(new BorderLayout(10, 10));
        mainWindow.setPreferredSize(new Dimension(400, 300));

        // Panel for game cards
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane cardsScrollPane = new JScrollPane(cardsPanel);
        mainWindow.add(cardsScrollPane, BorderLayout.CENTER);

        // Button panel at the bottom (SOUTH)
        mainSouthJpnl = new JPanel();
        mainSouthJpnl.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnAddGame = new JButton("Add Game");
        mainSouthJpnl.add(btnAddGame);
        mainWindow.add(mainSouthJpnl, BorderLayout.SOUTH);

        loadGames();

        btnAddGame.addActionListener(e -> {
            System.out.println("Opening Add Game Window with drag-and-drop support...");
            AddGameWindow addGameWindow = new AddGameWindow(game -> {
                System.out.println("Game received in main window: " + game.getName());
                try {
                    com.gamesavemanager.storage.GameStorer.saveGame(game);
                    // Add new card to UI
                    cardsPanel.add(new com.gamesavemanager.ui.GameCard(game));
                    cardsPanel.revalidate();
                    cardsPanel.repaint();
                    System.out.println("Game saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainWindow, "Failed to save game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addGameWindow.showWindow();
        });
    }

    private void loadGames() {
        System.out.println("hello again");
        java.util.List<Game> games = com.gamesavemanager.storage.GameStorer.loadAllGames();
        for (Game game : games) {
            cardsPanel.add(new GameCard(game));
        }
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CheckPoints");
        frame.setContentPane(new Window().mainWindow);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
