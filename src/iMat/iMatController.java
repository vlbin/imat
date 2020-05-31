
package iMat;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class iMatController implements Initializable {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public ArrayList<AnchorPane> history = new ArrayList<>();

	@FXML
	private AnchorPane window;

	private DateListObject activeObject = null;
	BackButton bb;

	private boolean sessionActive = false;

	private String deliveryDate;
	private String deliveryTime;

	private static HashMap<Product, Double> startPrice = new HashMap<>();

	@FXML
	private Button navbarLogin;
	@FXML
	private Text errorLoginText;

	// @FXML private MenuBar menuBar;
	@FXML
	private Button returningCustomerButton;
	@FXML
	private Button newCustomerButton;
	@FXML
	private AnchorPane returningOrNew;
	@FXML
	private AnchorPane loginScreen;
	@FXML
	private AnchorPane deliveryScreen;
	@FXML
	private AnchorPane signupOne;
	@FXML
	private AnchorPane signupTwo;
	@FXML
	private AnchorPane signupThree;
	@FXML
	private AnchorPane signupComplete;

	@FXML
	private Order activeOrder;

	@FXML
	private FlowPane welcomePane;
	@FXML
	private AnchorPane categoryPane;

	@FXML
	private AnchorPane startScreen;

	@FXML
	private ToggleGroup time;
	@FXML
	private ImageView arrow;

	/* LOGIN */
	@FXML
	private TextField loginEmailInput;
	@FXML
	private PasswordField loginPasswordInput;

	@FXML
	private TextField searchInput;

	/* SIGNUP ONE */
	@FXML
	private TextField firstNameInput;
	@FXML
	private TextField lastNameInput;
	@FXML
	private TextField homePhoneInput;
	@FXML
	private TextField cellPhoneInput;
	@FXML
	private TextField emailInput;
	@FXML
	private TextField repeatEmailInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private PasswordField repeatPasswordInput;

	@FXML
	private Text cartTotal;

	@FXML
	private Text cartTotalMyPages;

	/* SIGNUP TWO */
	@FXML
	private TextField addressInput;
	@FXML
	private TextField postCodeInput;
	@FXML
	private TextField cityInput;
	@FXML
	private TextField apartmentInput;

	/* SIGNUP THREE */
	@FXML
	private TextField cardholderNameInput;
	@FXML
	private TextField cardNumberInput;
	@FXML
	private TextField cardExpireMonthInput;
	@FXML
	private TextField cardExpireYearInput;
	@FXML
	private TextField cvvInput;

	@FXML
	private TabPane deliveryTabPane;
	@FXML
	private Text dateText;
	@FXML
	private Text timeText;
	@FXML
	private AnchorPane orderConfirmationScreen;

	@FXML
	private Text categoryHeading;

	@FXML
	private Pane button_settings;
	@FXML
	private Rectangle settings_background;
	@FXML
	private StackPane mypages_stackpane;
	@FXML
	private AnchorPane settings_window;
	@FXML
	private Pane button_history;
	@FXML
	private Rectangle history_background;
	@FXML
	private AnchorPane history_window;
	@FXML
	private FlowPane date_list;
	@FXML
	private FlowPane product_list;
	@FXML
	private Label order_header;
	@FXML
	private TextField epostadress;
	@FXML
	private TextField homeadress;
	@FXML
	private Label creditcard;

	private ArrayList<ProductItem> productItemList;

	private List<CustomCategory> customCategories = new ArrayList<CustomCategory>();

	private IMatDataHandler dataHandler;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private FlowPane menuBar;
	@FXML
	private FlowPane ProductItems;
	@FXML
	private ScrollPane scrollProducts;
	private boolean cartOrWindowStatus;

	@SuppressWarnings("static-access")

	@FXML
	private AnchorPane myPagesRoot;

	private int errorCount;

	public AnchorPane previousScreen;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.setProperty("prism.lcdtext", "false");
		categoryPane.setVisible(false);
		productItemList = new ArrayList<>();
		window.getStylesheets().add(getClass().getResource("BebasNeue-Regular.ttf").toExternalForm());

		scrollProducts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// scrollProducts.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		dataHandler = dataHandler.getInstance();
		dataHandler.getShoppingCart().clear();
		// ProductItems.getChildren().add(new ProductSpace());
		int i = 1;
		for (Product p : dataHandler.getProducts()) {
			ShoppingItem item = new ShoppingItem(p, 0);
			ProductItem product;
			switch (i) {
			case 1:
				product = new ProductItem(item, this, 10);
				break;
			case 2:
				product = new ProductItem(item, this, 50);
				break;
			case 3:
				product = new ProductItem(item, this, 20);
				break;
			case 4:
				product = new ProductItem(item, this, 20);
				break;
			default:
				product = new ProductItem(item, this, 0);
				break;
			}
			dataHandler.getShoppingCart().addShoppingCartListener(product);
			ProductItems.getChildren().add(product);
			productItemList.add(product);

			i++;
		}

		customCategories.add(new CustomCategory("Bakprodukter", "flour_sugar_salt"));
		customCategories.add(new CustomCategory("Drycker", "cold_drinks", "hot_drinks"));
		customCategories.add(new CustomCategory("Fisk", "fish"));
		customCategories
				.add(new CustomCategory("Frukt & Bär", "citrus_fruit", "exotic_fruit", "melons", "fruit", "berry"));
		customCategories.add(new CustomCategory("Fröer & Nötter", "nuts_and_seeds"));
		customCategories.add(new CustomCategory("Godis", "sweet"));
		customCategories.add(
				new CustomCategory("Grönsaker & Rotfrukter", "vegetable_fruit", "root_vegetable", "cabbage", "pod"));
		customCategories.add(new CustomCategory("Kött", "meat"));
		customCategories.add(new CustomCategory("Mejeriprodukter", "dairies"));
		customCategories.add(new CustomCategory("Spannmål", "bread", "pasta", "potato_rice"));
		customCategories.add(new CustomCategory("Örter", "herb"));

		for (CustomCategory c : customCategories) {
			menuBar.getChildren().add(new MenuItem(c, this));
		}

		goTo(startScreen, false);
		history.add(getCurrentScreen());
	}

	public void placeOrder() {
		dateText.setText(deliveryTabPane.getSelectionModel().getSelectedItem().getText());
		timeText.setText(((ToggleButton) time.getSelectedToggle()).getText());
		List<ShoppingItem> cartItems = dataHandler.getShoppingCart().getItems();
		for (ShoppingItem si : cartItems) {
			System.out.println(si.getProduct().getName() + "---" + si.getAmount());
		}
		dataHandler.placeOrder(false);
		dataHandler.shutDown();
		for (int i = 0; i < productItemList.size(); i++) {
			productItemList.get(i).getItem().setAmount(0);
			productItemList.get(i).changeText();
			notifyCart(productItemList.get(i).getItem());
		}
		dataHandler.getShoppingCart().clear();
		goTo(orderConfirmationScreen, false);
	}

	public AnchorPane getCurrentScreen() {
		return (AnchorPane) window.getChildren().get(window.getChildren().size() - 1);
	}

	public AnchorPane getPreviousScreen() {
		return (AnchorPane) window.getChildren().get(window.getChildren().size() - 2);
	}

	public void goTo(AnchorPane pane) {
		goTo(pane, true);
	}

	public void goTo(AnchorPane pane, boolean backButton) {
		history.add(getCurrentScreen());
		if (backButton) {
			this.bb = new BackButton(this);
			bb.setLayoutX(40);
			bb.setLayoutY(116);
			pane.getChildren().add(bb);
		}
		// TODO add backbutton to start page..
		pane.toFront();
	}

	public AnchorPane getWindow() {
		return window;
	}

	public void addBackToStartButton() {
		Button backToStart = new Button();
		backToStart.setText("<< Tillbaka till startsidan");
		backToStart.getStyleClass().add("back-button");
		backToStart.setLayoutX(185);
		backToStart.setLayoutY(16);
		categoryPane.getChildren().add(backToStart);
		backToStart.setOnMouseClicked(e -> {
			goToStart();
			categoryPane.getChildren().remove(backToStart);
		});
	}

	public void showProducts(CustomCategory customCategory) {
		ProductItems.getChildren().clear();
		welcomePane.setVisible(false);
		arrow.setVisible(false);
		categoryPane.setVisible(true);
		categoryHeading.setText(customCategory.getName());
		addBackToStartButton();
		for (ProductItem productItem : productItemList) {
			if (customCategory.getSubCategories().contains(productItem.getItem().getProduct().getCategory())) {
				ProductItems.getChildren().add(productItem);
			}
		}
	}

	public void goBack() {
		history.remove(history.size() - 1).toFront();
	}

	public void goToStart() {
		searchInput.setText("");
		ProductItems.getChildren().clear();
		for (ProductItem p : productItemList) {
			ProductItems.getChildren().add(p);
		}
		categoryPane.setVisible(false);
		welcomePane.setVisible(true);
		arrow.setVisible(true);
		goTo(startScreen, false);
	}

	@FXML
	public void returningCustomerClicked() {
		previousScreen = getCurrentScreen();
		goTo(loginScreen);
	}

	public void goToKassa(Cart cart) {
		closeCart(cart);
		if (sessionActive)
			goToDelivery();
		else
			goTo(returningOrNew);
	}

	@FXML
	public void newCustomerClicked() {
		previousScreen = getCurrentScreen();
		goTo(signupOne);
	}

	@FXML
	public void searchAction() {
		String query = searchInput.getText();
		if (!query.equals("")) {
			List<Product> foundProducts = dataHandler.findProducts(query);
			ProductItems.getChildren().clear();
			welcomePane.setVisible(false);
			arrow.setVisible(false);
			categoryPane.setVisible(true);
			categoryHeading.setText("Resultat för: \"" + query + "\"");
			addBackToStartButton();
			for (ProductItem p : productItemList) {
				if (foundProducts.contains(p.getItem().getProduct())) {
					ProductItems.getChildren().add(p);
				}
			}
		}

	}

	public boolean empty(TextField textField) {
		return textField.getText().isEmpty();
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	@FXML
	public void goToDelivery() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		for (int i = 0; i < deliveryTabPane.getTabs().size(); i++) {
			deliveryTabPane.getTabs().get(i).setText(
					calendar.get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMM").format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		goTo(deliveryScreen);
	}

	@FXML
	public void changePersonalInfo() {

	}

	@FXML
	public void changeDeliveryInfo() {

	}

	@FXML
	public void changePaymentInfo() {

	}

	public void goToMyPages() {
		postCodeInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					postCodeInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		apartmentInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					apartmentInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		cardNumberInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					cardNumberInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		cardExpireMonthInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					cardExpireMonthInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		cardExpireYearInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					cardExpireYearInput.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		// Settings for settings-button
		EventHandler<MouseEvent> eventHandler = e -> {
			settings_window.toFront();
			settings_background.getStyleClass().remove("button");
			settings_background.getStyleClass().add("button-active");

			history_background.getStyleClass().remove("button-active");
			history_background.getStyleClass().add("button");
		};
		button_settings.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

		// Settings for history-button
		eventHandler = mouseEvent -> {
			history_window.toFront();
			history_background.getStyleClass().remove("button");
			history_background.getStyleClass().add("button-active");

			settings_background.getStyleClass().remove("button-active");
			settings_background.getStyleClass().add("button");
		};
		button_history.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

		// Adds all history items
		date_list.getChildren().clear();
		List<Order> orders = IMatDataHandler.getInstance().getOrders();
		for (int k = 0; k < orders.size(); k++) {
			Date date = orders.get(k).getDate();
			DateListObject dateListObject = new DateListObject(date, k, this);
			date_list.getChildren().add(dateListObject);
		}

		goTo(myPagesRoot);
	}

	@FXML
	public void navbarLoginClicked() {
		if (sessionActive)
			goToMyPages();
		else
			goToLogin();
	}

	@FXML
	public void goToLogin() {
		loginPasswordInput.clear();
		loginEmailInput.clear();
		errorLoginText.setVisible(false);
		goTo(loginScreen);
	}

	@FXML
	public void goToSignUp() {
		goTo(signupOne);
	}

	public void returnFromLogin() {
		if (getPreviousScreen().equals(returningOrNew)) {
			goToDelivery();
		} else {
			goTo(getPreviousScreen(), false);
		}

	}

	public void handleLogin() {
		errorCount = 0;

		IMatDataHandler backend = IMatDataHandler.getInstance();
		if (empty(loginEmailInput))
			displayError(loginEmailInput);
		if (empty(loginPasswordInput))
			displayError(loginPasswordInput);
		if (loginEmailInput.getText().equals(backend.getUser().getUserName())
				&& loginPasswordInput.getText().equals(backend.getUser().getPassword())) {
			sessionActive = true;
			navbarLogin.setText("Mina sidor");
			returnFromLogin();
		} else {
			errorLoginText.setVisible(true);
		}
	}

	public void displayError(TextField textField) {
		textField.getStyleClass().add("error");
		errorCount++;
	}

	public void completeSignUpOne() {
		errorCount = 0;
		if (empty(emailInput))
			displayError(emailInput);
		if (empty(repeatEmailInput))
			displayError(repeatEmailInput);
		if (empty(firstNameInput))
			displayError(firstNameInput);
		if (empty(lastNameInput))
			displayError(lastNameInput);
		if (empty(passwordInput))
			displayError(passwordInput);
		if (empty(repeatPasswordInput))
			displayError(repeatPasswordInput);
		if (!passwordInput.getText().equals(repeatPasswordInput.getText())) {
			displayError(passwordInput);
			displayError(repeatPasswordInput);
		}
		if (!emailInput.getText().equals(repeatEmailInput.getText())) {
			displayError(emailInput);
			displayError(repeatEmailInput);
		}
		if (!validate(emailInput.getText())) {
			displayError(emailInput);
		}

		if (errorCount == 0) {
			IMatDataHandler backend = IMatDataHandler.getInstance();
			backend.getUser().setUserName(emailInput.getText());
			backend.getUser().setPassword(passwordInput.getText());
			backend.getCustomer().setEmail(emailInput.getText());
			backend.getCustomer().setFirstName(firstNameInput.getText());
			backend.getCustomer().setLastName(lastNameInput.getText());
			backend.getCustomer().setPhoneNumber(homePhoneInput.getText());
			backend.getCustomer().setMobilePhoneNumber(cellPhoneInput.getText());
			goTo(signupTwo);
		}
	}

	public void completeSignUpTwo() {
		errorCount = 0;
		postCodeInput.setText(postCodeInput.getText().replaceAll(" ", ""));
		if (empty(addressInput))
			displayError(addressInput);
		if (empty(postCodeInput) || postCodeInput.getText().length() != 5)
			displayError(postCodeInput);
		if (empty(cityInput))
			displayError(cityInput);
		if (errorCount == 0) {
			IMatDataHandler backend = IMatDataHandler.getInstance();
			backend.getCustomer().setAddress(cityInput.getText());
			backend.getCustomer().setPostCode(postCodeInput.getText());
			backend.getCustomer().setPostAddress(addressInput.getText());
			goTo(signupThree);
		}
	}

	public boolean isValidMonth(String s) {
		int m = Integer.parseInt(s);
		if (m < 1 || m > 12)
			return false;
		return true;
	}

	public boolean isValidYear(String s) {
		int y = Integer.parseInt(s);
		if (y < 20 || y > 99)
			return false;
		return true;
	}

	public void completeSignUpThree() {
		errorCount = 0;
		cardNumberInput.setText(cardNumberInput.getText().replaceAll(" ", ""));
		if (empty(cardholderNameInput))
			displayError(cardholderNameInput);
		if (empty(cardNumberInput) || cardNumberInput.getText().length() != 16)
			displayError(cardNumberInput);
		if (empty(cardExpireMonthInput) || cardExpireMonthInput.getText().length() != 2
				|| !isValidMonth(cardExpireMonthInput.getText()))
			displayError(cardExpireMonthInput);
		if (empty(cardExpireYearInput) || cardExpireYearInput.getText().length() != 2
				|| !isValidYear(cardExpireYearInput.getText()))
			displayError(cardExpireYearInput);
		if (empty(cvvInput) || cvvInput.getText().length() != 3)
			displayError(cvvInput);
		if (errorCount == 0) {
			IMatDataHandler backend = IMatDataHandler.getInstance();
			backend.getCreditCard().setHoldersName(cardholderNameInput.getText());
			backend.getCreditCard().setCardNumber(cardNumberInput.getText());

			if (cardNumberInput.getText().charAt(0) == '4')
				backend.getCreditCard().setCardType("Visa");
			else
				backend.getCreditCard().setCardType("MasterCard");

			backend.getCreditCard().setValidMonth(Integer.parseInt(cardExpireMonthInput.getText()));
			backend.getCreditCard().setValidMonth(Integer.parseInt(cardExpireYearInput.getText()));
			backend.getCreditCard().setVerificationCode(Integer.parseInt(cvvInput.getText()));
			goTo(signupComplete, false);
			sessionActive = true;
			backend.shutDown();
		}
	}

	public void setActiveOrder(DateListObject newActive, int index, String headerName) {
		Order order = IMatDataHandler.getInstance().getOrders().get(index);
		activeOrder = order;
		order_header.setText("Order: " + headerName);
		product_list.getChildren().clear();
		for (ShoppingItem item : order.getItems()) {
			ItemObject itemObject = new ItemObject(item, this);
			product_list.getChildren().add(itemObject);
		}

		if (activeObject != null) {
			activeObject.getStyleClass().remove("background-item-active");
			activeObject.getStyleClass().add("background-item");
		}

		newActive.getStyleClass().remove("background-item");
		newActive.getStyleClass().add("background-item-active");
		activeObject = newActive;
	}

	public void populateProductDetailView(ShoppingItem item, int specialPrice) {
		System.out.println("1");
		cartOrWindowStatus = true;
		ProductWindow w = new ProductWindow(item, this, specialPrice);
		window.getChildren().add(w);
		w.toFront();
	}

	public CustomCategory getCategoryFromItem(ShoppingItem si) {
		for (CustomCategory c : customCategories) {
			if (c.getSubCategories().contains(si.getProduct().getCategory())) {
				return c;
			}
		}
		return null;
	}

	protected void removeFromCart(ShoppingItem item, double currentAmount) {
		if (item.getAmount() == 1) {
			dataHandler.getShoppingCart().getItems().remove(item);
		} else {

		}
		if (item.getAmount() > 0) {
			if (currentAmount != item.getAmount()) {
				changedAmount(item, currentAmount);
			} else {
				item.setAmount(item.getAmount() - 1);
			}
			threeDigitslimited(item);
		}

	}

	public void removeCartPanel(CartPanel panel) {

	}

	protected void clearItemFromCart(ShoppingItem item) {
		item.setAmount(0);
		notifyCart(item);
		dataHandler.getShoppingCart().getItems().remove(item);
	}

	protected boolean inCart(ShoppingItem item) {
		return dataHandler.getShoppingCart().getItems().contains(item);
	}

	/*
	 * if item is not in cart, add
	 */
	protected void changedAmount(ShoppingItem item, double amount) {
		if (!inCart(item)) {
			dataHandler.getShoppingCart().getItems().add(item);
		}
		if (amount == 0) {
			clearItemFromCart(item);
		} else {
			item.setAmount(amount);
			threeDigitslimited(item);
		}
		notifyCart(item);
	}

	@FXML
	public void addAllItems() {
		for (ShoppingItem si : activeOrder.getItems()) {
			if (!inCart(si)) {
				dataHandler.getShoppingCart().addItem(si);
				System.out.println(si.getAmount());
				notifyCart(si);
			}

		}
	}

	public void addItemFromOrder(ShoppingItem si) {
		if (!inCart(si)) {
			dataHandler.getShoppingCart().addItem(si);
			notifyCart(si);
		}
	}

	public void increaseItem(ShoppingItem item) {
		if (!dataHandler.getShoppingCart().getItems().contains(item))
			dataHandler.getShoppingCart().addItem(item);
		item.setAmount(item.getAmount() + 1);
		notifyCart(item);
		threeDigitslimited(item);
	}

	public void decreaseItem(ShoppingItem item) {
		if (item.getAmount() > 1)
			item.setAmount(item.getAmount() - 1);
		else {
			item.setAmount(0);
			clearItemFromCart(item);
		}
		notifyCart(item);
	}

	protected double getAmount(Product p) {
		for (ShoppingItem i : dataHandler.getShoppingCart().getItems()) {
			if (i.getProduct() == p) {
				return i.getAmount();
			}
		}
		return 0;
	}

	private void threeDigitslimited(ShoppingItem i) {
		if (i.getAmount() > 999) {
			i.setAmount(999);
		}
	}

	public Image getImage(Product p, int width, int height) {
		return dataHandler.getFXImage(p, width, height);
	}

	protected void removeProductWindow(ProductWindow productWindow) {
		cartOrWindowStatus = false;
		window.getChildren().remove(productWindow);
	}

	Cart k;

	@FXML
	protected void openCart() {
		cartOrWindowStatus = true;
		k = new Cart(this, dataHandler.getShoppingCart().getItems());
		k.updateCartPrice();
		window.getChildren().add(k);
		k.toFront();
	}

	protected void closeCart(Cart cart) {
		window.getChildren().remove(cart);
		cartOrWindowStatus = false;
	}

	public boolean getCartStatus() {
		return cartOrWindowStatus;
	}

	public static String roundPrice(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		DecimalFormat df = new DecimalFormat("#.00");
		String s = df.format(value).replace(',', '.');
		if (s.charAt(0) == '.')
			s = "0" + s;
		return s;
	}

	public void notifyCart(ShoppingItem item) {
		if (k != null) {
			k.updateCartPrice();
		}
		cartTotal.setText(roundPrice(dataHandler.getShoppingCart().getTotal(), 2) + " kr");
		cartTotalMyPages.setText(roundPrice(dataHandler.getShoppingCart().getTotal(), 2) + " kr");
		dataHandler.getShoppingCart().fireShoppingCartChanged(item, true);
	}

	public double getPrice(Product product, double specialPric) {
		double x = product.getPrice() * (1 - (specialPric / 100));
		x = x * 100;
		x = (int) x;
		x = x / 100;
		return x;
	}

	public void logOutUser() {
		sessionActive = false;
		navbarLogin.setText("Logga in");
		goToStart();
	}

	public double getPrice(double product, double specialPric) {
		double x = product * (1 - (specialPric / 100));
		x = x * 100;
		x = (int) x;
		x = x / 100;
		return x;
	}

	public static void getDefaultPrice(Product p) {
		p.setPrice(startPrice.get(p));
	}

	public static double getDefaultPriceWindow(Product p) {
		return startPrice.get(p);
	}

	public static void addDefaultPrice(Product p) {
		startPrice.put(p, p.getPrice());
	}

	public static void getDefaultPriceAll() {
		for (Product p : startPrice.keySet()) {
			getDefaultPrice(p);
		}

	}
}
