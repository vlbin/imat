package iMat;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ProductSpace extends AnchorPane {

	public ProductSpace() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductSpace.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
