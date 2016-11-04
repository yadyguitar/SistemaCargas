package interfaz;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Interfaz extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent mainLayout = FXMLLoader.load(getClass().getResource("InterfazFXML.fxml"));
        Scene scene = new Scene(mainLayout, 1000, 600);
        primaryStage.setTitle("Sistema de Cargas");
        primaryStage.setScene(scene);
        primaryStage.show();	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
