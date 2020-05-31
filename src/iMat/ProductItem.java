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
	@FXML
	private Button buttonHigher;
	@FXML
	private Text pricespecial;
	private iMatController parentController;
	private ShoppingItem item;

	public ProductItem(ShoppingItem item, iMatController parentController, int specialPric) {
		this.item = item;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ProductItem.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		price.setText(Double.toString(item.getProduct().getPrice()) + " kr");
		if (specialPric == 0) {
			specialPrice.setVisible(false);
			SpecialpriceBackground.setVisible(false);
			pricespecial.setVisible(false);
		} else {
			iMatController.addDefaultPrice(item.getProduct());
			specialPrice.setText(Integer.toString(specialPric) + "% rabatt");
			specialPrice.setStyle("-fx-font-family: \"Bebas Neue\";");
			price.setStrikethrough(true);
			double sum = parentController.getPrice(item.getProduct(), specialPric);
			item.getProduct().setPrice(sum);
			pricespecial.setText(sum + " kr");

		}
		StringBuilder sb = new StringBuilder(item.getProduct().getName());
		if (sb.length() > 15) {
			sb.replace(12, sb.length(), "...");
		}
		productName.setText(sb.toString());

		productImage.setImage(parentController.getImage(item.getProduct(), 220, 136));
		this.parentController = parentController;
		changeText();
		addTextLimiter(bought, 2);
		outsideHigherColor();
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
		if (iMatController.isNumeric(bought.getText())) {
			double temp = Double.parseDouble(bought.getText());
			if (temp > -1) {
				int i = bought.getCaretPosition();
				parentController.changedAmount(item, temp);
				changeText();
				bought.positionCaret(i);
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
	public void greenButton(Event event) {
		event.consume();
	}

	@FXML
	public void redButton(Event event) {
		event.consume();
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
		if (bought.getText().equals("99")) {
			// buttonHigher.setDisable(true);
			return true;
		} else {
			return false;
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
			outsideLowerColor();
			outsideHigherColor();
		}

	}

}
