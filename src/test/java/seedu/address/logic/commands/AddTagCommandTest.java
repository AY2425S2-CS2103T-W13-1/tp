package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.PersonDescriptor;
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
        PersonDescriptor descriptor = new AddTagPersonDescriptorBuilder(editedPerson).build();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, descriptor);
        String expectedMessage = String.format(AddTagCommand.MESSAGE_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(currentPerson, editedPerson);
        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }
}
