package seedu.address.storage;

import java.io.IOException;

import seedu.address.model.person.Person;

/**
 * Represents a storage for Notes.
 */
public interface NotesStorage {

    /**
     * Reads the note for a person.
     * @param person The person to read the note for.
     * @return The content of the note.
     * @throws IOException If an error occurs during reading.
     */
    String readNote(Person person) throws IOException;

    /**
     * Saves a note for a person.
     * @param person The person to save the note for.
     * @param content The content of the note.
     * @throws IOException If an error occurs during saving.
     */
    void saveNote(Person person, String content) throws IOException;

    /**
     * Deletes the note for a person if it exists.
     * @param person The person to delete the note for.
     * @return true if the note was found and successfully deleted, false if no note was found.
     * @throws IOException If an error occurs during deletion.
     */
    boolean deleteNote(Person person) throws IOException;

    /**
     * Deletes all notes.
     * @throws IOException If an error occurs during deletion.
     */
    void deleteAllNotes() throws IOException;
}
