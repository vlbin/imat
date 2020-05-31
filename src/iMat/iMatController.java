
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

	private AnchorPane beforeMyPages;

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
	private Button navbarLogout;
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

	/* SIGNUP ONE */
	@FXML
	private TextField firstNameInput1;
	@FXML
	private TextField lastNameInput1;
	@FXML
	private TextField homePhoneInput1;
	@FXML
	private TextField cellPhoneInput1;
	@FXML
	private TextField emailInput1;
	@FXML
	private TextField repeatEmailInput1;
	@FXML
	private PasswordField passwordInput1;
	@FXML
	private PasswordField repeatPasswordInput1;

	@FXML private AnchorPane progressBar;

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

	/* SIGNUP TWO */
	@FXML
	private TextField addressInput1;
	@FXML
	private TextField postCodeInput1;
	@FXML
	private TextField cityInput1;
	@FXML
	private TextField apartmentInput1;

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
	private TextField cardholderNameInput1;
	@FXML
	private TextField cardNumberInput1;
	@FXML
	private TextField cardExpireMonthInput1;
	@FXML
	private TextField cardExpireYearInput1;
	@FXML
	private TextField cvvInput1;

	@FXML
	private TabPane deliveryTabPane;
	@FXML
	private Text dateText;
	@FXML
	private Text timeText;
	@FXML
	private Text dateTextPreview;
	@FXML
	private Text timeTextPreview;
	@FXML
	private AnchorPane orderConfirmationScreen;
	@FXML
	private AnchorPane orderPreviewScreen;

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

	@FXML
	private AnchorPane signupOne_change;
	@FXML
	private AnchorPane signupTwo_change;
	@FXML
	private AnchorPane signupThree_change;

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

	public void goToPreview() {
		goTo(orderPreviewScreen);
		dateTextPreview.setText(deliveryTabPane.getSelectionModel().getSelectedItem().getText());
		timeTextPreview.setText(((ToggleButton) time.getSelectedToggle()).getText());
	}

	public void placeOrder() {
		dateText.setText(deliveryTabPane.getSelectionModel().getSelectedItem().getText());
		timeText.setText(((ToggleButton) time.getSelectedToggle()).getText());
		List<ShoppingItem> cartItems = new ArrayList<>();
		for (ShoppingItem si : dataHandler.getShoppingCart().getItems()) {
			cartItems.add(new ShoppingItem(si.getProduct(), si.getAmount()));
		}
		dataHandler.getShoppingCart().clear();
		for (ShoppingItem si : cartItems) {
			dataHandler.getShoppingCart().addItem(si);
		}
		dataHandler.placeOrder(true);
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
		emailInput1.setText(dataHandler.getUser().getUserName());
		passwordInput1.setText(dataHandler.getUser().getPassword());
		firstNameInput1.setText(dataHandler.getCustomer().getFirstName());
		lastNameInput1.setText(dataHandler.getCustomer().getLastName());
		if (dataHandler.getCustomer().getMobilePhoneNumber().length() > 0)
			cellPhoneInput1.setText(dataHandler.getCustomer().getMobilePhoneNumber());
		if (dataHandler.getCustomer().getPhoneNumber().length() > 0)
			homePhoneInput1.setText(dataHandler.getCustomer().getPhoneNumber());
		goTo(signupOne_change, false);
	}

	@FXML
	public void changeDeliveryInfo() {
		if (dataHandler.getCustomer().getPostAddress().contains(":")) {
			addressInput1.setText(dataHandler.getCustomer().getPostAddress().substring(0, dataHandler.getCustomer().getPostAddress().indexOf(':')));
			apartmentInput1.setText(dataHandler.getCustomer().getPostAddress().substring(dataHandler.getCustomer().getPostAddress().indexOf(':')));
		} else {
			addressInput1.setText(dataHandler.getCustomer().getPostAddress());
			apartmentInput1.clear();
		}
		postCodeInput1.setText(dataHandler.getCustomer().getPostCode());
		cityInput1.setText(dataHandler.getCustomer().getAddress());
		//if (dataHandler.getCustomer().get)
		goTo(signupTwo_change, false);
	}

	@FXML
	public void changePaymentInfo() {
		cardholderNameInput1.setText(dataHandler.getCreditCard().getHoldersName());
		cardNumberInput1.setText(dataHandler.getCreditCard().getCardNumber());
		if (dataHandler.getCreditCard().getValidMonth() < 10)
			cardExpireMonthInput1.setText("0" + dataHandler.getCreditCard().getValidMonth());
		else
			cardExpireMonthInput1.setText(Integer.toString(dataHandler.getCreditCard().getValidMonth()));
		cardExpireYearInput1.setText(Integer.toString(dataHandler.getCreditCard().getValidYear()));
		String cvv = Integer.toString(dataHandler.getCreditCard().getVerificationCode());
		if (cvv.length() == 1)
			cvv = "00" + cvv;
		else if (cvv.length() == 2)
			cvv = "0" + cvv;
		cvvInput1.setText(cvv);
		goTo(signupThree_change, false);
	}

	public void goToMyPages() {
		beforeMyPages = getCurrentScreen();
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

		Button backFromMP = new Button();
		backFromMP.setText("<< Tillbaka");
		backFromMP.getStyleClass().add("back-button");
		backFromMP.setLayoutX(32);
		backFromMP.setLayoutY(128);
		myPagesRoot.getChildren().add(backFromMP);
		backFromMP.setOnMouseClicked(e -> {
			goTo(beforeMyPages, false);
			categoryPane.getChildren().remove(backFromMP);
		});
		goTo(myPagesRoot, false);
	}

	@FXML
	void abortChange() {
		goToMyPages();
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
		if (getCurrentScreen().equals(myPagesRoot)) {
			progressBar.setVisible(false);
		} else {
			progressBar.setVisible(true);
		}
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
			navbarLogout.setVisible(true);
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
			if (getPreviousScreen().equals(myPagesRoot)) {
				backend.shutDown();
				goToMyPages();
			} else {
				goTo(signupTwo);
			}
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
			if (!empty(apartmentInput) && isNumeric(apartmentInput.getText())) {
				dataHandler.getCustomer().setPostAddress(addressInput.getText()+":"+apartmentInput.getText());
			} else {
				dataHandler.getCustomer().setPostAddress(addressInput.getText());
			}
			dataHandler.getCustomer().setAddress(cityInput.getText());
			dataHandler.getCustomer().setPostCode(postCodeInput.getText());
			goTo(signupThree);
		}
	}

	public void completeSignUpTwo_change() {
		errorCount = 0;
		postCodeInput1.setText(postCodeInput1.getText().replaceAll(" ", ""));
		if (empty(addressInput1))
			displayError(addressInput1);
		if (empty(postCodeInput1) || postCodeInput1.getText().length() != 5)
			displayError(postCodeInput1);
		if (empty(cityInput1))
			displayError(cityInput1);
		if (errorCount == 0) {
			if (!empty(apartmentInput1) && isNumeric(apartmentInput1.getText())) {
				dataHandler.getCustomer().setPostAddress(addressInput1.getText()+":"+apartmentInput1.getText());
			} else {
				dataHandler.getCustomer().setPostAddress(addressInput1.getText());
			}
			dataHandler.getCustomer().setAddress(cityInput1.getText());
			dataHandler.getCustomer().setPostCode(postCodeInput1.getText());
			dataHandler.shutDown();
			goBack();
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

	public void completeSignUpThree_change() {
		errorCount = 0;
		cardNumberInput1.setText(cardNumberInput1.getText().replaceAll(" ", ""));
		if (empty(cardholderNameInput1))
			displayError(cardholderNameInput1);
		if (empty(cardNumberInput1) || cardNumberInput1.getText().length() != 16)
			displayError(cardNumberInput1);
		if (empty(cardExpireMonthInput1) || cardExpireMonthInput1.getText().length() != 2
				|| !isValidMonth(cardExpireMonthInput1.getText()))
			displayError(cardExpireMonthInput1);
		if (empty(cardExpireYearInput1) || cardExpireYearInput1.getText().length() != 2
				|| !isValidYear(cardExpireYearInput1.getText()))
			displayError(cardExpireYearInput1);
		if (empty(cvvInput1) || cvvInput1.getText().length() != 3)
			displayError(cvvInput1);
		if (errorCount == 0) {
			dataHandler.getCreditCard().setHoldersName(cardholderNameInput1.getText());
			dataHandler.getCreditCard().setCardNumber(cardNumberInput1.getText());

			if (cardNumberInput1.getText().charAt(0) == '4')
				dataHandler.getCreditCard().setCardType("Visa");
			else
				dataHandler.getCreditCard().setCardType("MasterCard");

			dataHandler.getCreditCard().setValidMonth(Integer.parseInt(cardExpireMonthInput1.getText()));
			dataHandler.getCreditCard().setValidYear(Integer.parseInt(cardExpireYearInput1.getText()));
			dataHandler.getCreditCard().setVerificationCode(Integer.parseInt(cvvInput1.getText()));
			dataHandler.shutDown();
			System.out.println(dataHandler.getCreditCard().getCardNumber());
			goBack();
		}
	}


	public void completeSignUpOne_change() {
		errorCount = 0;

		if (empty(emailInput1))
			displayError(emailInput1);
		if (empty(firstNameInput1))
			displayError(firstNameInput1);
		if (empty(lastNameInput1))
			displayError(lastNameInput1);
		if (empty(passwordInput1))
			displayError(passwordInput1);
		if (!validate(emailInput1.getText())) {
			displayError(emailInput1);
		}

		if (errorCount == 0) {
			IMatDataHandler backend = IMatDataHandler.getInstance();
			backend.getUser().setUserName(emailInput1.getText());
			backend.getUser().setPassword(passwordInput1.getText());
			backend.getCustomer().setEmail(emailInput1.getText());
			backend.getCustomer().setFirstName(firstNameInput1.getText());
			backend.getCustomer().setLastName(lastNameInput1.getText());
			backend.getCustomer().setPhoneNumber(homePhoneInput1.getText());
			backend.getCustomer().setMobilePhoneNumber(cellPhoneInput1.getText());
			backend.shutDown();
			goBack();
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
		navbarLogout.setVisible(false);
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
