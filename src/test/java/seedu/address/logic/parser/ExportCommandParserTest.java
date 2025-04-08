package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();
    @Test
    public void parse_validArgs_returnsExportCommand() throws Exception {
        String userInput = "exported_data.json";
        ExportCommand expectedCommand = new ExportCommand(Paths.get(userInput));
        ExportCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }
    @Test
    public void parse_missingArgs_throwsParseException() {
        String userInput = "   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwsParseException() {
        String userInput = "export exported_data.json";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_argContainSpecialCharacter_throwsParseException() {
        String userInput = "export ??.json";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(ExportCommand.MESSAGE_EXPORT_FAILURE
                        + " Invalid path: Illegal char <?> at index 0: ??.json"));
    }
}
