package iMat;

import iMat.iMatController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class CartButton {

    public CartButton(iMatController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
