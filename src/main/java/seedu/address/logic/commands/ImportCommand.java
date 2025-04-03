package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Imports contacts from a specified JSON file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_INVALID_FILE_FORMAT = "Import failed. Target file must have a .json extension.";
    public static final String MESSAGE_IMPORT_SUCCESS = "Imported contacts from %1$s as requested ...";
    public static final String MESSAGE_IMPORT_FAILURE = "Failed to import json file";
    public static final String MESSAGE_INVALID_JSON = "JSON file does not follow required format.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from the specified path.\n"
            + "Parameters: FILEPATH (must be a valid file path)\n"
            + "Example (Windows): " + COMMAND_WORD + " C:/Users/username/Desktop/exported_data.json\n"
            + "Example (Mac): " + COMMAND_WORD + " /Users/username/Desktop/exported_data.json\n"
            + "Example (Linux): " + COMMAND_WORD + " /home/user/desktop/exported_data.json";
    private int expectedIdNum = 1;
    private final Path targetPath;

    /**
     * Imports contacts from specified .json file from {@param targetPath}
     */
    public ImportCommand(Path targetPath) {
        this.targetPath = targetPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        requireNonNull(targetPath);

        if (!targetPath.toString().toLowerCase().endsWith(".json")) {
            throw new CommandException(MESSAGE_INVALID_FILE_FORMAT);
        }

        JsonAddressBookStorage jsonStorage = new JsonAddressBookStorage(targetPath);

        try {
            ReadOnlyAddressBook importedAddressBook = jsonStorage.readAddressBook()
                    .orElseThrow(() -> new CommandException(MESSAGE_IMPORT_FAILURE));

            validateImportedContacts(importedAddressBook);
            model.setAddressBook(importedAddressBook);

            Path savePath = Paths.get("data", "addressbook.json");
            new JsonAddressBookStorage(savePath).saveAddressBook(model.getAddressBook());

            return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS, targetPath));

        } catch (DataLoadingException | IOException e) {
            throw new CommandException("Invalid JSON file: " + shortenErrorMessage(e));
        }
    }

    /**
     * Method to shorten error message
     * @param e
     * @return shortened error message
     */
    private String shortenErrorMessage(Exception e) {
        if (e.getCause() instanceof IllegalValueException) {
            IllegalValueException ive = (IllegalValueException) e.getCause();
            return ive.getMessage();
        }
        return MESSAGE_INVALID_JSON;
    }

    /**
     * Checks that at least one of "phone", "email", or "address" is different from placeholder values.
     * Checks that personId of the first person is 1 and increment sequentially.
     */
    private void validateImportedContacts(ReadOnlyAddressBook addressBook) throws CommandException {
        for (Person person : addressBook.getPersonList()) {
            if (isAllPlaceholderValues(person)) {
                throw new CommandException("Invalid JSON file: Person '" + person.getName()
                        + "' does not have any added phone, email, or address.");
            }
            PersonId expectedId = new PersonId(String.valueOf(expectedIdNum));
            if (expectedId.equals(person.getId())) {
                expectedIdNum += 1;
            } else {
                throw new CommandException("Invalid JSON file: Person '" + person.getName()
                        + "' has either out-of-order OR duplicate person ID.");
            }
        }
    }

    /**
     * Returns true if all three fields (phone, email, address) are set to placeholder values.
     */
    private boolean isAllPlaceholderValues(Person person) {
        return "000".equals(person.getPhone().value)
                && "unknown@example.com".equals(person.getEmail().value)
                && "Unknown address".equals(person.getAddress().value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ImportCommand that = (ImportCommand) obj;
        return targetPath.equals(that.targetPath); // Compare the file path
    }
}
