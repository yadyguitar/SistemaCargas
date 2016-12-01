package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class controladorInterfaz implements Initializable{

	//FXML//
	@FXML TextField nombre;
	@FXML TextField ubicacion;
	@FXML TextField muestra;
	@FXML TextField pca;
	@FXML TextField profundidad;
	
	@FXML GridPane granulometria;
	@FXML GridPane limites;
	@FXML GridPane clasificacion;
	@FXML GridPane constantes;
	@FXML GridPane humedadInicial;
	@FXML GridPane antesDeLaPrueba;
	@FXML GridPane despuesDeConsolidar;
	
	////////
	final int NumeroCargas=8;
/////////////Métodos de FXML/////////////////////////////////////////
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
	@FXML public void agregaFilaR(MouseEvent e){
		Button b=(Button)e.getSource();
		GridPane grid=(GridPane)b.parentProperty().getValue();
		GridPane tabla = (GridPane) grid.getChildren().get(1);
		int lastRow=tabla.getChildren().size()/7;
		lastRow+=1;
		for (int i=0;i<10;i++){
			tabla.add(new TextField(), i, lastRow);	
		}
		tabla.setGridLinesVisible(false);
		tabla.setGridLinesVisible(true);
	}
	@FXML public void guardar(MouseEvent e){
		String nombre=this.nombre.getText();
		String ubicacion=this.ubicacion.getText();
		String muestra=this.muestra.getText();
		String pca=this.pca.getText();
		String profundidad=this.profundidad.getText();
		
		String info=nombre+","+ubicacion+","+muestra+","+pca+","+profundidad+"\n";
		
		//Cadena de datos de granulometria
		String granulometria=cadena(6,this.granulometria);
		String limites=cadena(8,this.limites);
		String clasificacion=cadena(8,this.clasificacion);
		String constantes=cadena(12,this.constantes);
		String humedadInicial=cadena(10,this.humedadInicial);
		String antesDeLaPrueba=cadena(20,this.antesDeLaPrueba);
		String despuesDeConsolidar=cadena(22,this.despuesDeConsolidar);
		
		
		
		/////FILE/////////////////////////////////////
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(new Stage());
        
        if(file != null){
        	if(!file.getName().contains(".")) {
        		  file = new File(file.getAbsolutePath() + ".txt");
    		}
        	SaveFile(""+info+granulometria+limites+clasificacion+constantes+humedadInicial+antesDeLaPrueba+despuesDeConsolidar,file);
        }
	}
	@FXML public void nuevo() throws IOException{
		Interfaz i= new Interfaz();
		i.start(new Stage());
			
	}
	@FXML public void abrir(){
		try (Scanner scanner = new Scanner(new FileChooser().showOpenDialog(new Stage()) )) {
			String[] linea;
	        int cont=0;
			while (scanner.hasNext()){
	            String textfile=scanner.nextLine();
	            linea=textfile.split(",");
	            //cont=0 son los Datos del Proyecto
	            if(cont==0){
	            this.nombre.setText(linea[0]);
	            this.ubicacion.setText(linea[1]);
	            this.muestra.setText(linea[2]);
	            this.pca.setText(linea[3]);
	            this.profundidad.setText(linea[4]);
	            }
	            //cont=1 es Granulometría
	            if (cont==1){
	            	insertarCadena(6, this.granulometria, linea);
	            }
	            //cont=2 es limites
	            if(cont==2){
	            	insertarCadena(8,this.limites,linea);
	            }
	            //cont=3 es clasificacion
	            if(cont==3){
	            	insertarCadena(8,this.clasificacion,linea);
	            }
	            //constantes del equipo
	            if(cont==4){
	            	insertarCadena(12,this.constantes,linea);
	            }
	            //Humedad inicial
	            if(cont==5){
	            	insertarCadena(10,this.humedadInicial,linea);
	            }
	            //antesdeLaPrueba
	            if(cont==6){
	            	insertarCadena(20,this.antesDeLaPrueba,linea);
	            }
	            //despuesDeConsolidar
	            if(cont==7){
	            	insertarCadena(22,this.despuesDeConsolidar,linea);
	            }
	            
	            
	            
	           cont++;
			}

	    } catch (Exception e) {
	        
	    }
	 }
	
///////////////////////////////////////////////////
	public void insertarCadena(int tam, GridPane t, String[]linea){
		int j=0;
    	for (int i=1;i<tam;i+=2){
			TextField temp=(TextField)t.getChildren().get(i);
			if(i%2!=0){
				temp.setText(linea[j]);
				j++;
			}
		}
	}
	
	public String cadena(int tam, GridPane t){
		String c="";
		for (int i=1;i<tam;i+=2){
			TextField temp=(TextField)t.getChildren().get(i);
			if(i==tam-1){
				c+=temp.getText()+"\n";
			}else{
				c+=temp.getText()+",";
			}
		}
		return c;
	}
	
	private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("errorcito");
        }
         
    }
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	

}
