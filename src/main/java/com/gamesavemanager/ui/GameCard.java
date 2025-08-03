package com.gamesavemanager.ui;

import com.gamesavemanager.obj.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameCard extends JPanel {
    public GameCard(Game game) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10)); // 10px margin

        // Load image (fallback to placeholder if not found)
        JLabel imageLabel = new JLabel();
        BufferedImage img = null;
        Path imgPath = game.getLocalImagePath();
        System.out.println("[GameCard] Game: " + game.getName() + ", localImagePath: " + imgPath);
        if (imgPath != null && Files.exists(imgPath)) {
            try {
                img = ImageIO.read(imgPath.toFile());
                System.out.println("[GameCard] Loaded image from: " + imgPath);
            } catch (IOException ex) {
                System.out.println("[GameCard] Failed to load image: " + ex.getMessage());
            }
        } else {
            if (imgPath == null) {
                System.out.println("[GameCard] localImagePath is null for game: " + game.getName());
            } else if (!Files.exists(imgPath)) {
                System.out.println("[GameCard] Image file does not exist at: " + imgPath);
            }
        }
        if (img != null) {
            Image scaled = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } else {
            imageLabel.setPreferredSize(new Dimension(120, 120));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setText("No Image");
        }
        add(imageLabel, BorderLayout.CENTER);

        // Bottom panel for name (left) and date (right)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(game.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(nameLabel, BorderLayout.WEST);

        String dateStr = "";
        if (game.getDateAdded() != null) {
            dateStr = game.getDateAdded().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        JLabel dateLabel = new JLabel(dateStr);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        bottomPanel.add(dateLabel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(160, 180));
    }
}