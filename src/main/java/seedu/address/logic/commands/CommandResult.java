package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.NoteCloseInstruction.CLOSE_NONE;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean shouldExit;

    /** Note should be shown to the user. */
    private final boolean showNote;
    /** Note should be deleted. */
    private final NoteCloseInstruction shouldDeleteNote;

    /** The person to show the note for. */
    private final Person targetPerson;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean shouldExit,
                         boolean showNote, NoteCloseInstruction shouldDeleteNote, Person targetPerson) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.shouldExit = shouldExit;
        this.showNote = showNote;
        this.shouldDeleteNote = shouldDeleteNote;
        this.targetPerson = targetPerson;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, CLOSE_NONE, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code showHelp}, {@code exit},
     * and {@code showNote}, and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean shouldExit,
                         boolean showNote, NoteCloseInstruction shouldDeleteNote) {
        this(feedbackToUser, showHelp, shouldExit, showNote, shouldDeleteNote, null);
    }

    public Person getTargetPerson() {
        return targetPerson;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return shouldExit;
    }

    public boolean isShowNote() {
        return showNote;
    }
    public NoteCloseInstruction shouldDeleteNote() {
        return shouldDeleteNote;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && shouldExit == otherCommandResult.shouldExit
                && showNote == otherCommandResult.showNote
                && shouldDeleteNote == otherCommandResult.shouldDeleteNote
                && Objects.equals(targetPerson, otherCommandResult.targetPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, shouldExit,
                showNote, shouldDeleteNote, targetPerson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", shouldExit)
                .add("showNote", showNote)
                .add("shouldDeleteNote", shouldDeleteNote)
                .toString();
    }

}
