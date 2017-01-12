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
import javafx.scene.control.Label;
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
	@FXML Label name;
	
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
	//List <Float>c1 = new ArrayList<Float>();
	
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
		int tam=tabla.getChildren().size()-1;
		
		TextField t=(TextField)tabla.getChildren().get(tam-3);//18
		
		t.textProperty().addListener((observable,lastValue,newValue)->{
			try{
				float ini=Float.parseFloat(((TextField)tabla.getChildren().get(7)).getText());
				System.out.println(ini);
				float rest=ini-(Float.parseFloat(t.getText()));
				((TextField)tabla.getChildren().get(tam-3)).setText(String.valueOf(rest));
				((TextField)tabla.getChildren().get(tam-3)).setEditable(false);
				((TextField)tabla.getChildren().get(tam-1)).setEditable(false);
				
				if (((TextField)tabla.getChildren().get(tam-2)).getText().equals("")){
					((TextField)tabla.getChildren().get(tam-1)).setText(String.valueOf(rest));
				}else{
					rest=rest-(Float.parseFloat(((TextField)tabla.getChildren().get(tam-2)).getText()));
					((TextField)tabla.getChildren().get(tam-1)).setText(String.valueOf(rest));
				}
				
			}catch(Exception ec){
				System.out.println(ec.getMessage());
			}
			
		});
		
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
		
		String info=nombre+";"+ubicacion+";"+muestra+";"+pca+";"+profundidad+"\n";
		
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
	            linea=textfile.split(";");
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
	        		linea=textfile.split(";");
	        		int tam=linea.length;
	        	    
	        		int tamactual=(temp.get(1).getChildren().size()-1)-7;
	        		System.out.println(tamactual);
	        		if (tam>7){
	        			
	        			while (tamactual<tam){
	        					        				
	        				int lastRow=temp.get(1).getChildren().size()/7;
	        				lastRow+=1;
	        				for (int i=0;i<7;i++){
	        					temp.get(1).add(new TextField(), i, lastRow);	
	        				}
	        				
	        				TextField t=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-3);
	        				System.out.println("t: "+t.contextMenuProperty());
	        				
	        				int tama=(temp.get(1).getChildren().size()-1);
	        				t.textProperty().addListener((observable,lastValue,newValue)->{
	        					
	        					try{
	        						float ini=Float.parseFloat(((TextField)temp.get(1).getChildren().get(7)).getText());
	        						
	        						float rest=ini-(Float.parseFloat(t.getText()));
	        						
	        						((TextField)temp.get(1).getChildren().get(tama-3)).setText(String.valueOf(rest));
	        						((TextField)temp.get(1).getChildren().get(tama-3)).setEditable(false);
	        						
	        						((TextField)temp.get(1).getChildren().get(tama-1)).setEditable(false);
	        						
	        						if (((TextField)temp.get(1).getChildren().get(tama-2)).getText().equals("") || ((TextField)temp.get(1).getChildren().get(tama-2)).getText().equals(" ")  ){
	        							((TextField)temp.get(1).getChildren().get(tama-1)).setText(String.valueOf(rest));
	        						}else{
	        							rest=rest-(Float.parseFloat(((TextField)temp.get(1).getChildren().get(tama-2)).getText()));
	        							((TextField)temp.get(1).getChildren().get(tama-1)).setText(String.valueOf(rest));
	        						}
	        						
	        					}catch(Exception ec){
	        						System.out.println(ec.getMessage());
	        					}
	        					
	        				});
	        				temp.get(1).setGridLinesVisible(false);
	        				temp.get(1).setGridLinesVisible(true);
	        				
	        				tam-=7;
	        			}
	        		}
	        		
	        		insertarCadena(temp.get(1),linea);
	        		
	        		textfile=scanner.nextLine();	        		
	        		linea=textfile.split(";");
	        		tam=linea.length;
	        		tamactual=(temp.get(2).getChildren().size()-1)-7;
	        		System.out.println(tamactual);
	        		if (tam>7){
	        			
	        			while (tamactual<tam){
	        					        				
	        				int lastRow=temp.get(2).getChildren().size()/7;
	        				lastRow+=1;
	        				for (int i=0;i<7;i++){
	        					temp.get(2).add(new TextField(), i, lastRow);	
	        				}
	        				temp.get(2).setGridLinesVisible(false);
	        				temp.get(2).setGridLinesVisible(true);
	        				
	        				tam-=7;
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
		GridPane carga=(GridPane)((GridPane)lizq.getItems().get(1)).getChildren().get(1);  //carga (gridpane es el 2do elemento
		GridPane descarga=(GridPane)((GridPane)lder.getItems().get(0)).getChildren().get(1);
		
		List<GridPane> temp= new ArrayList<GridPane>();
		temp.add(datos); //obtengo Gridpane de datos
		temp.add(carga);//obtengo GridPane de descarga
		temp.add(descarga);//obtengo GridPane de carga
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
					c+=temp.getText()+";";
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
	
	float []consta= new float[5]; //0>diam 1>alt 2>ar 3>vol 4>pesoAnillo
	float []humIni= new float[4]; //0>wm 1>ws 2>wf 3>wpercent
	float [] antPrueba=new float[10];//0>wma 1>wmnatural 2>humedad 3>wmnaturals 4>volmnatural 5>wamnatural 6>voltmnatural 7>volvnatural 8>vacios 9>gradSat
	float [] despPrueba= new float[11];//0>pesomanillo 1>Wm 2>pesomseca 3>Ws 4>W% 5>Vm 6>Vs 7>Vv 8>e 9>G% 10>Ww 
	final float PI=3.14159265f;
	@FXML
    public void initialize() {
		TextField gm = (TextField)this.clasificacion.getChildren().get(1);
		TextField Ss=(TextField)this.clasificacion.getChildren().get(3);
		
		TextField diametro=(TextField)this.constantes.getChildren().get(1);
		TextField altura=(TextField)this.constantes.getChildren().get(3);
		TextField area=(TextField)this.constantes.getChildren().get(5);
		TextField volumen=(TextField)this.constantes.getChildren().get(7);
		TextField pesoAnillo=(TextField)this.constantes.getChildren().get(9);
		
		//--------------------
		TextField wm=(TextField)this.humedadInicial.getChildren().get(3);
		TextField ws=(TextField)this.humedadInicial.getChildren().get(5);
		TextField wf=(TextField)this.humedadInicial.getChildren().get(7);
		TextField wpercent=(TextField)this.humedadInicial.getChildren().get(9);
		//---------------------
		TextField wma=(TextField)this.antesDeLaPrueba.getChildren().get(1);
		TextField wmnatural=(TextField)this.antesDeLaPrueba.getChildren().get(3);
		TextField hum=(TextField)this.antesDeLaPrueba.getChildren().get(5);
		TextField wmnaturals=(TextField)this.antesDeLaPrueba.getChildren().get(7);
		TextField volmnatural=(TextField)this.antesDeLaPrueba.getChildren().get(9);
		TextField pesoWw=(TextField)this.antesDeLaPrueba.getChildren().get(11);
		TextField volVm=(TextField)this.antesDeLaPrueba.getChildren().get(13);
		TextField volVv=(TextField)this.antesDeLaPrueba.getChildren().get(15);
		TextField vacios=(TextField)this.antesDeLaPrueba.getChildren().get(17);
		TextField gsat=(TextField)this.antesDeLaPrueba.getChildren().get(19);
		//--------------------------
		TextField wmsa=(TextField)this.despuesDeConsolidar.getChildren().get(1);
		TextField wmsat=(TextField)this.despuesDeConsolidar.getChildren().get(3);
		TextField wmsanillo=(TextField)this.despuesDeConsolidar.getChildren().get(5);
		TextField wms=(TextField)this.despuesDeConsolidar.getChildren().get(7);
		TextField wper=(TextField)this.despuesDeConsolidar.getChildren().get(9);
		TextField voltotmsat=(TextField)this.despuesDeConsolidar.getChildren().get(11);
		TextField volsol=(TextField)this.despuesDeConsolidar.getChildren().get(13);
		TextField volva=(TextField)this.despuesDeConsolidar.getChildren().get(15);
		TextField va=(TextField)this.despuesDeConsolidar.getChildren().get(17);
		TextField gpercent=(TextField)this.despuesDeConsolidar.getChildren().get(19);
		TextField wamuestrasat=(TextField)this.despuesDeConsolidar.getChildren().get(21);
		
		//Los listener se aplican a los input, al cambiar alguno, modificaran los cálculos
		//Listener Ss en clasificación
		nombre.textProperty().addListener((observable,oldValue,newValue)->{
		try{
			name.setText(nombre.getText());
		}catch(Exception e){}	
			
		});
		
		Ss.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float ss=Float.parseFloat(((String)Ss.getText()));
				antPrueba[4]=antPrueba[3]/ss;
				volmnatural.setText(String.valueOf(antPrueba[4]));
				
				despPrueba[6]=despPrueba[3]/ss;
				volsol.setText(String.valueOf(despPrueba[6]));
				despPrueba[7]=despPrueba[5]-despPrueba[6];
				volva.setText(String.valueOf(despPrueba[7]));
			}catch(Exception e){
				
			}
			
		});
		
		//para calcular las constantes de equipo/////////////////////////
		diametro.textProperty().addListener((observable, oldValue, newValue) -> {
		    try{
		    	consta[0]=Float.parseFloat(((String)diametro.getText()));	
		    	consta[2]=PI*consta[0]*consta[0]/4; 
		    	consta[3]=consta[1]*consta[2];
				area.setText(String.valueOf(consta[2]));
				volumen.setText(String.valueOf(consta[3]));
				gm.setText(String.valueOf(antPrueba[1]/consta[3]));
				volVm.setText(String.valueOf(consta[3]));
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
				volVm.setText(String.valueOf(consta[3]));
				gm.setText(String.valueOf(antPrueba[1]/consta[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
			System.out.println(consta[1]);
		});
		
		pesoAnillo.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				consta[4]=Float.parseFloat(((String)pesoAnillo.getText()));
				wmnatural.setText( String.valueOf(antPrueba[0]-consta[4]));		
				despPrueba[1]=despPrueba[0]-consta[4];
				despPrueba[2]=consta[4]+antPrueba[3];
				wmsat.setText(String.valueOf(despPrueba[1]));
				wmsanillo.setText(String.valueOf(despPrueba[2]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
		
		//////////////////////////////////////////////////////////////////////////////
		
	///Para calcular la humedad inicial
		wm.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[0]=Float.parseFloat(((String)wm.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
		ws.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[1]=Float.parseFloat(((String)ws.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
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
		
		//excepción
		wpercent.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				antPrueba[2]=Float.parseFloat((String)wpercent.getText());
				hum.setText(wpercent.getText());
				
				antPrueba[3]=antPrueba[1]/(1+antPrueba[2]/100);
				wmnaturals.setText(String.valueOf(antPrueba[3]));
				float ss=Float.parseFloat(((String)Ss.getText()));
				antPrueba[4]=antPrueba[3]/ss;
				volmnatural.setText(String.valueOf(antPrueba[4]));
				antPrueba[5]=antPrueba[3]*antPrueba[2]/100;//Peso(Ww)
				
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		////////////////////////////////////////////////////////////////////
		/////////////////////ANTES DE LA PRUEBA////////////////////////////
		wma.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				antPrueba[0]=Float.parseFloat(((String)wma.getText()));
				antPrueba[1]=antPrueba[0]-consta[4];
				wmnatural.setText( String.valueOf(antPrueba[1]));
				
				
				antPrueba[3]=antPrueba[1]/(1+antPrueba[2]/100);
				wmnaturals.setText(String.valueOf(antPrueba[3]));
				
				gm.setText(String.valueOf(antPrueba[1]/consta[3]));//modifica valor en clasificacion 
				
				float ss=Float.parseFloat(((String)Ss.getText()));
				antPrueba[4]=antPrueba[3]/ss;
				volmnatural.setText(String.valueOf(antPrueba[4]));
				antPrueba[5]=antPrueba[3]*antPrueba[2]/100;//Peso(Ww)
				pesoWw.setText(String.valueOf(antPrueba[5]));
				antPrueba[6]=consta[3];
				antPrueba[7]=antPrueba[6]-antPrueba[4];
				volVv.setText(String.valueOf(antPrueba[7]));
				antPrueba[8]=antPrueba[7]/antPrueba[4];
				vacios.setText(String.valueOf(antPrueba[8]));
				antPrueba[9]=(antPrueba[5]/antPrueba[7])*100;
				gsat.setText(String.valueOf(antPrueba[9]));
				volVm.setText(String.valueOf(consta[3]));
				
				despPrueba[2]=consta[4]+antPrueba[3];
				wmsanillo.setText(String.valueOf(despPrueba[2]));
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
	
		//////////////DESPUES DE LA PRUEBA////////////////////////////////
		wmsa.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				despPrueba[0]=Float.parseFloat(((String)wmsa.getText()));
				despPrueba[1]=despPrueba[0]-consta[4];
				despPrueba[2]=consta[4]+antPrueba[3];
				despPrueba[3]=antPrueba[3];
				despPrueba[4]=((despPrueba[1]-despPrueba[3])/despPrueba[3])*100;
				despPrueba[5]=consta[2]*1;//falta vincularlo con resultados
				despPrueba[6]=despPrueba[3]/Float.parseFloat(((String)Ss.getText()));
				despPrueba[7]=despPrueba[5]-despPrueba[6];
				despPrueba[8]=1;//datos de resultados
				despPrueba[10]=despPrueba[0]-despPrueba[2];
				despPrueba[9]=(despPrueba[10]/despPrueba[7])*100;
				
				wmsat.setText(String.valueOf(despPrueba[1]));
				wmsanillo.setText(String.valueOf(despPrueba[2]));
				wms.setText(String.valueOf(despPrueba[3]));
				wper.setText(String.valueOf(despPrueba[4]));
				voltotmsat.setText(String.valueOf(despPrueba[5]));
				volsol.setText(String.valueOf(despPrueba[6]));
				volva.setText(String.valueOf(despPrueba[7]));
				va.setText(String.valueOf(despPrueba[8]));
				gpercent.setText(String.valueOf(despPrueba[9]));
				wamuestrasat.setText(String.valueOf(despPrueba[10]));
			}catch(Exception e){
				
			}
		});
		
		
		///////////////////////////////////////////////////////////////////

	}
	
	
	

}
