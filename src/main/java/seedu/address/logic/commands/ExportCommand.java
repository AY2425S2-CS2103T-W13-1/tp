package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports contacts into a json file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SOURCE_FILE_NOT_FOUND = "Address book not found."
            + " Start adding contacts to form your address book.";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Export failed. Target file must have a .json extension.";
    public static final String MESSAGE_EXPORT_SUCCESS = "Exported Address Book to %1$s as requested ...";
    public static final String MESSAGE_EXPORT_FAILURE = "Failed to export json file";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the address book data at the specified path.\n"
            + "Parameters: FILEPATH (must be a valid file path)\n"
            + "Example (Windows): " + COMMAND_WORD + " C:/Users/username/Desktop/exported_data.json"
            + "Example (Mac): " + COMMAND_WORD + " /Users/username/Desktop/exported_data.json"
            + "Example (Linux): " + COMMAND_WORD + " /home/user/desktop/exported_data.json";
    private static final Path SOURCE_JSON_PATH = Paths.get("data/addressbook.json");
    private final Path targetPath;
    private boolean sourcePathValid = true;

    /**
     * Creates an ExportCommand to export json file to specified target path
     * @param targetPath
     */
    public ExportCommand(Path targetPath) {
        this.targetPath = targetPath;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(targetPath);

        if (!targetPath.toString().toLowerCase().endsWith(".json")) {
            throw new CommandException(MESSAGE_INVALID_FILE_FORMAT);
        }
        if (!sourcePathValid) {
            throw new CommandException(MESSAGE_SOURCE_FILE_NOT_FOUND);
        }
        try {
            // Ensure parent directory exists
            if (targetPath.getParent() != null) {
                Files.createDirectories(targetPath.getParent());
            }

            // Perform the export
            Files.copy(SOURCE_JSON_PATH, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, targetPath.toAbsolutePath()));

        } catch (IOException e) {
            throw new CommandException(MESSAGE_EXPORT_FAILURE);
        }
    }

    /**
     * Method to check if the json source file exists and
     * store boolean in variable
     */
    public void sourceFileExists(String s) {
        if (s.equals("simulate invalid source file")) {
            sourcePathValid = false;
        } else {
            sourcePathValid = Files.exists(SOURCE_JSON_PATH);
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ExportCommand that = (ExportCommand) obj;
        return targetPath.equals(that.targetPath); // Compare the file path
    }
}
