package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PersonDescriptor;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;


public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();
    @Test
    public void parse_validArgsMultipleTags_returnsRemoveTagCommand() {
        //valid input with multiple tags
        String userInput = "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        Index expectedIndex = Index.fromOneBased(1);
        PersonDescriptor expectedDescriptor = new PersonDescriptor();
        expectedDescriptor.setTags(Set.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        RemoveTagCommand expectedCommand = new RemoveTagCommand(expectedIndex, expectedDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validArgsSingleTag_returnsRemoveTagCommand() {
        String userInput = "1 " + TAG_DESC_FRIEND;
        Index expectedIndex = Index.fromOneBased(1);
        PersonDescriptor expectedDescriptor = new PersonDescriptor();
        expectedDescriptor.setTags(Set.of(new Tag(VALID_TAG_FRIEND)));

        RemoveTagCommand expectedCommand = new RemoveTagCommand(expectedIndex, expectedDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String userInput = TAG_DESC_FRIEND;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingTag_throwsParseException() {
        String userInput = "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "abc" + TAG_DESC_FRIEND;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTagField_returnsRemoveTagCommandWithNoTags() {
        String userInput = "1 t/";
        Index expectedIndex = Index.fromOneBased(1);
        PersonDescriptor expectedDescriptor = new PersonDescriptor();
        expectedDescriptor.setTags(Set.of());

        RemoveTagCommand expectedCommand = new RemoveTagCommand(expectedIndex, expectedDescriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}


