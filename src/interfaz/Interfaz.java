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
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setTitle("Sistema de Cargas");
        primaryStage.setScene(scene);
        Image ico= new Image("interfaz/logo_uv.png");
        
        primaryStage.getIcons().add(ico);
        primaryStage.show();
        
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
