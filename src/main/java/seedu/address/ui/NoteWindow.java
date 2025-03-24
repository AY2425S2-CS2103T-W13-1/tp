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
    public static final String FXML = "NoteWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(NoteWindow.class);

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
     * Creates a new NoteWindow
     */
    public NoteWindow() {
        this(new Stage());
    }

    /**
     * Set the text on the text field of the NoteWindow
     * @param text
     */
    public void setText(String text) {
        noteTextArea.setText(text);
    }

    /**
     * @return text from the text field of NoteWindow
     */
    public String getText() {
        return noteTextArea.getText();
    }

    /**
     * Shows the note window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing note page for specified contact.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the note window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the note window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the note window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
