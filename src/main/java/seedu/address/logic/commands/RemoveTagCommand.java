package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Removes specified tags from a person in the address book.
 */
public class RemoveTagCommand extends Command {

    public static final String COMMAND_WORD = "removetag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag(s) from the person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer) t/TAG [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 t/Friend t/Colleague";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tags removed for person: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "Some of the specified tags do not exist for this person.";

    private final Index targetIndex;
    private final PersonDescriptor personDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param personDescriptor details to edit the person with
     */
    public RemoveTagCommand(Index targetIndex, PersonDescriptor personDescriptor) {
        this.targetIndex = targetIndex;
        this.personDescriptor = new PersonDescriptor(personDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRemoveTag = lastShownList.get(targetIndex.getZeroBased());
        Person removedTagPerson = createRemovedTagPerson(personToRemoveTag, personDescriptor);
        if (removedTagPerson.getTags().equals(personToRemoveTag.getTags())) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
        model.setPerson(personToRemoveTag, removedTagPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(removedTagPerson)));
    }

    /**
     * Creates and returns a {@code Person}, removing from {@code personToRemoveTag}
     * only the tags specified in {@code personDescriptor}.
     */
    private static Person createRemovedTagPerson(Person personToRemoveTag,
                                                 PersonDescriptor personDescriptor) {
        assert personToRemoveTag != null;

        Name name = personToRemoveTag.getName();
        Phone phone = personToRemoveTag.getPhone();
        Email email = personToRemoveTag.getEmail();
        Address address = personToRemoveTag.getAddress();
        // Start with the existing set of Tags
        Set<Tag> existingTags = new HashSet<>(personToRemoveTag.getTags());

        //Remove specified tags
        Optional<Set<Tag>> tagsToRemove = personDescriptor.getTags();
        tagsToRemove.ifPresent(existingTags::removeAll);
        return new Person(name, phone, email, address, existingTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        RemoveTagCommand otherCommand = (RemoveTagCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && personDescriptor.equals(otherCommand.personDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("personDescriptor", personDescriptor)
                .toString();
    }
}
