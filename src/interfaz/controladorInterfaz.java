package interfaz;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.sun.javafx.scene.control.skin.SplitPaneSkin;
import com.sun.javafx.scene.layout.region.Margins.Converter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	
	@FXML TitledPane carga1;
	@FXML TitledPane carga2;
	@FXML TitledPane carga3;
	@FXML TitledPane carga4;
	@FXML TitledPane carga5;
	@FXML TitledPane carga6;
	@FXML TitledPane carga7;
	@FXML TitledPane carga8;
	@FXML BorderPane border;
	
	
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
		String granulometria=cadena(this.granulometria);
		String limites=cadena(this.limites);
		String clasificacion=cadena(this.clasificacion);
		String constantes=cadena(this.constantes);
		String humedadInicial=cadena(this.humedadInicial);
		String antesDeLaPrueba=cadena(this.antesDeLaPrueba);
		String despuesDeConsolidar=cadena(this.despuesDeConsolidar);
		
		
		String cargas=cadenaCargas();
		
		//ponerlo en una función xD
		
		
		
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
        	SaveFile(""+info+granulometria+limites+clasificacion+constantes+humedadInicial+antesDeLaPrueba+despuesDeConsolidar+cargas,file);
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
	            	insertarCadena( this.granulometria, linea);
	            }
	            //cont=2 es limites
	            if(cont==2){
	            	insertarCadena(this.limites,linea);
	            }
	            //cont=3 es clasificacion
	            if(cont==3){
	            	insertarCadena(this.clasificacion,linea);
	            }
	            //constantes del equipo
	            if(cont==4){
	            	insertarCadena(this.constantes,linea);
	            }
	            //Humedad inicial
	            if(cont==5){
	            	insertarCadena(this.humedadInicial,linea);
	            }
	            //antesdeLaPrueba
	            if(cont==6){
	            	insertarCadena(this.antesDeLaPrueba,linea);
	            }
	            //despuesDeConsolidar
	            if(cont==7){
	            	insertarCadena(this.despuesDeConsolidar,linea);
	            }
	            
	            //Para cargas
	            if(cont==8){
	            	List<GridPane> temp = getCadenaCargas(this.carga1); //obtengo un List de GridPanes, de los elementos de cada carga
	        		insertarCadena(temp.get(0),linea);
	        		
	        		textfile=scanner.nextLine();	        		
	        		linea=textfile.split(",");
	        		int tam=linea.length;
	        		
	        		if (tam>7){
	        			int contador=(tam/7)-1;
	        			while (contador>0){
	        					        				
	        				int lastRow=temp.get(1).getChildren().size()/7;
	        				lastRow+=1;
	        				for (int i=0;i<7;i++){
	        					temp.get(1).add(new TextField(), i, lastRow);	
	        				}
	        				temp.get(1).setGridLinesVisible(false);
	        				temp.get(1).setGridLinesVisible(true);
	        				
	        				contador--;
	        			}
	        		}
	        		
	        		insertarCadena(temp.get(1),linea);
	        		
	        		textfile=scanner.nextLine();	        		
	        		linea=textfile.split(",");
	        		tam=linea.length;
	        		
	        		if (tam>7){
	        			int contador=(tam/7)-1;
	        			while (contador>0){
	        					        				
	        				int lastRow=temp.get(2).getChildren().size()/7;
	        				lastRow+=1;
	        				for (int i=0;i<7;i++){
	        					temp.get(2).add(new TextField(), i, lastRow);	
	        				}
	        				temp.get(2).setGridLinesVisible(false);
	        				temp.get(2).setGridLinesVisible(true);
	        				
	        				contador--;
	        			}
	        		}
	        		
	        		insertarCadena(temp.get(2),linea);
	        		
	            }
	            
	            
	           cont++;
			}

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	 }
	
	
///////////////////////////////////////////////////
	
	
	public void insertarCadena(GridPane t, String []linea){
		int tam=t.getChildren().size()-1;
		
		int j=0;
		for (int i=0;i<tam;i++){
			if( t.getChildren().get(i).getClass().equals(new TextField().getClass()) ){
				TextField temp=(TextField)t.getChildren().get(i);
				temp.setText(linea[j]);
				j++;
			}
		}
		
			
	}
	
	public void insertarCadenaCargas(GridPane t, String []linea){
		int tam=t.getChildren().size()-1;
		
		int j=0;
		for (int i=0;i<tam;i++){
			if( t.getChildren().get(i).getClass().equals(new TextField().getClass()) ){
				TextField temp=(TextField)t.getChildren().get(i);
				temp.setText(linea[j]);
				j++;
			}
		}
			
	}
	
	
	public List<GridPane> getCadenaCargas(TitledPane carg){
		SplitPane  general = (SplitPane)carg.getContent();
		SplitPane lizq=(SplitPane)general.getItems().get(0);//Datos y  Descarga
		SplitPane lder=(SplitPane)general.getItems().get(1);
		GridPane datos=(GridPane)((GridPane)lizq.getItems().get(0)).getChildren().get(0);  //Datos
		GridPane descarga=(GridPane)((GridPane)lizq.getItems().get(1)).getChildren().get(1);  //Descarga (gridpane es el 2do elemento
		GridPane carga=(GridPane)((GridPane)lder.getItems().get(0)).getChildren().get(1);
		
		List<GridPane> temp= new ArrayList<GridPane>();
		temp.add(datos); //obtengo Gridpane de datos
		temp.add(descarga);//obtengo GridPane de descarga
		temp.add(carga);//obtengo GridPane de carga
		return temp;	
	}
	
	public String cadenaCargas(){
		//meter en un sola cadena que incluye "\n", toda la información de las 8 cargas
		List<GridPane> temp = getCadenaCargas(this.carga1); //obtengo un List de GridPanes, de los elementos de cada carga
		String cad=""; 
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		
		temp = getCadenaCargas(this.carga2);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga3);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga4);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga5);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga6);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga7);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		temp = getCadenaCargas(this.carga8);
		cad+=cadena(temp.get(0));
		cad+=cadena(temp.get(1));
		cad+=cadena(temp.get(2));
		
		return cad;
		
		
	}
	
	public String cadena( GridPane t){
		String c="";
		int tam=t.getChildren().size()-1;
		for (int i=0;i<tam;i++){
			if( t.getChildren().get(i).getClass().equals(new TextField().getClass()) ){		//si es textfield, entonces guardalo
				TextField temp=(TextField)t.getChildren().get(i);
				if (temp.getText().equals("")){
					c+=" ";
				}
				if(i==tam-1){
					c+=temp.getText()+"\n";
				}else{
					c+=temp.getText()+",";
				}
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
		try{
			initialize();				
		}catch (Exception e) {
			// TODO: handle exception
		}
				
	}
	
	float []consta= new float[4]; //0>diam 1>alt 2>ar 3>vol
	float []humIni= new float[4]; //0>flanera 1>wm 2>ws 3>wf 4>wpercent
	
	float wp=0;
	
	
	final float PI=3.14159265f;
	@FXML
    public void initialize() {
		TextField area=(TextField)this.constantes.getChildren().get(5);
		TextField diametro=(TextField)this.constantes.getChildren().get(1);
		TextField altura=(TextField)this.constantes.getChildren().get(3);
		TextField volumen=(TextField)this.constantes.getChildren().get(7);
		
		//para calcular las constantes de equipo/////////////////////////
		diametro.textProperty().addListener((observable, oldValue, newValue) -> {
		    try{
		    	consta[0]=Float.parseFloat(((String)diametro.getText()));	
		    	consta[2]=PI*consta[0]*consta[0]/4; 		    
				area.setText(String.valueOf(consta[2]));
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.println("error, letras");
			}
			
		    System.out.println(consta[0]);
		});
		
		altura.textProperty().addListener((observable, oldValue, newValue) -> {
			try{
				consta[1]=Float.parseFloat(((String)altura.getText()));
				consta[3]=consta[1]*consta[2];
				volumen.setText(String.valueOf(consta[3]));
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
			System.out.println(consta[1]);
		});
		//////////////////////////////////////////////////////////////////////////////
		
	///Para calcular la humedad inicial
		
		
		TextField wm=(TextField)this.humedadInicial.getChildren().get(3);
		TextField ws=(TextField)this.humedadInicial.getChildren().get(5);
		TextField wf=(TextField)this.humedadInicial.getChildren().get(7);
		TextField wpercent=(TextField)this.humedadInicial.getChildren().get(9);
		
		
		
		wm.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[0]=Float.parseFloat(((String)wm.getText()));
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
		ws.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[1]=Float.parseFloat(((String)ws.getText()));
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		
		wf.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[2]=Float.parseFloat(((String)wf.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.valueOf(humIni[3]));
				
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
	//////////////////////////////////////////////////////////////////////////////////////////
		
		
		

	}
	
	
	

}
