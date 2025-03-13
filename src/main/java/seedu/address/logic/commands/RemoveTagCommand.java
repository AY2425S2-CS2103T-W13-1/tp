package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;



/**
 * Removes one tag from a person in the address book.
 */
public class RemoveTagCommand extends Command {
    public static final String COMMAND_WORD = "removetag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a tag from a person in the address book. ";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Tag removed for person: %1$s";

    private final Index targetIndex;
    private final String tagName;
    public RemoveTagCommand(Index targetIndex, String tagName) {
        this.targetIndex = targetIndex;
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        Set<Tag> personToDeleteTags = personToDelete.getTags();
        personToDeleteTags.remove(tagName.hashCode());
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(personToDelete)));
    }
}
