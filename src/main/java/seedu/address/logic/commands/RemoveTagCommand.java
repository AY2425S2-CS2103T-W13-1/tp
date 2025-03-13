package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    private final RemoveTagPersonDescriptor removeTagPersonDescriptor;
    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param removeTagPersonDescriptor details to edit the person with
     */
    public RemoveTagCommand(Index targetIndex, RemoveTagPersonDescriptor removeTagPersonDescriptor) {
        this.targetIndex = targetIndex;
        this.removeTagPersonDescriptor = new RemoveTagPersonDescriptor(removeTagPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRemoveTag = lastShownList.get(targetIndex.getZeroBased());
        Person removedTagPerson = createRemovedTagPerson(personToRemoveTag, removeTagPersonDescriptor);
        if (removedTagPerson.getTags().equals(personToRemoveTag.getTags())) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
        model.setPerson(personToRemoveTag, removedTagPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(removedTagPerson)));
    }
    /**
     * Creates and returns a {@code Person} with only the specified tags removed from {@code personToRemoveTag}.
     */
    private static Person createRemovedTagPerson(Person personToRemoveTag,
                                                 RemoveTagPersonDescriptor removeTagPersonDescriptor) {
        assert personToRemoveTag != null;

        Name name = personToRemoveTag.getName();
        Phone phone = personToRemoveTag.getPhone();
        Email email = personToRemoveTag.getEmail();
        Address address = personToRemoveTag.getAddress();
        // Start with the existing set of Tags
        Set<Tag> existingTags = new HashSet<>(personToRemoveTag.getTags());

        //Remove specified tags
        Optional<Set<Tag>> tagsToRemove = removeTagPersonDescriptor.getTags();
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
                && removeTagPersonDescriptor.equals(otherCommand.removeTagPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("removeTagPersonDescriptor", removeTagPersonDescriptor)
                .toString();
    }
    /**
     * Stores the details for which tags should be removed. Each non-empty field value
     * will remove the corresponding tags from the person.
     */
    public static class RemoveTagPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public RemoveTagPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public RemoveTagPersonDescriptor(RemoveTagPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof RemoveTagCommand.RemoveTagPersonDescriptor)) {
                return false;
            }

            RemoveTagCommand.RemoveTagPersonDescriptor otherRemoveTagPersonDescriptor =
                    (RemoveTagCommand.RemoveTagPersonDescriptor) other;
            return Objects.equals(name, otherRemoveTagPersonDescriptor.name)
                    && Objects.equals(phone, otherRemoveTagPersonDescriptor.phone)
                    && Objects.equals(email, otherRemoveTagPersonDescriptor.email)
                    && Objects.equals(address, otherRemoveTagPersonDescriptor.address)
                    && Objects.equals(tags, otherRemoveTagPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
