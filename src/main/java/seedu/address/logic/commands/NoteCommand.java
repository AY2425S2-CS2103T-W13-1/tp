package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Creates a .txt file tagged to contact identified using it's displayed index from the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a note to the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_NOTE_PERSON_SUCCESS = "Viewing note for: %1$s";

    private final Index targetIndex;
    public NoteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToAddNote = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_NOTE_PERSON_SUCCESS, Messages.format(personToAddNote)),
                false, false, true, NoteDeleteInstruction.DELETE_NONE, personToAddNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteCommand)) {
            return false;
        }

        NoteCommand otherNoteCommand = (NoteCommand) other;
        return targetIndex.equals(otherNoteCommand.targetIndex);
    }
}
