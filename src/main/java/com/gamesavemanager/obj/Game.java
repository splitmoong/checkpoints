package com.gamesavemanager.obj;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.awt.image.BufferedImage;

public class Game implements Serializable {

    //variables
    private String id;
    private String name;
    private String publisher;
    private static final long serialVersionUID = 6185443264251524809L;

    //transient unserializable attributes
    private transient File localSaveFile;
    private transient Path localSaveFilePath;
    private transient BufferedImage image;
    private transient Path localImagePath;

    //string representations of paths for serialization
    private String localSaveFileString;
    private String localImagePathString;

    //google drive specific attributes
    private String googleDriveFolderId;
    private String googleDriveRootFolderId;

    //date-time added and synced
    private transient LocalDateTime dateAdded;
    private transient LocalDateTime lastSynced;
    private transient long lastLocalModificationTime;
    private String dateAddedString;
    private String lastSyncedString;

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
    public String getLocalSaveFileString() { return localSaveFileString; }
    public String getLocalImagePathString() { return localImagePathString; }

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
    public void setLocalSaveFileString(String localSaveFileString) { this.localSaveFileString = localSaveFileString; }
    public void setLocalImagePathString(String localImagePathString) { this.localImagePathString = localImagePathString; }

    public void setGoogleDriveFolderId(String googleDriveFolderId) { this.googleDriveFolderId = googleDriveFolderId; }
    public void setGoogleDriveRootFolderId(String googleDriveRootFolderId) { this.googleDriveRootFolderId = googleDriveRootFolderId; }


    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
        this.dateAddedString = dateAdded.toString();
    }
    public void setLastSynced(LocalDateTime lastSynced) {
        this.lastSynced = lastSynced;
        this.lastSyncedString = lastSynced.toString();
    }
    public void setLastLocalModificationTime(long lastLocalModificationTime) {
        this.lastLocalModificationTime = lastLocalModificationTime;
    }
}
