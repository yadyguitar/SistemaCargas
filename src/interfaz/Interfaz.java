package interfaz;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;




public class Interfaz extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException  {
		
		Parent mainLayout = FXMLLoader.load(getClass().getResource("InterfazFXML.fxml"));
        Scene scene = new Scene(mainLayout, 1100, 600);
        primaryStage.setTitle("Consolidómetro");
        primaryStage.setScene(scene);

        Image icon = new Image(getClass().getResourceAsStream("logo_uv.png"));
		primaryStage.getIcons().add(icon);
		
		primaryStage.setMaximized(true);
		
		primaryStage.show();
                
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
