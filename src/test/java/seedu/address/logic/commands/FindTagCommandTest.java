package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagNamesContainsTagsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagNamesContainsTagsPredicate firstPredicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("first"));
        TagNamesContainsTagsPredicate secondPredicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("second"));

        FindTagCommand findTagFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findTagSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findTagFirstCommand.equals(findTagFirstCommand));

        // same values -> returns true
        FindTagCommand findTagFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findTagFirstCommand.equals(findTagFirstCommandCopy));

        // different types -> returns false
        assertFalse(findTagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findTagFirstCommand.equals(null));

        // different tag list -> returns false
        assertFalse(findTagFirstCommand.equals(findTagSecondCommand));
    }

    @Test
    public void execute_oneTag_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("friends"));
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        List<Person> expectedPersons = Arrays.asList(ALICE, BENSON, DANIEL);
        assertEquals(expectedPersons, model.getFilteredPersonList());
    }

    @Test
    public void execute_oneTag_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("owesMoney"));
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneTag_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Collections.singletonList("nonExistentTag"));
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTags_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Arrays.asList("friends", "owesMoney"));
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTags_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagNamesContainsTagsPredicate predicate =
                new TagNamesContainsTagsPredicate(Arrays.asList("friends", "nonExistentTag"));
        FindTagCommand command = new FindTagCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        TagNamesContainsTagsPredicate predicate = new TagNamesContainsTagsPredicate(List.of("tags"));
        FindTagCommand findTagCommand = new FindTagCommand(predicate);
        String expected = FindTagCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findTagCommand.toString());
    }
}
