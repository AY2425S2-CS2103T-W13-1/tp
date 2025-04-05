package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {
    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnImportCommand() throws Exception {
        String userInput = "src/test/data/ImportCommandTest/validAddressBook.json";
        ImportCommand expectedCommand = new ImportCommand(Paths.get(userInput));
        ImportCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingArgs_throwParseException() {
        String userInput = "   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArgs_throwParseException() {
        String userInput = "import validAddressBook.json";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
