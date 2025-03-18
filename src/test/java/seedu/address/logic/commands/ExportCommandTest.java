package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        } finally {
            // Clean up
            Files.deleteIfExists(sourcePath);
        }
    }

    @Test
    public void execute_invalidFileFormat_throwsCommandException() {
        Path invalidPath = temporaryFolder.resolve("exported_data.txt"); // Not a .json file

        ExportCommand exportCommand = new ExportCommand(invalidPath);
        assertThrows(CommandException.class, () -> exportCommand.execute(model));
    }
}
