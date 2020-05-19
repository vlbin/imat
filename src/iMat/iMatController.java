
package iMat;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class iMatController implements Initializable {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public ArrayList<AnchorPane> history = new ArrayList<>();

    @FXML private AnchorPane window;

    BackButton bb;

    @FXML private MenuBar menuBar;
    @FXML private Button returningCustomerButton;
    @FXML private Button newCustomerButton;
    @FXML private AnchorPane returningOrNew;
    @FXML private AnchorPane loginScreen;
    @FXML private AnchorPane signupOne;
    @FXML private AnchorPane signupTwo;
    @FXML private AnchorPane signupThree;

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


    public AnchorPane previousPage;

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

    public AnchorPane getCurrentScreen() {
        return (AnchorPane) window.getChildren().get(window.getChildren().size()-1);
    }

    public void goTo(AnchorPane pane) {
        history.add(getCurrentScreen());
        this.bb = new BackButton(this);
        bb.setLayoutX(40);
        bb.setLayoutY(116);
        pane.getChildren().add(bb);
        pane.toFront();
    }

    public void goBack() {
        history.remove(history.size()-1).toFront();
    }

    @FXML
    public void returningCustomerClicked() {
        previousPage = getCurrentScreen();
        goTo(loginScreen);
    }

    @FXML
    public void newCustomerClicked() {
        previousPage = getCurrentScreen();
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

    public void completeSignUpOne() {
        if (empty(emailInput) || empty(repeatEmailInput) || empty(firstNameInput) || empty(lastNameInput) || empty(passwordInput) || empty(repeatPasswordInput)
        ) {
            return;
        } else {
            if (!passwordInput.getText().equals(repeatPasswordInput.getText())) {
                return;
            }
            if (!emailInput.getText().equals(repeatEmailInput.getText())) {
                return;
            }
            if (!validate(emailInput.getText())) {
                return;
            }
            goTo(signupTwo);
            //save input values
        }
    }

    public void completeSignUpTwo() {
        if (empty(addressInput) || empty(postCodeInput) || empty(cityInput)) {
            return;
        } else {
            goTo(signupThree);
        }
    }

    public void completeSignUpThree() {
        if (empty(cardholderNameInput) || empty(cardNumberInput) || empty(cardExpireMonthInput) || empty(cardExpireYearInput) || empty(cvvInput)) {
            return;
        } else {

        }
    }


    
}
