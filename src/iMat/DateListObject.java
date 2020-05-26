package MyPages;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateListObject extends AnchorPane {

    @FXML private Label label_dateitem;
    private int index;
    private MyPages controller;
    private String label;

    public DateListObject(Date date, int index, MyPages controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateListObject.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.index = index;
        this.controller = controller;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        label = formatter.format(date);
        String newLabel = "Order från " + label;
        label_dateitem.setText(newLabel);


    }

    @FXML public void onClickDateItem(Event event){
        controller.setActiveOrder(this, index, label);
    }

}
