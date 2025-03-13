package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagNamesContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns a FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] tagArgs = trimmedArgs.split("\\s+");
        List<String> tagNames = new ArrayList<>();
        for (String tagArg : tagArgs) {
            if (!tagArg.startsWith("t/")) {
                throw new ParseException("Invalid tag format. Each tag should start with 't/'.");
            }

            String tagName = tagArg.substring(2);
            if (tagName.isEmpty()) {
                throw new ParseException("Tag name cannot be empty.");
            }
            tagNames.add(tagName);
        }

        return new FindTagCommand(new TagNamesContainsTagsPredicate(tagNames));
    }

}
