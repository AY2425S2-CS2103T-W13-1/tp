package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportCommand.
 */
public class ExportCommandTest {
    @TempDir
    public Path temporaryFolder; // JUnit will create a temporary directory

    private final Model model = new ModelManager();
    @Test
    public void execute_exportSuccess() throws Exception {
        // Create source directory and file for testing
        Path sourceDir = Files.createDirectories(Paths.get("data"));
        Path sourcePath = sourceDir.resolve("addressbook.json");
        Files.write(sourcePath, "{}".getBytes()); // Write empty JSON object

        Path targetPath = temporaryFolder.resolve("exported_data.json");
        ExportCommand exportCommand = new ExportCommand(targetPath);

        try {
            CommandResult result = exportCommand.execute(model);
            assertEquals(String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, targetPath.toAbsolutePath()),
                    result.getFeedbackToUser());
            assertEquals(true, Files.exists(targetPath));
        } catch (CommandException e) {
            System.out.println("Error message: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void execute_invalidFileFormat_throwsCommandException() {
        Path invalidPath = temporaryFolder.resolve("exported_data.txt"); // Not a .json file

        ExportCommand exportCommand = new ExportCommand(invalidPath);
        CommandException exception = assertThrows(CommandException.class, () -> {
            exportCommand.execute(model);
        });
        assertEquals(ExportCommand.MESSAGE_INVALID_FILE_FORMAT, exception.getMessage());
    }

    @Test
    public void execute_sourceFileNotFound_throwsCommandException() {
        Path destPath = temporaryFolder.resolve("testing.json");
        ExportCommand exportCommand = new ExportCommand(destPath);
        exportCommand.sourceFileExists("simulate invalid source file");

        CommandException exception = assertThrows(CommandException.class, () -> {
            exportCommand.execute(model);
        });
        assertEquals(ExportCommand.MESSAGE_SOURCE_FILE_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Path targetPath = Paths.get("test.json");
        ExportCommand exportCommand = new ExportCommand(targetPath);

        // Same object reference should be equal
        assertTrue(exportCommand.equals(exportCommand));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Path targetPath = Paths.get("test.json");
        ExportCommand exportCommand = new ExportCommand(targetPath);

        // Null comparison should return false
        assertFalse(exportCommand.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        Path targetPath = Paths.get("test.json");
        ExportCommand exportCommand = new ExportCommand(targetPath);

        // Different class comparison should return false
        assertFalse(exportCommand.equals("string"));
    }

    @Test
    public void equals_sameTargetPath_returnsTrue() {
        Path targetPath1 = Paths.get("test.json");
        Path targetPath2 = Paths.get("test.json");

        ExportCommand exportCommand1 = new ExportCommand(targetPath1);
        ExportCommand exportCommand2 = new ExportCommand(targetPath2);

        // Commands with same target path should be equal
        assertTrue(exportCommand1.equals(exportCommand2));
        assertTrue(exportCommand2.equals(exportCommand1)); // Test symmetry
    }

    @Test
    public void equals_differentTargetPath_returnsFalse() {
        Path targetPath1 = Paths.get("test1.json");
        Path targetPath2 = Paths.get("test2.json");

        ExportCommand exportCommand1 = new ExportCommand(targetPath1);
        ExportCommand exportCommand2 = new ExportCommand(targetPath2);

        // Commands with different target paths should not be equal
        assertFalse(exportCommand1.equals(exportCommand2));
        assertFalse(exportCommand2.equals(exportCommand1)); // Test symmetry
    }

    @Test
    public void equals_differentPathFormat_dependsOnPathImplementation() {

        Path targetPath1 = Paths.get("./folder/test.json");
        Path targetPath2 = Paths.get("folder/test.json");

        ExportCommand exportCommand1 = new ExportCommand(targetPath1);
        ExportCommand exportCommand2 = new ExportCommand(targetPath2);
        assertEquals(targetPath1.equals(targetPath2),
                exportCommand1.equals(exportCommand2));
    }
}
