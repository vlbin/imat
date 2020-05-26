package MyPages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ItemObject extends AnchorPane {

    @FXML ImageView item_image;
    @FXML Label item_name;
    @FXML Label item_amount;
    @FXML Label item_price;

    public ItemObject(ShoppingItem item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemObject.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }

        item_image.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct()));
        item_name.setText(item.getProduct().getName());
        item_amount.setText("" + (int)item.getAmount() + " st");
        item_price.setText("" + item.getAmount() * item.getProduct().getPrice() + "kr");

    }

}
