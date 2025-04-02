package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
/**
 * Handles the opening and closing of NoteWindows.
 */
public class NoteWindowHandler {
    private final Map<Person, NoteWindow> openNoteWindows = new HashMap<>();
    private final Logic logic;
    public NoteWindowHandler(Logic logic) {
        this.logic = logic;
    }

    /**
     * Opens a NoteWindow for the specified person. If a NoteWindow is already open for the person, it will be focused.
     * @param person
     */
    public void openNoteWindow(Person person) {
        if (openNoteWindows.containsKey(person)) {
            NoteWindow existingNoteWindow = openNoteWindows.get(person);
            existingNoteWindow.focus();
        } else {
            NoteWindow newNoteWindow = new NoteWindow(person, logic);
            newNoteWindow.setupCloseAndHideHandler(() -> closeNoteWindowWithSaving(person));
            openNoteWindows.put(person, newNoteWindow);
            newNoteWindow.show();
        }
    }

    /**
     * Closes the NoteWindow for the specified person, saving the notes.
     * @param person
     */
    public void closeNoteWindowWithSaving(Person person) {
        // Since the notes are set to save on close/hide by default,
        // we only need to close/hide the window to save the notes.
        NoteWindow noteWindow = openNoteWindows.get(person);
        if (noteWindow != null) {
            noteWindow.hide();
            openNoteWindows.remove(person);
        }
    }
    /**
     * Closes the NoteWindow for the specified person without saving the notes.
     * @param person
     */
    public void closeNoteWindowWithoutSaving(Person person) {
        // Since the notes are set to save on close/hide by default,
        // we need to reset the handler, then close/hide the window to save the notes.
        NoteWindow noteWindow = openNoteWindows.get(person);
        if (noteWindow != null) {
            noteWindow.closeWithoutSaving();
            openNoteWindows.remove(person);
        }
    }

    /**
     * Closes all open NoteWindows.
     */
    public void closeAllNoteWindows(boolean isToSave) {
        if (isToSave) {
            new ArrayList<>(openNoteWindows.keySet()).forEach(this::closeNoteWindowWithSaving);
        } else {
            new ArrayList<>(openNoteWindows.keySet()).forEach(this::closeNoteWindowWithoutSaving);
        }
    }
}
