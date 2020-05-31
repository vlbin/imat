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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ProductWindow extends AnchorPane {

	@FXML
	private AnchorPane ProductDetailPane;
	@FXML
	private Text nameProduct;
	@FXML
	private Text numberProduct;
	@FXML
	private Text priceProduct;
	@FXML
	private Text boughtProduct;
	@FXML
	private ImageView imageProduct;
	@FXML
	private Text specialPriceProduct;
	@FXML
	private ImageView closeProduct;
	@FXML
	private TextField amountProduct;
	@FXML
	private Button buttonLower;
	@FXML
	private Button buttonHigher;
	@FXML
	private Text pricespecial;
	@FXML
	private Group SpecialpriceBackground;
	private iMatController parentController;
	private ShoppingItem item;
	private int specialPric;

	public ProductWindow(ShoppingItem item, iMatController parentController, int specialPric) {
		this.specialPric = specialPric;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductWindow.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		if (specialPric == 0) {
			priceProduct.setText(Double.toString(item.getProduct().getPrice()) + " kr");
			pricespecial.setVisible(false);
			SpecialpriceBackground.setVisible(false);
			pricespecial.setVisible(false);
		} else {
			priceProduct.setText(Double.toString(iMatController.getDefaultPriceWindow(item.getProduct())) + " kr");
			//iMatController.addDefaultPrice(item.getProduct());
			specialPriceProduct.setText(Integer.toString(specialPric) + "% rabatt");
			specialPriceProduct.setStyle("-fx-font-family: \"Bebas Neue\";");
			priceProduct.setStrikethrough(true);
			double sum = parentController.getPrice(iMatController.getDefaultPriceWindow(item.getProduct()), specialPric);
			item.getProduct().setPrice(sum);
			pricespecial.setText(sum + " kr");
		}

		// specialPriceProduct.setText("50% rabatt");
		// specialPriceProduct.setStyle("-fx-font-family: \"Bebas Neue\";");
		this.item = item;
		this.parentController = parentController;
		nameProduct.setText(item.getProduct().getName());
		numberProduct.setText(parentController.getCategoryFromItem(item).getName());
		// priceProduct.setText(Double.toString(item.getProduct().getPrice()) + " kr");
		Image image = parentController.getImage(item.getProduct(), 340, 260);
		imageProduct.setImage(image);
		addTextLimiter(amountProduct, 2);
		changeText();

		outsideLowerColor();
		outsideHigherColor();
	}

	private void changeText() {
		String s = Double.toString(item.getAmount());
		s = s.substring(0, s.indexOf("."));
		amountProduct.setText(s);
		parentController.notifyCart(item);
	}

	@FXML
	protected void increaseItem(Event event) {
		parentController.increaseItem(item);
	}

	@FXML
	protected void addThisItem(Event event) {
		if (!max()) {
			parentController.increaseItem(item);
			changeText();
			hoverHigherColor();
			outsideLowerColor();
			event.consume();
		}

	}

	@FXML
	protected void removeThisItem(Event event) {
		if (!zero()) {
			parentController.decreaseItem(item);
			changeText();
			hoverLowerColor();
			outsideHigherColor();
			event.consume();
		}
	}

	@FXML
	protected void changedText() {
		if (iMatController.isNumeric(amountProduct.getText())) {
			double temp = Double.parseDouble(amountProduct.getText());
			if (temp > -1) {
				int i = amountProduct.getCaretPosition();
				parentController.changedAmount(item, temp);
				changeText();
				amountProduct.positionCaret(i);
				outsideLowerColor();
				outsideHigherColor();
			}

		}

	}

	@FXML
	public void mousetrap(Event event) {
		event.consume();
	}

	@FXML
	public void closeProductView(Event event) {
		if (specialPric != 0) {
			iMatController.getDefaultPrice(item.getProduct());
		}
		parentController.removeProductWindow(this);
	}

	@FXML
	public void outsideLowerColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonLower.setStyle("-fx-background-color: #F03E3E;");
		}

	}

	@FXML
	public void hoverLowerColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
			buttonLower.setStyle("-fx-cursor: default;");
		} else {
			buttonLower.setStyle("-fx-background-color: #a11414;");
		}

	}

	@FXML
	public void pressedLowerColor() {
		if (zero()) {
			buttonLower.setStyle("-fx-background-color: #e4dfdf;");
			buttonLower.setStyle("-fx-cursor: default;");
		} else {
			buttonLower.setStyle("-fx-background-color: #5a0e0e;");
		}

	}

	@FXML
	public void outsideHigherColor() {
		if (max()) {
			buttonHigher.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonHigher.setStyle("-fx-background-color: #0dbb29;");
		}

	}

	@FXML
	public void hoverHigherColor() {
		if (max()) {
			buttonHigher.setStyle("-fx-cursor: default;");
			buttonHigher.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonHigher.setStyle("-fx-background-color: #027814;");
		}

	}

	@FXML
	public void pressedHigherColor() {
		if (max()) {
			buttonHigher.setStyle("-fx-cursor: default;");
			buttonHigher.setStyle("-fx-background-color: #e4dfdf;");
		} else {
			buttonHigher.setStyle("-fx-background-color: #065d13;");
		}

	}

	private boolean max() {
		if (amountProduct.getText().equals("99")) {
			// buttonHigher.setDisable(true);
			return true;
		} else {
			return false;
		}
	}

	private boolean zero() {
		if (amountProduct.getText().equals("0")) {
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

}