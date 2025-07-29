package main.java.com.gamesavemanager.obj;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Game {

    //variables
    private String id;
    private String name;
    private String publisher;
    private Path localSaveDirectory;

    private Path localImagePath;

    // Google Drive specific attributes
    private String googleDriveFolderId;
    private String googleDriveRootFolderId;

    //save file metadata
    private LocalDateTime dateAdded;
    private LocalDateTime lastSynced;
    private long lastLocalModificationTime;


    // Constructor
    public Game(String id, String name, String publisher, Path localSaveDirectory, Path localImagePath) {
        this.id = id; // You'd typically generate this (e.g., UUID.randomUUID().toString())
        this.name = name;
        this.publisher = publisher;
        this.localSaveDirectory = localSaveDirectory;
        this.localImagePath = localImagePath;
        this.dateAdded = LocalDateTime.now();
        // Initialize other fields as null or default
        this.lastLocalModificationTime = 0; // Or whatever is appropriate
    }

    //getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPublisher() { return publisher; }
    public Path getLocalSaveDirectory() { return localSaveDirectory; }
    public Path getLocalImagePath() { return localImagePath; }
    public String getGoogleDriveFolderId() { return googleDriveFolderId; }
    public String getGoogleDriveRootFolderId() { return googleDriveRootFolderId; }
    public LocalDateTime getDateAdded() { return dateAdded; }
    public LocalDateTime getLastSynced() { return lastSynced; }
    public long getLastLocalModificationTime() { return lastLocalModificationTime; }

    //setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setLocalSaveDirectory(Path localSaveDirectory) { this.localSaveDirectory = localSaveDirectory; }
    public void setLocalImagePath(Path localImagePath) { this.localImagePath = localImagePath; }
    public void setGoogleDriveFolderId(String googleDriveFolderId) { this.googleDriveFolderId = googleDriveFolderId; }
    public void setGoogleDriveRootFolderId(String googleDriveRootFolderId) { this.googleDriveRootFolderId = googleDriveRootFolderId; }
    public void setDateAdded(LocalDateTime dateAdded) { this.dateAdded = dateAdded; }
    public void setLastSynced(LocalDateTime lastSynced) { this.lastSynced = lastSynced; }
    public void setLastLocalModificationTime(long lastLocalModificationTime) { this.lastLocalModificationTime = lastLocalModificationTime; }
}
