
package iMat;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMat extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ResourceBundle bundle = ResourceBundle.getBundle("iMat/iMatpro");
        
        Parent root = FXMLLoader.load(getClass().getResource("iMat.fxml"), bundle);
        
        Scene scene = new Scene(root, 600, 400);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
