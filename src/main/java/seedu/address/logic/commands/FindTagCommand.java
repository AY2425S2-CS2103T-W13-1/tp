package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagNamesContainsTagsPredicate;

/**
 * Finds and lists all contacts in ScoopBook whose tag(s) match(es) all of the argument tag(s).
 * Tag matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose tag(s) match(es) all of "
            + "the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/TAG [t/MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " t/friend t/reporter t/government";

    public static final String MESSAGE_EMPTY_TAG = "Tags cannot be empty.";

    private final TagNamesContainsTagsPredicate predicate;

    public FindTagCommand(TagNamesContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindTagCommand)) {
            return false;
        }

        FindTagCommand otherFindTagCommand = (FindTagCommand) other;
        return predicate.equals(otherFindTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
