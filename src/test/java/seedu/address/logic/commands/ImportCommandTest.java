package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand
 */
public class ImportCommandTest {
    private final Model model = new ModelManager();

    @Test
    public void execute_importSuccess() throws Exception {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/validAddressBook.json").toAbsolutePath();
        ImportCommand importcommand = new ImportCommand(targetPath);
        try {
            CommandResult result = importcommand.execute(model);
            assertEquals(String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, targetPath.toAbsolutePath()),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            System.out.println("Error message: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void execute_notJsonFile_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/invalidAddressBook.txt").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        assertEquals(ImportCommand.MESSAGE_INVALID_FILE_FORMAT, exception.getMessage());
    }

    @Test
    public void execute_nonExistentJsonFile_throwsCommandException() {
        Path targetPath = Paths.get("h.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        assertEquals(ImportCommand.MESSAGE_IMPORT_FAILURE, exception.getMessage());
    }

    @Test
    public void execute_noCustomPhoneEmailAddress_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noCustomPhoneEmailAddress.json")
                .toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person 'alex' does not have any added phone, email, or address.";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_missingNameField_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noNameField.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person's Name field is missing!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_missingPhoneField_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noPhoneField.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person's Phone field is missing!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_missingEmailField_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noEmailField.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person's Email field is missing!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_missingAddressField_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noAddressField.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person's Address field is missing!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_missingPersonIdField_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/noPersonIdField.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person's PersonId field is missing!";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_duplicatePersonId_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/duplicatePersonId.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person 'Bernice Yu' has either out-of-order OR duplicate person ID.";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void execute_outOfOrderPersonId_throwsCommandException() {
        Path targetPath = Paths.get("src/test/data/ImportCommandTest/outOfOrderPersonId.json").toAbsolutePath();
        ImportCommand importCommand = new ImportCommand(targetPath);
        CommandException exception = Assertions.assertThrows(CommandException.class, () -> {
            importCommand.execute(model);
        });
        String expected = "Invalid JSON file: Person 'Roy Balakrishnan' has either out-of-order OR duplicate person ID.";
        assertEquals(expected, exception.getMessage());
    }
}
