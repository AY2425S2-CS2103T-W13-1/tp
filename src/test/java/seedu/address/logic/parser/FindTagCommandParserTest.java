package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.person.TagNamesContainsTagsPredicate;

public class FindTagCommandParserTest {
    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", "Tags cannot be empty.");
    }

    @Test
    public void parse_noTagPrefix_throwsParseException() {
        assertParseFailure(parser, "friends", "Tags cannot be empty.");
    }

    @Test
    public void parse_validArgs_returnsFindTagCommand() {
        // Test with a single tag
        FindTagCommand expectedSingleTagCommand =
                new FindTagCommand(new TagNamesContainsTagsPredicate(Collections.singletonList("friends")));
        assertParseSuccess(parser, " t/friends", expectedSingleTagCommand);

        // Test with multiple tags
        FindTagCommand expectedMultipleTagsCommand =
                new FindTagCommand(new TagNamesContainsTagsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, " t/friends t/colleagues", expectedMultipleTagsCommand);
    }

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        assertParseFailure(parser, " t/invalid*tag",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagName_throwsParseException() {
        assertParseFailure(parser, " t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTagsWithOneEmpty_throwsParseException() {
        assertParseFailure(parser, " t/valid t/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }
}
