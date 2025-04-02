package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * Deletes the note tagged to the contact identified using it's displayed index from the address book.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "deletenote";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETENOTE_SUCCESS = "Note deleted for: %1$s";
    public static final String MESSAGE_NO_NOTE = "No note found for: %1$s";
    private final Index targetIndex;
    private Storage storage;
    private Person targetPerson;

    public DeleteNoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDeleteNote = lastShownList.get(targetIndex.getZeroBased());
        this.targetPerson = personToDeleteNote;

        boolean deleted;
        try {
            deleted = storage.deleteNote(targetPerson);
        } catch (IOException e) {
            throw new CommandException("Failed to delete note: " + e.getMessage(), e);
        }

        String message = deleted
                ? String.format(MESSAGE_DELETENOTE_SUCCESS, Messages.format(personToDeleteNote))
                : String.format(MESSAGE_NO_NOTE, Messages.format(personToDeleteNote));

        return new CommandResult(message, false, false, true,
                deleted ? NoteDeleteInstruction.DELETE_ONE : NoteDeleteInstruction.DELETE_NONE, personToDeleteNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DeleteNoteCommand)) {
            return false;
        }

        DeleteNoteCommand otherDeleteNoteCommand = (DeleteNoteCommand) other;
        return targetIndex.equals(otherDeleteNoteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Returns the target person to be deleted.
     */
    public Person getTargetPerson() {
        return targetPerson;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
