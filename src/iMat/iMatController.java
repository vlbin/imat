
package iMat;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import static java.util.Calendar.SHORT;

public class iMatController implements Initializable {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public ArrayList<AnchorPane> history = new ArrayList<>();

    @FXML private AnchorPane window;

    BackButton bb;

    private String deliveryDate;
    private String deliveryTime;

    @FXML private MenuBar menuBar;
    @FXML private Button returningCustomerButton;
    @FXML private Button newCustomerButton;
    @FXML private AnchorPane returningOrNew;
    @FXML private AnchorPane loginScreen;
    @FXML private AnchorPane deliveryScreen;
    @FXML private AnchorPane signupOne;
    @FXML private AnchorPane signupTwo;
    @FXML private AnchorPane signupThree;
    @FXML private AnchorPane signupComplete;

    @FXML private ToggleGroup time;

    /* LOGIN */
    @FXML private TextField loginEmailInput;
    @FXML private PasswordField loginPasswordInput;

    /* SIGNUP ONE */
    @FXML private TextField firstNameInput;
    @FXML private TextField lastNameInput;
    @FXML private TextField homePhoneInput;
    @FXML private TextField cellPhoneInput;
    @FXML private TextField emailInput;
    @FXML private TextField repeatEmailInput;
    @FXML private PasswordField passwordInput;
    @FXML private PasswordField repeatPasswordInput;

    /* SIGNUP TWO */
    @FXML private TextField addressInput;
    @FXML private TextField postCodeInput;
    @FXML private TextField cityInput;
    @FXML private TextField apartmentInput;

    /* SIGNUP THREE */
    @FXML private TextField cardholderNameInput;
    @FXML private TextField cardNumberInput;
    @FXML private TextField cardExpireMonthInput;
    @FXML private TextField cardExpireYearInput;
    @FXML private TextField cvvInput;

    @FXML private TabPane deliveryTabPane;
    @FXML private Text dateText;
    @FXML private Text timeText;
    @FXML private AnchorPane orderConfirmationScreen;

    private int errorCount;

    public AnchorPane previousScreen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        postCodeInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postCodeInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        apartmentInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    apartmentInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        cardNumberInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardNumberInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        cardExpireMonthInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardExpireMonthInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        cardExpireYearInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cardExpireYearInput.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        goTo(returningOrNew);
        history.add(getCurrentScreen());

    }

    public void placeOrder() {
        dateText.setText(deliveryTabPane.getSelectionModel().getSelectedItem().getText());
        timeText.setText(((ToggleButton) time.getSelectedToggle()).getText());
        goTo(orderConfirmationScreen, false);
    }

    public AnchorPane getCurrentScreen() {
        return (AnchorPane) window.getChildren().get(window.getChildren().size()-1);
    }

    public AnchorPane getPreviousScreen() {
        return (AnchorPane) window.getChildren().get(window.getChildren().size()-2);
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
        //TODO add backbutton to start page..
        pane.toFront();
    }

    public void goBack() {
        history.remove(history.size()-1).toFront();
    }

    public void goToStart() {
        //TODO fix this
    }

    @FXML
    public void returningCustomerClicked() {
        previousScreen = getCurrentScreen();
        goTo(loginScreen);
    }

    @FXML
    public void newCustomerClicked() {
        previousScreen = getCurrentScreen();
        goTo(signupOne);
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
            deliveryTabPane.getTabs().get(i).setText(calendar.get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMM").format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        goTo(deliveryScreen, false);
    }

    @FXML
    public void goToLogin() {
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
        if (empty(loginEmailInput)) displayError(loginEmailInput);
        if (empty(loginPasswordInput)) displayError(loginPasswordInput);
        System.out.println(loginEmailInput.getText() + "-----" + backend.getUser().getUserName());
        System.out.println(loginPasswordInput.getText() + "-----" + backend.getUser().getPassword());
        if (loginEmailInput.getText().equals(backend.getUser().getUserName()) && loginPasswordInput.getText().equals(backend.getUser().getPassword())) {
            returnFromLogin();
        }
    }

    public void displayError(TextField textField) {
        textField.getStyleClass().add("error");
        errorCount++;
    }


    public void completeSignUpOne() {
        errorCount = 0;
        if (empty(emailInput)) displayError(emailInput);
        if (empty(repeatEmailInput)) displayError(repeatEmailInput);
        if (empty(firstNameInput)) displayError(firstNameInput);
        if (empty(lastNameInput)) displayError(lastNameInput);
        if (empty(passwordInput)) displayError(passwordInput);
        if (empty(repeatPasswordInput)) displayError(repeatPasswordInput);
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

    public void completeSignUpThree() {
        errorCount = 0;
        if (empty(cardholderNameInput)) displayError(cardholderNameInput);
        if (empty(cardNumberInput) || cardNumberInput.getText().length() != 16) displayError(cardNumberInput);
        if (empty(cardExpireMonthInput) || cardExpireMonthInput.getText().length() != 2) displayError(cardExpireMonthInput);
        if (empty(cardExpireYearInput) ||cardExpireYearInput.getText().length() != 2) displayError(cardExpireYearInput);
        if (empty(cvvInput) || cvvInput.getText().length() != 3) displayError(cvvInput);
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
            backend.shutDown();
        }
    }
}
