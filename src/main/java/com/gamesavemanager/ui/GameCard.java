package com.gamesavemanager.ui;

import com.gamesavemanager.obj.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GameCard extends JPanel {
    public GameCard(Game game) {
        setLayout(new BorderLayout());
        setOpaque(false); // use custom paint
        setPreferredSize(new Dimension(160, 210));

        // Container panel with background color and rounded shape
        JPanel container = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();
                int arc = 20;
                int notch = 20;

                // create a rounded rect with a bottom-right corner cut
                GeneralPath path = new GeneralPath();
                path.moveTo(0, arc);
                path.quadTo(0, 0, arc, 0);
                path.lineTo(w - arc, 0);
                path.quadTo(w, 0, w, arc);
                path.lineTo(w, h - notch);
                path.lineTo(w - notch, h);
                path.lineTo(arc, h);
                path.quadTo(0, h, 0, h - arc);
                path.closePath();

                g2.setColor(new Color(24, 24, 24, 240));
                g2.fill(path);
                g2.setColor(new Color(189, 189, 189, 152));
                g2.draw(path);
                g2.dispose();
            }
        };
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(12, 12, 12, 12));
        add(container);

        // Load image
        final BufferedImage img;
        Path imgPath = game.getLocalImagePath();
        BufferedImage loadedImg = null;
        if (imgPath != null && Files.exists(imgPath)) {
            try {
                loadedImg = ImageIO.read(imgPath.toFile());
            } catch (IOException ex) {
                System.out.println("[GameCard] Failed to load image: " + ex.getMessage());
            }
        }
        img = loadedImg;

        JPanel roundedImagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.setClip(clip);
                    g2.drawImage(img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, getWidth(), getHeight(), null);
                    g2.dispose();
                }
            }
        };
        roundedImagePanel.setPreferredSize(new Dimension(120, 120));
        roundedImagePanel.setOpaque(false);

        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        imagePanel.add(roundedImagePanel);

        container.add(imagePanel, BorderLayout.CENTER);

        // Bottom panel for name and date
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(8, 5, 0, 5));

        JTextArea nameArea = new JTextArea(game.getName());
        nameArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nameArea.setWrapStyleWord(true);
        nameArea.setLineWrap(true);
        nameArea.setOpaque(false);
        nameArea.setEditable(false);
        nameArea.setFocusable(false);
        nameArea.setForeground(new Color(33, 33, 33));
        nameArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameArea.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));

        bottomPanel.add(nameArea, BorderLayout.CENTER);

        container.add(bottomPanel, BorderLayout.SOUTH);
    }
}