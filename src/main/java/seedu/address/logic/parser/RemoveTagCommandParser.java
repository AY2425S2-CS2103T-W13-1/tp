package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PersonDescriptor;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns a RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        PersonDescriptor removeTagPersonDescriptor = new PersonDescriptor();

        parseTags(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(removeTagPersonDescriptor::setTags);
        return new RemoveTagCommand(index, removeTagPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTags(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty() || tags.contains("")) {
            throw new ParseException(RemoveTagCommand.MESSAGE_EMPTY_TAG);
        }

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
