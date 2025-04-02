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
    private Map<Person, NoteWindow> openNoteWindows = new HashMap<>();
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
            newNoteWindow.setupCloseAndHideHandler(() -> closeNoteWindow(person));
            openNoteWindows.put(person, newNoteWindow);
            newNoteWindow.show();
        }
    }

    /**
     * Closes the NoteWindow for the specified person.
     * @param person
     */
    public void closeNoteWindow(Person person) {
        NoteWindow noteWindow = openNoteWindows.get(person);
        if (noteWindow != null) {
            noteWindow.hide();
            openNoteWindows.remove(person);
        }
    }

    /**
     * Closes all open NoteWindows.
     */
    public void closeAllNoteWindows() {
        new ArrayList<>(openNoteWindows.keySet()).forEach(this::closeNoteWindow);
    }

    public NoteWindow getNoteWindow(Person person) {
        return openNoteWindows.get(person);
    }

}
