package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
    @Test
    public void loadResourceFile_validFile_returnsContent() {
        String content = loadResourceFile("help/local_userguide.txt");
        assertNotNull(content);
        assertTrue(content.length() > 0);
    }

    /**
     * Loads a resource file from the classpath.
     * This is a copy of the method from HelpWindow to test it independently of JavaFX.
     */
    private String loadResourceFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = getClass().getResourceAsStream("/" + filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException | NullPointerException e) {
            LogsCenter.getLogger(HelpCommandTest.class).warning("Error loading resource file: " + filename);
            return null;
        }
    }
}
