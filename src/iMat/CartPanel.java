package iMat;

import java.io.IOException;
import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class CartPanel extends AnchorPane {

	@FXML
	private ImageView imgKassaVara;
	@FXML
	private Label lblKundvagnVara;
	@FXML
	private Label lblKundvagnEko;
	@FXML
	private Label priceAll;
	@FXML
	private TextField cartBought;
	@FXML
	private Button buttonLower;
	@FXML
	private ImageView clearFromCart;
	private iMatController parentController;
	private ShoppingItem item;

	public CartPanel(iMatController parentController, ShoppingItem item, Image image) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartPanel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		this.parentController = parentController;
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		imgKassaVara.setImage(image);
		StringBuilder sb = new StringBuilder(item.getProduct().getName());
		if (sb.length() > 12) {
			sb.replace(10, sb.length(), "...");
		}
		lblKundvagnVara.setText(sb.toString());
		if (item.getProduct().isEcological()) {
			// lblKundvagnEko.setText("Eko");
		}
		this.item = item;
		priceAll.setText(parentController.roundPrice(item.getTotal(), 2));
		addTextLimiter(cartBought, 2);
		changeText();
		outsideColor();
	}

	@FXML
	protected void deleteItem() {
		((FlowPane) this.getParent()).getChildren().remove(this);
		parentController.clearItemFromCart(item);
	}

	@FXML
	public void clearButtonMouseEntered() {
		clearFromCart.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/trash-1.png")));
	}

	@FXML
	public void clearButtonMousePressed() {
		clearFromCart.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/trash-2.png")));
	}

	@FXML
	public void clearButtonMouseExited() {
		clearFromCart.setImage(new Image(getClass().getClassLoader().getResourceAsStream("resources/trash.png")));
	}

	@FXML
	protected void addThisItem(Event event) {
		parentController.increaseItem(item);
		changeText();
		outsideColor();
		event.consume();
	}

	@FXML
	protected void removeThisItem(Event event) {
		parentController.decreaseItem(item);
		changeText();
		hoverColor();
		event.consume();
		if (zero()) {
			((FlowPane) this.getParent()).getChildren().remove(this);
		}
	}

	@FXML
	protected void changedText() {
		if (parentController.isNumeric(cartBought.getText())) {
			double temp = Double.parseDouble(cartBought.getText());
			if (temp > -1) {
				int i = cartBought.getCaretPosition();
				parentController.changedAmount(item, temp);
				changeText();
				cartBought.positionCaret(i);
			}

		}

	}

	private void changeText() {
		String s = Double.toString(item.getAmount());
		priceAll.setText(parentController.roundPrice(item.getTotal(), 2));
		cartBought.setText(s.substring(0, s.indexOf(".")));
		parentController.notifyCart(item);
	}

	@FXML
	public void mousetrap(Event event) {
		event.consume();
	}

	@FXML
	public void greenButton(Event event) {
		event.consume();
	}

	@FXML
	public void redButton(Event event) {
		event.consume();
	}

	@FXML
	public void outsideColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonLower.setStyle("-fx-background-color: #F03E3E;");
		}

	}

	@FXML
	public void hoverColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
			buttonLower.setStyle("-fx-cursor: default;");
		} else {
			buttonLower.setStyle("-fx-background-color: #a11414;");
		}

	}

	@FXML
	public void pressedColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonLower.setStyle("-fx-background-color: #5a0e0e;");
		}

	}

	private boolean zero() {
		if (cartBought.getText().equals("0")) {
			return true;
		} else {
			return false;
		}

	}

	public static void addTextLimiter(final TextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> ov, final String oldValue,
					final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}
			}
		});
	}

}
