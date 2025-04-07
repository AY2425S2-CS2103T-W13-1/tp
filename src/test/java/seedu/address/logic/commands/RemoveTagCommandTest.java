package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonDescriptorBuilder;

public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        model.setAddressBook(new AddressBook()); // Clear previous data

        // Create person with tags "collegaue" and "friends"
        Person currentPerson = new PersonBuilder().withName("removeTagTestAllFields")
                .withTags("colleague", "friends").build();
        model.addPerson(currentPerson);

        Person editedPerson = new PersonBuilder(currentPerson).withTags("friends").build();

        // Descriptor that specifies which tag to remove
        PersonDescriptor descriptor = new PersonDescriptorBuilder().withTags("colleague").build();

        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS,
                Messages.format(editedPerson), "[colleague]");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(currentPerson, editedPerson);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Person personForDescriptor = new PersonBuilder().build();
        PersonDescriptor descriptor = new PersonDescriptorBuilder(personForDescriptor).build();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() {
        model.setAddressBook(new AddressBook()); // Clear previous data

        // Create person with tags "collegaue" and "friends"
        Person currentPerson = new PersonBuilder().withName("removeTagTestAllFields")
                .withTags("colleague", "friends").build();
        model.addPerson(currentPerson);

        PersonDescriptor descriptor = new PersonDescriptorBuilder().withTags("nonExistentTag").build();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);
    }

    @Test
    public void equals_testAllFields() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PersonDescriptor descriptor = new PersonDescriptorBuilder(firstPerson).build();
        Person firstPersonOnListDifferentTags = new PersonBuilder().withTags("tester").build();
        PersonDescriptor descriptor2 = new PersonDescriptorBuilder(firstPersonOnListDifferentTags)
                .build();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(INDEX_FIRST_PERSON, descriptor);
        RemoveTagCommand removeTagCommandCopy = new RemoveTagCommand(INDEX_FIRST_PERSON, descriptor);
        RemoveTagCommand removeTagCommandDifferentIndex = new RemoveTagCommand(Index.fromOneBased(2), descriptor);
        RemoveTagCommand removeTagCommandDifferentDescriptor = new RemoveTagCommand(INDEX_FIRST_PERSON,
                descriptor2);
        // same object -> returns true
        assertTrue(removeTagCommand.equals(removeTagCommand));
        // same values -> returns true
        assertTrue(removeTagCommand.equals(removeTagCommandCopy));
        // different types -> returns false
        assertFalse(removeTagCommand.equals(1));
        // null -> returns false
        assertFalse(removeTagCommand.equals(null));
        // different index -> returns false
        assertFalse(removeTagCommand.equals(removeTagCommandDifferentIndex));
        // different descriptor -> returns false
        assertFalse(removeTagCommand.equals(removeTagCommandDifferentDescriptor));
    }

    @Test
    public void toString_test() {
        Index index = Index.fromOneBased(1);
        PersonDescriptor descriptor = new PersonDescriptor();
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, descriptor);
        String expected = RemoveTagCommand.class.getCanonicalName() + "{targetIndex=" + index + ", personDescriptor="
                + descriptor + "}";
        assertEquals(expected, removeTagCommand.toString());
    }
}
