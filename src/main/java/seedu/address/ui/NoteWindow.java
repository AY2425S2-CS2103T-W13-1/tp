package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a note page
 */
public class NoteWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);
    private static final String FXML = "NoteWindow.fxml";

    @FXML
    private TextArea noteTextArea;

    /**
     * Creates a new NoteWindow.
     *
     * @param root Stage to use as the root of the NoteWindow.
     */
    public NoteWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new NoteWindow with a new Stage.
     */
    public NoteWindow() {
        this(new Stage());
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
