package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;

/**
 * Controller for a note page
 */
public class NoteWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);
    private static final String FXML = "NoteWindow.fxml";

    @FXML
    private TextArea noteTextArea;

    private Person person;
    private Logic logic;

    /**
     * Creates a new NoteWindow.
     *
     * @param root Stage to use as the root of the NoteWindow.
     */
    public NoteWindow(Stage root) {
        super(FXML, root);
        setupCloseHandler();
    }

    /**
     * Creates a new NoteWindow with a new Stage.
     */
    public NoteWindow() {
        this(new Stage());
    }

    /**
     * Creates a new NoteWindow for a specific person.
     *
     * @param person The person to create notes for
     */
    public NoteWindow(Person person, Logic logic) {
        this();
        this.person = person;
        this.logic = logic;
        if (person != null) {
            getRoot().setTitle("Notes for " + person.getName().fullName);
            loadExistingNotes();
        }
    }

    /**
     * Sets up the handler to save notes when the window is closed.
     */
    private void setupCloseHandler() {
        getRoot().setOnCloseRequest(event -> {
            if (person != null) {
                saveNotes();
            }
        });
    }

    /**
     * Loads existing notes for a specific person if available.
     */
    private void loadExistingNotes() {
        try {
            String content = logic.readNote(person);
            noteTextArea.setText(content);
            logger.info("Loaded notes for person: " + person.getName().fullName);
        } catch (IOException e) {
            logger.warning("Failed to load notes for person: " + person.getName().fullName);
        }
    }

    /**
     * Saves the notes for a specific person to a file.
     */
    private void saveNotes() {
        try {
            logic.saveNote(person, noteTextArea.getText());
            logger.info("Saved notes for person: " + person.getName().fullName);
        } catch (IOException e) {
            logger.warning("Failed to save notes: " + e.getMessage());
        }
    }

    /**
     * Shows the Note window.
     */
    public void show() {
        logger.fine("Showing Note window.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Hides the Note window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Note window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Sets the text in the note area.
     */
    public void setText(String text) {
        noteTextArea.setText(text);
    }

    /**
     * Gets the text from the note area.
     */
    public String getText() {
        return noteTextArea.getText();
    }
}
