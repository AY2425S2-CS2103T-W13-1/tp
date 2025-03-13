package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTagCommand.AddTagPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddTagPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        // execute with valid index and tags
        Person currentPerson = model.getFilteredPersonList().get(0);
        Person editedPerson = new PersonBuilder(currentPerson).withTags("colleague", "friends").build();
        AddTagPersonDescriptor descriptor = new AddTagPersonDescriptorBuilder(editedPerson).build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(currentPerson, editedPerson);
        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Person personForDescriptor = new PersonBuilder().build();
        AddTagPersonDescriptor descriptor = new AddTagPersonDescriptorBuilder(personForDescriptor).build();
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals_testAllFields() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddTagPersonDescriptor descriptor = new AddTagPersonDescriptorBuilder(firstPerson).build();
        Person firstPersonOnListDifferentTags = new PersonBuilder().withTags("tester").build();
        AddTagPersonDescriptor descriptor2 = new AddTagPersonDescriptorBuilder(firstPersonOnListDifferentTags)
                .build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, descriptor);
        AddTagCommand addTagCommandCopy = new AddTagCommand(INDEX_FIRST_PERSON, descriptor);
        AddTagCommand addTagCommandDifferentIndex = new AddTagCommand(Index.fromOneBased(2), descriptor);
        AddTagCommand addTagCommandDifferentDescriptor = new AddTagCommand(INDEX_FIRST_PERSON,
                descriptor2);
        // same object -> returns true
        assert(addTagCommand.equals(addTagCommand));
        // same values -> returns true
        assert(addTagCommand.equals(addTagCommandCopy));
        // different types -> returns false
        assert(!addTagCommand.equals(1));
        // null -> returns false
        assert(!addTagCommand.equals(null));
        // different index -> returns false
        assert(!addTagCommand.equals(addTagCommandDifferentIndex));
        // different descriptor -> returns false
        assert(!addTagCommand.equals(addTagCommandDifferentDescriptor));
    }

    @Test
    public void toString_test() {
        Index index = Index.fromOneBased(1);
        AddTagPersonDescriptor addTagPersonDescriptor = new AddTagPersonDescriptor();
        AddTagCommand addTagCommand = new AddTagCommand(index, addTagPersonDescriptor);
        String expected = AddTagCommand.class.getCanonicalName() + "{index=" + index + ", addTagPersonDescriptor="
                + addTagPersonDescriptor + "}";
        assertEquals(expected, addTagCommand.toString());
    }
}
