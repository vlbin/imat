package iMat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BackButton extends VBox {

    private iMatController parent;

    public BackButton(iMatController parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BackButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parent = parent;
    }

    @FXML
    public void handleBackButton() {
        parent.goBack();
    }

}
