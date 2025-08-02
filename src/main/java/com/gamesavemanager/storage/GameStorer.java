package main.java.com.gamesavemanager.storage;

import main.java.com.gamesavemanager.obj.Game;
import java.io.*;

public class GameStorer {
    private static final String STORAGE_FILE = "games.dat";

    public static void saveGame(Game game) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(STORAGE_FILE, true))) {
            oos.writeObject(game);
        }
    }

    // Optional: Load all games
    public static java.util.List<Game> loadGames() throws IOException, ClassNotFoundException {
        java.util.List<Game> games = new java.util.ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            while (true) {
                try {
                    games.add((Game) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return games;
    }
}