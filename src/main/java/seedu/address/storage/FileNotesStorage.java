package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * A class to manage notes storage in the file system.
 */
public class FileNotesStorage implements NotesStorage {
    private static final Logger logger = LogsCenter.getLogger(FileNotesStorage.class);
    private final Path notesDir;

    /**
     * Creates a new FileNotesStorage.
     *
     * @param notesDir The directory to store notes in
     */
    public FileNotesStorage(Path notesDir) {
        this.notesDir = notesDir;
        createNotesDirectory();
    }

    private void createNotesDirectory() {
        try {
            Files.createDirectories(notesDir);
        } catch (IOException e) {
            logger.warning("Failed to create notes directory: " + e.getMessage());
        }
    }

    private Path getNoteFilePath(Person person) {
        return notesDir.resolve(person.getId().value + ".txt");
    }

    @Override
    public String readNote(Person person) throws IOException {
        Path notePath = getNoteFilePath(person);
        if (Files.exists(notePath)) {
            return Files.readString(notePath);
        }
        return "";
    }

    @Override
    public void saveNote(Person person, String content) throws IOException {
        Files.createDirectories(notesDir);
        Files.writeString(getNoteFilePath(person), content);
    }

    @Override
    public boolean deleteNote(Person person) throws IOException {
        Path notePath = getNoteFilePath(person);
        if (Files.exists(notePath)) {
            Files.delete(notePath);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllNotes() throws IOException {
        if (Files.exists(notesDir)) {
            Files.list(notesDir).filter(path -> path.toString().endsWith(".txt")).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    logger.warning("Failed to delete note: " + e.getMessage());
                }
            });
        }
    }
}
