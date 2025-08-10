package com.gamesavemanager.vcs;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class VCSManager {

    // A central location to store all your Git repos
    // Example: C:\Users\YourUser\AppData\Local\GameSaveManager\repos
    private static final Path reposRoot = Paths.get(System.getProperty("user.home"), "AppData", "Local", "GameSaveManager", "repos");

    /**
     * Initializes a new Git repository for a game.
     * This should be called when a new game is added.
     * @param gameId A unique identifier for the game (e.g., "cyberpunk-2077")
     * @param originalSavePath The path to the game's actual save file/directory.
     * @return The path to the newly created Git repository.
     */
    public static Path initRepository(String gameId, Path originalSavePath) throws IOException, GitAPIException {
        Path repoPath = reposRoot.resolve(gameId);
        Files.createDirectories(repoPath);

        // 1. Initialize the Git repository
        Git git = Git.init().setDirectory(repoPath.toFile()).call();

        // 2. Copy the save file(s) into the new repo
        copyToRepo(originalSavePath, repoPath);

        // 3. Create the first commit
        git.add().addFilepattern(".").call();
        git.commit().setMessage("Initial save state").call();
        git.close();

        System.out.println("Initialized Git repo for " + gameId + " at " + repoPath);
        return repoPath;
    }

    /**
     * Creates a new "checkpoint" (a commit) for a game's save file.
     * @param gameId The unique identifier for the game.
     * @param originalSavePath The path to the game's actual save file/directory.
     * @param commitMessage A message describing the checkpoint.
     */
    public static void createCheckpoint(String gameId, Path originalSavePath, String commitMessage) throws IOException, GitAPIException {
        Path repoPath = reposRoot.resolve(gameId);
        Git git = Git.open(repoPath.toFile());

        // 1. Copy the latest save files into our Git repo, overwriting old ones
        copyToRepo(originalSavePath, repoPath);

        // 2. Stage the changes
        git.add().addFilepattern(".").call();

        // 3. Commit the changes
        git.commit().setMessage(commitMessage).call();
        git.close();
        System.out.println("Created new checkpoint for " + gameId);
    }

    /**
     * Retrieves the commit history for a game's repository.
     * @param gameId The unique identifier for the game.
     * @return A list of commits (RevCommit objects).
     */
    public static List<RevCommit> getCommitHistory(String gameId) throws IOException, GitAPIException {
        Path repoPath = reposRoot.resolve(gameId);
        Git git = Git.open(repoPath.toFile());

        Iterable<RevCommit> logs = git.log().all().call();
        List<RevCommit> history = new ArrayList<>();
        logs.forEach(history::add);

        git.close();
        return history;
    }

    /**
     * Restores a game's save file to a specific checkpoint.
     * @param gameId The unique identifier for the game.
     * @param commitId The hash ID of the commit to restore.
     * @param destinationPath The original game save location to copy the files back to.
     */
    public static void restoreCheckpoint(String gameId, String commitId, Path destinationPath) throws IOException, GitAPIException {
        Path repoPath = reposRoot.resolve(gameId);
        Git git = Git.open(repoPath.toFile());

        // 1. Checkout the specific commit. This reverts the files IN OUR REPO.
        git.checkout().setName(commitId).call();
        git.close();

        // 2. Copy the restored files from our repo back to the game's save directory.
        // WARNING: This overwrites the user's current save file. You MUST warn them first!
        copyFromRepo(repoPath, destinationPath);
        System.out.println("Restored " + gameId + " to commit " + commitId);
    }


    // --- Helper Methods for copying files ---

    private static void copyToRepo(Path source, Path destination) throws IOException {
        // This is a simplified copy. For directories, you'll need a recursive copy.
        // Apache Commons IO's FileUtils.copyDirectory() is great for this.
        File sourceFile = source.toFile();
        if (sourceFile.isDirectory()) {
            // Implement recursive copy for directories
            throw new UnsupportedOperationException("Directory copy not implemented yet.");
        } else {
            Files.copy(source, destination.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void copyFromRepo(Path sourceRepo, Path destination) throws IOException {
        // Similar to copyToRepo, this needs to handle directories.
        // This implementation is simplified for a single file.
        File[] filesInRepo = sourceRepo.toFile().listFiles(file -> !file.getName().equals(".git"));
        if (filesInRepo != null && filesInRepo.length > 0) {
            // Assuming the first file is the save file. This needs to be more robust.
            Path sourceFile = filesInRepo[0].toPath();
            Files.copy(sourceFile, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
