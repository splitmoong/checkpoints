package com.gamesavemanager.storage;

import com.google.gson.Gson;

import com.gamesavemanager.obj.Game;
import java.io.*;

public class GameStorer {

    private static final String STORAGE_FILE = "src/main/java/com/gamesavemanager/storage/games.json";

    public static void saveGame(Game game) throws IOException {
        // Always reload from disk to avoid stale in-memory list
        java.util.List<Game> games = loadAllGames();
        System.out.println("[GameStorer] Games loaded before save: " + games.size());
        games.add(game);
        System.out.println("[GameStorer] Games to be saved: " + games.size());
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(STORAGE_FILE)) {
            gson.toJson(games, writer);
        }
    }

    public static java.util.List<Game> loadAllGames() {
        Gson gson = new Gson();
        java.util.List<Game> games = new java.util.ArrayList<>();
        File file = new File(STORAGE_FILE);
        if (!file.exists()) {
            System.out.println("[GameStorer] No storage file found.");
            return games;
        }
        try (Reader reader = new FileReader(STORAGE_FILE)) {
            System.out.println("[GameStorer] Loading games from storage file: " + STORAGE_FILE);
            Game[] gamesArray = gson.fromJson(reader, Game[].class);
            if (gamesArray != null) {
                for (Game game : gamesArray) {
                    System.out.println("[GameStorer] Loaded game: " + game.getName() + ", localImagePathString: " + game.getLocalImagePathString());
                    // Reconstruct image and path fields from saved strings
                    if (game.getLocalImagePathString() != null) {
                        java.nio.file.Path imgPath = java.nio.file.Paths.get(game.getLocalImagePathString());
                        game.setLocalImagePath(imgPath);
                        try {
                            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(imgPath.toFile());
                            game.setImage(img);
                            System.out.println("[GameStorer] Image loaded for game: " + game.getName());
                        } catch (Exception e) {
                            game.setImage(null);
                            System.out.println("[GameStorer] Failed to load image for game: " + game.getName() + ", error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("[GameStorer] No image path for game: " + game.getName());
                    }
                    games.add(game);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[GameStorer] Total games loaded: " + games.size());
        return games;
    }
}