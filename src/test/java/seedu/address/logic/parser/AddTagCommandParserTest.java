package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddTagCommand;
import seedu.address.testutil.PersonDescriptorBuilder;

public class AddTagCommandParserTest {
    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, " 1 t/friends", new AddTagCommand(INDEX_FIRST_PERSON, new PersonDescriptorBuilder()
                .withTags("friends").build()));

        // multiple tags - all accepted
        assertParseSuccess(parser, " 1 t/friends t/colleagues",
                new AddTagCommand(INDEX_FIRST_PERSON, new PersonDescriptorBuilder().withTags("friends", "colleagues")
                        .build()));
    }


    @Test
    public void parse_noTagsPresent_failure() {
        // no tags at all
        assertParseFailure(parser, " 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE));
        // tag prefix present but no tags
        assertParseFailure(parser, " 1 t/", AddTagCommand.MESSAGE_EMPTY_TAG);
        // tag prefix present but only spaces
        assertParseFailure(parser, " 1 t/     ", AddTagCommand.MESSAGE_EMPTY_TAG);
    }

    @Test
    public void parse_multipleTagsWithOneEmpty_throwsParseException() {
        assertParseFailure(parser, "1 t/valid t/", AddTagCommand.MESSAGE_EMPTY_TAG);
    }
}
