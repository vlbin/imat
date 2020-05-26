package MyPages;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Date;
import java.util.List;

public class MyPages extends AnchorPane {

    private DateListObject activeObject = null;

    @FXML private Pane button_settings;
    @FXML private Rectangle settings_background;
    @FXML private StackPane mypages_stackpane;
    @FXML private AnchorPane settings_window;
    @FXML private Pane button_history;
    @FXML private Rectangle history_background;
    @FXML private AnchorPane history_window;
    @FXML private FlowPane date_list;
    @FXML private FlowPane product_list;
    @FXML private Label order_header;
    @FXML private Label epostadress;
    @FXML private Label homeadress;
    @FXML private Label creditcard;

    @FXML public void initialize(){


        //Settings for settings-button
        EventHandler<MouseEvent> eventHandler = e -> {
            settings_window.toFront();
            settings_background.getStyleClass().remove("button");
            settings_background.getStyleClass().add("button-active");

            history_background.getStyleClass().remove("button-active");
            history_background.getStyleClass().add("button");
        };
        button_settings.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        //Settings for history-button
        eventHandler = mouseEvent -> {
            history_window.toFront();
            history_background.getStyleClass().remove("button");
            history_background.getStyleClass().add("button-active");

            settings_background.getStyleClass().remove("button-active");
            settings_background.getStyleClass().add("button");
        };
        button_history.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


        //Adds all history items
        date_list.getChildren().clear();
        List<Order> orders = IMatDataHandler.getInstance().getOrders();
        for(int i = 0; i < orders.size(); i++){
            Date date = orders.get(i).getDate();
            DateListObject dateListObject = new DateListObject(date, i, this);
            date_list.getChildren().add(dateListObject);
        }

        String email = IMatDataHandler.getInstance().getCustomer().getEmail();
        if(email != null && !email.equals("")){
            epostadress.setText(email);
        }

        String address = IMatDataHandler.getInstance().getCustomer().getAddress();
        if(address != null && !address.equals("")){
            homeadress.setText(address);
        }

        String cardNumber = IMatDataHandler.getInstance().getCreditCard().getCardNumber();
        if(cardNumber != null && !cardNumber.equals("")){
            StringBuilder censoredCardNumber = new StringBuilder(cardNumber);
            censoredCardNumber.replace(0, 3, "****");
            censoredCardNumber.replace(censoredCardNumber.length() - 5, censoredCardNumber.length() - 1, "****");
            creditcard.setText(censoredCardNumber.toString());
        }
    }


    public void setActiveOrder(DateListObject newActive, int index, String headerName){
        Order order = IMatDataHandler.getInstance().getOrders().get(index);
        order_header.setText("Order: " + headerName);
        product_list.getChildren().clear();
        for(ShoppingItem item : order.getItems()){
            ItemObject itemObject = new ItemObject(item);
            product_list.getChildren().add(itemObject);
        }

        if(activeObject != null){
            activeObject.getStyleClass().remove("background-item-active");
            activeObject.getStyleClass().add("background-item");
        }

        newActive.getStyleClass().remove("background-item");
        newActive.getStyleClass().add("background-item-active");
        activeObject = newActive;
    }

}
