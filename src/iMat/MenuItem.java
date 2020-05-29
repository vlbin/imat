package iMat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

public class MenuItem extends AnchorPane {
	@FXML
	private Text menuLabel;
	@FXML
	private Rectangle border;

	private iMatController parentController;

	CustomCategory customCategory;

	public MenuItem(CustomCategory customCategory, iMatController parentController) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuItem.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		this.parentController = parentController;
		this.customCategory = customCategory;
		menuLabel.setText(customCategory.getName().substring(0,1) + customCategory.getName().replaceAll("_", " ").substring(1).toLowerCase());

		

	}

	@FXML
	protected void onClick(Event event) {
		List<Product> products = new ArrayList<>();
		parentController.showProducts(customCategory);
		/*for (ProductCategory pc : customCategory.getSubCategories()) {
			products.addAll(IMatDataHandler.getInstance().getProducts(pc));
		}
		parentController.showProducts(products);*/
	}
}
