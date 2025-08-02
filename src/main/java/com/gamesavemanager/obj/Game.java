package main.java.com.gamesavemanager.obj;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.awt.image.BufferedImage;

public class Game {

    //variables
    private String id;
    private String name;
    private String publisher;

    //save file file, save file path,
    //image file and image path.
    private File localSaveFile;
    private Path localSaveFilePath;
    private BufferedImage image;
    private Path localImagePath;

    // Google Drive specific attributes
    private String googleDriveFolderId;
    private String googleDriveRootFolderId;

    //save file metadata
    private LocalDateTime dateAdded;
    private LocalDateTime lastSynced;
    private long lastLocalModificationTime;


    // Constructor
    public Game(String id, String name, String publisher, File localSaveFile, Path localImagePath) {
        this.id = id; // You'd typically generate this (e.g., UUID.randomUUID().toString())
        this.name = name;
        this.publisher = publisher;
        this.localSaveFile = localSaveFile;
        this.localImagePath = localImagePath;
        this.dateAdded = LocalDateTime.now();
        // Initialize other fields as null or default
        this.lastLocalModificationTime = 0; // Or whatever is appropriate
    }

    //getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPublisher() { return publisher; }

    public Path getLocalSaveFilePath() { return localSaveFilePath; }
    public File getLocalSaveFile() { return localSaveFile; }
    public Path getLocalImagePath() { return localImagePath; }
    public BufferedImage getImage() { return image; }

    public String getGoogleDriveFolderId() { return googleDriveFolderId; }
    public String getGoogleDriveRootFolderId() { return googleDriveRootFolderId; }
    public LocalDateTime getDateAdded() { return dateAdded; }
    public LocalDateTime getLastSynced() { return lastSynced; }
    public long getLastLocalModificationTime() { return lastLocalModificationTime; }

    //SETTERS
    //setters for id, name, and publisher
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    //setters for local file path, local save file, and local image path
    public void setLocalSaveFilePath(Path localSaveFilePath) { this.localSaveFilePath = localSaveFilePath; }
    public void setLocalSaveFile(File localSaveFile) { this.localSaveFile = localSaveFile; }
    public void setLocalImagePath(Path localImagePath) { this.localImagePath = localImagePath; }
    public void setImage(BufferedImage image) { this.image = image; }

    public void setGoogleDriveFolderId(String googleDriveFolderId) { this.googleDriveFolderId = googleDriveFolderId; }
    public void setGoogleDriveRootFolderId(String googleDriveRootFolderId) { this.googleDriveRootFolderId = googleDriveRootFolderId; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }
    public void setLastSynced(LocalDateTime lastSynced) { this.lastSynced = lastSynced; }
    public void setLastLocalModificationTime(long lastLocalModificationTime) { this.lastLocalModificationTime = lastLocalModificationTime; }
}
