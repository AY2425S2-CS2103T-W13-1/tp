package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
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
import seedu.address.model.person.PersonId;
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
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag(s) %2$s removed for person: %1$s";
    public static final String MESSAGE_INVALID_TAGS = "Tag(s) %2$s do not exist for this person.";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag(s) do not exist for this person.";

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

    /**
     * Executes the RemoveTagCommand by removing specified tags from the selected person.
     * Displays which tags were removed and which were invalid (not found).
     *
     * @param model The current model containing the address book.
     * @return CommandResult containing feedback to the user.
     * @throws CommandException if the index is invalid or no valid tags were found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToRemoveTag = lastShownList.get(targetIndex.getZeroBased());
        TagRemovalResult result = createTagRemovalResult(personToRemoveTag, personDescriptor);
        Person removedTagPerson = result.updatedPerson;

        if (result.validTags.isEmpty()) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        String message = messageBuilder(result);

        model.setPerson(personToRemoveTag, removedTagPerson);
        return new CommandResult(message);
    }

    /**
     * Creates and returns a {@code Person}, removing from {@code personToRemoveTag}
     * only the tags specified in {@code personDescriptor}.
     */
    private static TagRemovalResult createTagRemovalResult(Person personToRemoveTag,
                                                 PersonDescriptor personDescriptor) {
        assert personToRemoveTag != null;

        Name name = personToRemoveTag.getName();
        Phone phone = personToRemoveTag.getPhone();
        Email email = personToRemoveTag.getEmail();
        Address address = personToRemoveTag.getAddress();

        // Start with the existing set of Tags
        Set<Tag> existingTags = new HashSet<>(personToRemoveTag.getTags());
        Set<Tag> validTags = new HashSet<>();
        Set<Tag> invalidTags = new HashSet<>();
        Optional<Set<Tag>> tagsToRemove = personDescriptor.getTags();
        PersonId personId = personToRemoveTag.getId();

        //Sort and remove valid tags
        for (Tag tag : tagsToRemove.orElse(Collections.emptySet())) {
            if (existingTags.contains(tag)) {
                existingTags.remove(tag);
                validTags.add(tag);
            } else {
                invalidTags.add(tag);
            }
        }

        Person removedTagPerson = new Person(name, phone, email, address, existingTags, personId);
        return new TagRemovalResult(removedTagPerson, validTags, invalidTags);
    }

    /**
     * Constructs a user-friendly message summarizing which tags were removed
     * and which tags were not found on the person.
     *
     * @param result The result of the tag removal operation.
     * @return A formatted message to be displayed to the user.
     */
    private static String messageBuilder(TagRemovalResult result) {
        Person removedTagPerson = result.updatedPerson;

        String validTagString = formatTagSet(result.validTags);
        String invalidTagString = formatTagSet(result.invalidTags);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(removedTagPerson), validTagString));
        if (!result.invalidTags.isEmpty()) {
            sb.append("\n\n").append(String.format(MESSAGE_INVALID_TAGS,
                    Messages.format(removedTagPerson), invalidTagString));
        }
        return sb.toString();
    }

    /**
     * Formats a set of tags into a comma-separated string.
     *
     * @param tags Set of tags to format.
     * @return A string representation of the tag set, or "none" if empty.
     */
    private static String formatTagSet(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::toString)
                .sorted()
                .reduce((a, b) -> a + ", " + b)
                .orElse("none");
    }


    /**
     * A container for the result of a removeTag operation.
     * Stores the updated {@code Person} object after attempting to remove tags,
     * along with sets of tags that were successfully removed and tags that were not found.
     */
    private static class TagRemovalResult {
        private final Person updatedPerson;
        private final Set<Tag> validTags;
        private final Set<Tag> invalidTags;

        /**
         * Constructs a {@code TagRemovalResult}.
         *
         * @param updatedPerson The updated person after tag removal.
         * @param validTags Tags that were successfully removed.
         * @param invalidTags Tags that were not found on the person.
         */
        public TagRemovalResult(Person updatedPerson, Set<Tag> validTags, Set<Tag> invalidTags) {
            this.updatedPerson = updatedPerson;
            this.validTags = validTags;
            this.invalidTags = invalidTags;
        }
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemoveTagCommand otherCommand)) {
            return false;
        }

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
