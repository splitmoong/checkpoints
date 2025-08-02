package main.java.com.gamesavemanager.ui;
//game object class
import main.java.com.gamesavemanager.obj.Game;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;

public class AddGameWindow {
    private JPanel mainWindow;
    private JPanel centerPanel;
    private JLabel gameImage;
    private JButton btnCancel;
    private JButton btnAddGame;

    private File selectedSaveFile;
    private Path selectedSaveFilePath;
    private Path selectedImagePath;

    private GameListener listener;

    public AddGameWindow(GameListener listener) {
        this.listener = listener;

        Game game = new Game("", "", "", null, null);


        mainWindow = new JPanel(new GridLayout(1, 2, 10, 0));
        mainWindow.setPreferredSize(new Dimension(670, 340));
        mainWindow.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));


        // LEFT PANEL
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, 350));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        JPanel imageContainer = new JPanel(new GridBagLayout());
        JLabel placeholderLabel = new JLabel("No Image Loaded");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeholderLabel.setVerticalAlignment(SwingConstants.CENTER);
        placeholderLabel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));
        placeholderLabel.setPreferredSize(new Dimension(300, 300));

        JButton pickImageButton = new JButton("Pick Image");
        JTextField imagePathTextField = new JTextField();
        imagePathTextField.setEditable(false);
        imagePathTextField.setVisible(false);

        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.insets = new Insets(10, 10, 10, 10);
        imageContainer.add(placeholderLabel, leftGbc);

        leftGbc.gridy = 1;
        imageContainer.add(pickImageButton, leftGbc);

        leftPanel.add(imageContainer, BorderLayout.CENTER);
        leftPanel.add(imagePathTextField, BorderLayout.SOUTH);

        // RIGHT PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Row 1: Save file picker
        JPanel filePickerPanel = new JPanel(new BorderLayout());
        JButton pickSaveFileButton = new JButton("Pick Save File");
        filePickerPanel.add(pickSaveFileButton, BorderLayout.CENTER);

        pickSaveFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedSaveFile = fileChooser.getSelectedFile();
                selectedSaveFilePath = selectedSaveFile.toPath();
                System.out.println("Selected Save File: " + selectedSaveFile.getAbsolutePath());
                pickSaveFileButton.setText("Save File Selected");
                pickSaveFileButton.setForeground(new Color(82, 107, 77));                // You can store or use the file as needed
                // You can store or use the file as needed

            }
        });

        // Row 2: Game name entry
        JPanel namePanel = new JPanel(new BorderLayout());
        JTextArea gameNameArea = new JTextArea(2, 20);
        gameNameArea.setBorder(BorderFactory.createTitledBorder("Game Name"));
        namePanel.add(gameNameArea, BorderLayout.CENTER);

        // Row 3: Metadata table
        String[] columnNames = {"Field", "Value", "Edit"};
        Object[][] data = {{"Genre", "", "✏️"}, {"Developer", "", "✏️"}, {"Release Date", "", "✏️"}};
        JTable metadataTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(metadataTable);

        // Row 4: Buttons
        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancel = new JButton("Cancel");
        btnAddGame = new JButton("Save Changes");
        actionButtons.add(btnCancel);
        actionButtons.add(btnAddGame);

        btnAddGame.addActionListener(e -> {
            game.setName(gameNameArea.getText());
            if (selectedSaveFile != null) {
                game.setLocalSaveFile(selectedSaveFile);
                game.setLocalSaveFilePath(selectedSaveFilePath);
                game.setLocalSaveFileString(selectedSaveFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Please select a save file before saving.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            game.setName(gameNameArea.getText());
            System.out.println("Game updated: " + game.getName());

            if (listener != null) {
                listener.onGameAdded(game);
            }
            // Close the window
            SwingUtilities.getWindowAncestor(mainWindow).dispose();
        });


        // Add all right rows
        rightPanel.add(filePickerPanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(namePanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(tableScrollPane);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(actionButtons);

        // Add panels to main
        mainWindow.add(leftPanel);
        mainWindow.add(rightPanel);

        // Image picker logic
        pickImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File imageFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage img = ImageIO.read(imageFile);
                    if (img == null) {
                        JOptionPane.showMessageDialog(mainWindow, "Selected file is not a supported image.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int width = img.getWidth();
                    int height = img.getHeight();
                    if (width != height) {
                        JOptionPane.showMessageDialog(mainWindow, "Image must be a perfect square (width = height).", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //update the game object with the image file
                    game.setLocalImagePath(imageFile.toPath());
                    game.setLocalImagePathString(imageFile.getAbsolutePath());
                    game.setImage(ImageIO.read(imageFile));

                    imagePathTextField.setText(imageFile.getAbsolutePath());
                    imagePathTextField.setVisible(true);
                    Image scaledImage = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    placeholderLabel.setIcon(new ImageIcon(scaledImage));
                    placeholderLabel.setText("");
                    placeholderLabel.setBorder(null);
                    imageContainer.remove(pickImageButton);
                    imageContainer.revalidate();
                    imageContainer.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainWindow, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        placeholderLabel.setText("Drop Image Here or Use Button");

        imageContainer.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) return false;
                try {
                    java.util.List<File> files = (java.util.List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        File imageFile = files.get(0);
                        imagePathTextField.setText(imageFile.getAbsolutePath());
                        imagePathTextField.setVisible(true);
                        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                        Image scaledImage = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                        placeholderLabel.setIcon(new ImageIcon(scaledImage));
                        placeholderLabel.setText("");
                        placeholderLabel.setBorder(null);
                        imageContainer.remove(pickImageButton);
                        imageContainer.revalidate();
                        imageContainer.repaint();
                        return true;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
    }

    public void showWindow() {
        JFrame frame = new JFrame("Add Game");
        frame.setContentPane(mainWindow);  // this gets the root JPanel from the form
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes only this window
        frame.pack();
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }
}