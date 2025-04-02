package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false,
                false, NoteDeleteInstruction.DELETE_NONE)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false,
                false, NoteDeleteInstruction.DELETE_NONE)));
        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true,
                false, NoteDeleteInstruction.DELETE_NONE)));
        // different showNote value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                true, NoteDeleteInstruction.DELETE_NONE)));
        // different shouldDeleteNote value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                false, NoteDeleteInstruction.DELETE_ALL)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true,
                false, false, NoteDeleteInstruction.DELETE_NONE).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                true, false, NoteDeleteInstruction.DELETE_NONE).hashCode());

        // different showNote value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, true, NoteDeleteInstruction.DELETE_NONE).hashCode());
        // different shouldDeleteNote value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, false, NoteDeleteInstruction.DELETE_ALL).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit()
                + ", showNote=" + commandResult.isShowNote()
                + ", shouldDeleteNote=" + commandResult.shouldDeleteNote() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void equals_differentTargetPerson_returnsFalse() {
        Person person1 = new PersonBuilder().withName("Alice").build();
        Person person2 = new PersonBuilder().withName("Bob").build();
        CommandResult commandResult = new CommandResult("feedback", false, false,
                true, NoteDeleteInstruction.DELETE_NONE, person1);

        // different targetPerson -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                true, NoteDeleteInstruction.DELETE_NONE, person2)));
    }

    @Test
    public void getTargetPerson_returnsCorrectPerson() {
        Person person = new PersonBuilder().withName("Alice").build();
        CommandResult commandResult = new CommandResult("feedback", false, false,
                true, NoteDeleteInstruction.DELETE_NONE, person);
        assertEquals(person, commandResult.getTargetPerson());
    }
}
