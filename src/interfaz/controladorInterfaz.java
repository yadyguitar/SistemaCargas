package interfaz;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class controladorInterfaz implements Initializable{

	//FXML//
	
	
	////////
	final int NumeroCargas=8;
	
	//Métodos de FXML//
	@FXML public void agregaFila(MouseEvent e){
		Button b=(Button)e.getSource();
		GridPane grid=(GridPane)b.parentProperty().getValue();
		GridPane tabla = (GridPane) grid.getChildren().get(1);
		int lastRow=tabla.getChildren().size()/7;
		lastRow+=1;
		for (int i=0;i<7;i++){
			tabla.add(new TextField(), i, lastRow);	
		}
		tabla.setGridLinesVisible(false);
		tabla.setGridLinesVisible(true);
	}
	///////////////////
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	

}
