package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds one or more tags to a person in the address book.
 */
public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to a person in the address book. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_TAG + "TAG (need not be a single word) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "Tag added to person: %1$s";

    public static final String MESSAGE_EMPTY_TAG = "Tags cannot be empty";

    private final Index index;
    private final PersonDescriptor personDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param personDescriptor details to edit the person with
     */
    public AddTagCommand(Index targetIndex, PersonDescriptor personDescriptor) {
        this.index = targetIndex;
        this.personDescriptor = personDescriptor;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddTag = lastShownList.get(index.getZeroBased());
        Person personWithTags = createPersonWithAddedTags(personToAddTag, personDescriptor);

        model.setPerson(personToAddTag, personWithTags);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personWithTags)));
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddTag}
     * edited with {@code personDescriptor}.
     */
    private static Person createPersonWithAddedTags(Person personToAddTag,
                                                    PersonDescriptor personDescriptor) {
        assert personToAddTag != null;

        Name updatedName = personToAddTag.getName();
        Phone updatedPhone = personToAddTag.getPhone();
        Email updatedEmail = personToAddTag.getEmail();
        Address updatedAddress = personToAddTag.getAddress();
        // gets current tags for personToAddTag
        Set<Tag> updatedTags = new HashSet<>(personToAddTag.getTags());
        // add new tags from personDescriptor
        updatedTags.addAll(personDescriptor.getTags().orElse(Collections.emptySet()));
        PersonId personId = personToAddTag.getId();

        return new Person(
                updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, personId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand otherAddTagCommand = (AddTagCommand) other;
        return index.equals(otherAddTagCommand.index)
                && personDescriptor.equals(otherAddTagCommand.personDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("personDescriptor", personDescriptor)
                .toString();
    }
}

