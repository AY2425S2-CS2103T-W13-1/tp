package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FileNotesStorageTest {

    @TempDir
    public Path testFolder;

    private FileNotesStorage notesStorage;
    private Person testPerson;

    @BeforeEach
    public void setUp() {
        notesStorage = new FileNotesStorage(testFolder);
        testPerson = new PersonBuilder().withName("Test Person").withPersonId("123").build();
    }

    @Test
    public void readNote_nonExistentNote_returnsEmptyString() throws IOException {
        assertEquals("", notesStorage.readNote(testPerson));
    }

    @Test
    public void saveAndReadNote_validInput_success() throws IOException {
        String testContent = "This is a test note";
        notesStorage.saveNote(testPerson, testContent);

        // Check if the file exists
        File noteFile = new File(testFolder.toString(), "123.txt");
        assertTrue(noteFile.exists());

        // Verify content
        String readContent = notesStorage.readNote(testPerson);
        assertEquals(testContent, readContent);
    }

    @Test
    public void deleteNote_existingNote_success() throws IOException {
        // Create a note first
        notesStorage.saveNote(testPerson, "Note to delete");
        File noteFile = new File(testFolder.toString(), "123.txt");
        assertTrue(noteFile.exists());

        // Delete the note
        notesStorage.deleteNote(testPerson);
        assertFalse(noteFile.exists());
    }

    @Test
    public void deleteAllNotes_multipleNotes_success() throws IOException {
        // Create multiple notes
        Person person1 = new PersonBuilder().withPersonId("1").build();
        Person person2 = new PersonBuilder().withPersonId("2").build();

        notesStorage.saveNote(person1, "Note 1");
        notesStorage.saveNote(person2, "Note 2");

        // Verify files exist
        assertTrue(new File(testFolder.toString(), "1.txt").exists());
        assertTrue(new File(testFolder.toString(), "2.txt").exists());

        // Delete all notes
        notesStorage.deleteAllNotes();

        // Verify files are deleted
        assertFalse(new File(testFolder.toString(), "1.txt").exists());
        assertFalse(new File(testFolder.toString(), "2.txt").exists());
    }
}
