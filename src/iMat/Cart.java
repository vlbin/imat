package iMat;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class Cart extends AnchorPane {

	@FXML
	private ScrollPane scrpane;
	@FXML
	private Label lblTaBort;
	@FXML
	private Button btnKassa;
	@FXML
	private FlowPane flowPaneItems;
	@FXML
	private Label price;
	@FXML
	private ImageView closeProduct;
	@FXML
	public Text totalPriceCart;
	@FXML
	private Button buttonLower;
	private iMatController parentController;

	public Cart(iMatController parentController, List<ShoppingItem> list) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cart.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		scrpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		if (list.size() < 7) {
			scrpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		}
		this.parentController = parentController;
		for (ShoppingItem item : list) {
			if (item.getAmount() > 0) {
				CartPanel cPanel = new CartPanel(parentController, item,
						parentController.getImage(item.getProduct(), 80, 80));
				flowPaneItems.getChildren().add(cPanel);
			} else {
				parentController.clearItemFromCart(item);
			}
		}
		if (list.size() > 0) {
			btnKassa.setDisable(false);
		}
	}

	public void updateCartPrice() {
		if ((IMatDataHandler.getInstance()).getShoppingCart().getTotal() == 0) {
			btnKassa.setDisable(true);
		} else {
			btnKassa.setDisable(false);
		}
		totalPriceCart.setText(
				iMatController.roundPrice((IMatDataHandler.getInstance()).getShoppingCart().getTotal(), 2) + " kr");
	}

	@FXML
	public void goToKassa() {
		parentController.goToKassa(this);
	}

	@FXML
	public void closeButtonMouseEntered() {
		closeProduct
				.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/Group 1_darkred.png")));
	}

	@FXML
	public void closeButtonMousePressed() {
		closeProduct.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/Group 1_red.png")));
	}

	@FXML
	public void closeButtonMouseExited() {
		closeProduct.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/Group 1.png")));
	}

	@FXML
	public void closeCart() {
		parentController.closeCart(this);
	}

	@FXML
	public void mouseTrap(Event event) {
		event.consume();
	}
}
