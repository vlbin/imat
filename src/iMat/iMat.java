
package iMat;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class iMat extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("iMat/iMatpro");
        
        Parent root = FXMLLoader.load(getClass().getResource("iMat.fxml"), bundle);
        
        Scene scene = new Scene(root, 600, 400);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());
    }

    @Override
    public void stop() {
        IMatDataHandler.getInstance().getShoppingCart().clear();
        //IMatDataHandler.getInstance().reset();
        IMatDataHandler.getInstance().shutDown();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
