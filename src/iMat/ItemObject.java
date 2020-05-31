package iMat;

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

    private ShoppingItem item;
    private iMatController parentController;
    public ItemObject(ShoppingItem item, iMatController parentController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemObject.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.item = item;
        this.parentController = parentController;
        try{
            fxmlLoader.load();
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }

        item_image.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct()));
        item_name.setText(item.getProduct().getName());
        item_amount.setText("" + (int)item.getAmount() + " st");
        item_price.setText(iMatController.roundPrice(item.getTotal(), 2));

    }

    @FXML
    public void addItemFromOrder() {
        parentController.addItemFromOrder(item);
    }

}
