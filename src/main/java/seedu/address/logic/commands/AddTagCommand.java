package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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

    private final Index index;
    private final AddTagPersonDescriptor addTagPersonDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param addTagPersonDescriptor details to edit the person with
     */
    public AddTagCommand(Index targetIndex, AddTagPersonDescriptor addTagPersonDescriptor) {
        this.index = targetIndex;
        this.addTagPersonDescriptor = addTagPersonDescriptor;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddTag = lastShownList.get(index.getZeroBased());
        Person personWithTags = createPersonWithAddedTags(personToAddTag, addTagPersonDescriptor);

        model.setPerson(personToAddTag, personWithTags);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personWithTags)));
    }


    private static Person createPersonWithAddedTags(Person personToAddTag,
                                                    AddTagPersonDescriptor addTagPersonDescriptor) {
        assert personToAddTag != null;

        Name updatedName = personToAddTag.getName();
        Phone updatedPhone = personToAddTag.getPhone();
        Email updatedEmail = personToAddTag.getEmail();
        Address updatedAddress = personToAddTag.getAddress();
        // gets current tags for personToAddTag
        Set<Tag> updatedTags = new HashSet<>(personToAddTag.getTags());
        // add new tags from addTagPersonDescriptor
        updatedTags.addAll(addTagPersonDescriptor.getTags().orElse(Collections.emptySet()));

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
                && addTagPersonDescriptor.equals(otherAddTagCommand.addTagPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addTagPersonDescriptor", addTagPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to tag the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class AddTagPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public AddTagPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddTagPersonDescriptor(AddTagCommand.AddTagPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
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
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            AddTagCommand.AddTagPersonDescriptor otherAddTagPersonDescriptor =
                    (AddTagCommand.AddTagPersonDescriptor) other;
            return Objects.equals(name, otherAddTagPersonDescriptor.name)
                    && Objects.equals(phone, otherAddTagPersonDescriptor.phone)
                    && Objects.equals(email, otherAddTagPersonDescriptor.email)
                    && Objects.equals(address, otherAddTagPersonDescriptor.address)
                    && Objects.equals(tags, otherAddTagPersonDescriptor.tags);
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

