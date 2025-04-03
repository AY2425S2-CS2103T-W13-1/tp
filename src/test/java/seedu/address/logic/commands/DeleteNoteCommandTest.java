package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

public class DeleteNoteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deleteNote_success() {
        Person personToDeleteNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        deleteNoteCommand.setStorage(new StorageStub(true));

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_DELETENOTE_SUCCESS,
                Messages.format(personToDeleteNote));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                false, NoteCloseInstruction.CLOSE_ONE, personToDeleteNote);

        assertCommandSuccess(deleteNoteCommand, model, expectedCommandResult, model);
        assertEquals(personToDeleteNote, deleteNoteCommand.getTargetPerson());
    }

    @Test
    public void execute_deleteNote_noNoteFound() {
        Person personToDeleteNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        deleteNoteCommand.setStorage(new StorageStub(false));

        String expectedMessage = String.format(DeleteNoteCommand.MESSAGE_NO_NOTE,
                Messages.format(personToDeleteNote));

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                false, NoteCloseInstruction.CLOSE_ONE, personToDeleteNote);

        assertCommandSuccess(deleteNoteCommand, model, expectedCommandResult, model);
        assertEquals(personToDeleteNote, deleteNoteCommand.getTargetPerson());
    }
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeleteNoteCommand command = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        command.setStorage(new StorageStub(true));

        Model emptyModel = new ModelManager();

        assertCommandFailure(command, emptyModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        DeleteNoteCommand deleteNoteFirstCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        DeleteNoteCommand deleteNoteSecondCommand = new DeleteNoteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteNoteFirstCommand.equals(deleteNoteFirstCommand));

        // same values -> returns true
        DeleteNoteCommand deleteNoteFirstCommandCopy = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteNoteFirstCommand.equals(deleteNoteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteNoteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteNoteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteNoteFirstCommand.equals(deleteNoteSecondCommand));
    }
    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteNoteCommand deleteNoteCommand = new DeleteNoteCommand(index);
        String expected = DeleteNoteCommand.class.getCanonicalName() + "{targetIndex=" + index + "}";
        assertEquals(expected, deleteNoteCommand.toString());
    }

    private static class StorageStub implements Storage {
        private final boolean shouldDelete;

        StorageStub(boolean shouldDelete) {
            this.shouldDelete = shouldDelete;
        }
        @Override
        public boolean deleteNote(Person person) {
            return shouldDelete;
        }
        // Throw for unimplemented methods to catch accidental calls
        @Override
        public String readNote(Person person) throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public void saveNote(Person person, String content) throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public void deleteAllNotes() throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public Path getUserPrefsFilePath() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Optional<UserPrefs> readUserPrefs() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public Path getAddressBookFilePath() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
            throw new UnsupportedOperationException();
        }
        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new UnsupportedOperationException();
        }
    }
}
