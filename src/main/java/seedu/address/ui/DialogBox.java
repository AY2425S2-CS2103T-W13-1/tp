package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A custom control using FXML that represents a dialog box.
 */
public class DialogBox extends UiPart<Region> {
    private static final String FXML = "DialogBox.fxml";
    @FXML
    private Label text;

    /**
     * Constructor for DialogBox.
     * @param s
     */
    public DialogBox(String s) {
        super(FXML);
        text.setText(s);
    }

    public void setDialogBox(String textToSet) {
        requireNonNull(textToSet);
        text.setText(textToSet);
    }



    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    public static DialogBox getScoopBookDialog(String text) {
        var db = new DialogBox(text);
        setScoopBookDialogStyle(db);
        return db;
    }
    private static void setScoopBookDialogStyle(DialogBox db) {
        db.getRoot().setScaleX(-1);
        db.text.setScaleX(-1);
        db.text.styleProperty().set("-fx-background-color: #9c9c9c");
    }
}
