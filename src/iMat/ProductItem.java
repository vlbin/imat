package iMat;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductItem extends AnchorPane implements ShoppingCartListener {

	@FXML
	private ImageView productImage;
	@FXML
	private Text specialPrice;
	@FXML
	private Text productName;
	@FXML
	private Text price;
	@FXML
	private TextField bought;
	@FXML
	private Group SpecialpriceBackground;
	@FXML
	private Button buttonLower;
	private iMatController parentController;
	private ShoppingItem item;

	public ProductItem(ShoppingItem item, iMatController parentController, String specialPric) {
		this.item = item;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItem.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		if (specialPric.equals("no")) {
			specialPrice.setVisible(false);
			SpecialpriceBackground.setVisible(false);
		} else {
			specialPrice.setText(specialPric);
			specialPrice.setStyle("-fx-font-family: \"Bebas Neue\";");
		}
		StringBuilder sb = new StringBuilder(item.getProduct().getName());
		if (sb.length() > 17) {
			sb.replace(14, sb.length(), "...");
		}
		productName.setText(sb.toString());
		price.setText(Double.toString(item.getProduct().getPrice()) + " kr");
		productImage.setImage(parentController.getImage(item.getProduct(), 220, 136));
		this.parentController = parentController;
		changeText();
		addTextLimiter(bought, 3);
	}

	public ShoppingItem getItem() {
		return item;
	}

	@FXML
	protected void onClick(Event event) {
		parentController.populateProductDetailView(item);
	}

	public void changeText() {
		String s = Double.toString(item.getAmount());
		bought.setText(s.substring(0, s.indexOf(".")));
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
	}

	@FXML
	protected void changedText() {
		//susem.out.println(bought.getText());
		if (parentController.isNumeric(bought.getText())) {
			double temp = Double.parseDouble(bought.getText());
			if (temp > -1) {
				int i = bought.getCaretPosition();
				parentController.changedAmount(item, temp);
				changeText();
				bought.positionCaret(i);
			}

		}

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
		if (bought.getText().equals("0")) {
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

	@Override
	public void shoppingCartChanged(CartEvent event) {
		if (parentController.getCartStatus()) {
			changeText();
			outsideColor();
		}

	}

}
