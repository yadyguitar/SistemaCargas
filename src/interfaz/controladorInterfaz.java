package interfaz;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
import java.math.BigDecimal;

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
	
	static XYChart.Series s1 = new XYChart.Series<>();
	static XYChart.Series s2 = new XYChart.Series<>();
	static XYChart.Series s3 = new XYChart.Series<>();
	static XYChart.Series s4 = new XYChart.Series<>();
	static XYChart.Series s5 = new XYChart.Series<>();
	static XYChart.Series s6 = new XYChart.Series<>();
	static XYChart.Series s7 = new XYChart.Series<>();
	static XYChart.Series s8 = new XYChart.Series<>();
	////////
	final int NumeroCargas=8;
	//List <Float>c1 = new ArrayList<Float>();
/////////////Métodos de FXML/////////////////////////////////////////
	@FXML public void agregaFila(MouseEvent e){
		Button b=(Button)e.getSource();
		GridPane grid=(GridPane)b.parentProperty().getValue();
		GridPane tabla = (GridPane) grid.getChildren().get(1);
		int lastRow=tabla.getChildren().size()/6;
		
		lastRow+=1;
		for (int i=0;i<6;i++){
			tabla.add(new TextField(), i, lastRow);
		}
		int tam=tabla.getChildren().size()-1;
		///Parte de grafica
		SplitPane general=(SplitPane)((SplitPane)grid.getParent().getParent()).getParent().getParent();
		TitledPane carga = (TitledPane)general.getParent().getParent();
		
		SplitPane lder=(SplitPane)general.getItems().get(1);
		LineChart<Number,Number> grafica=(LineChart<Number,Number>)lder.getItems().get(1);
		
		if(grafica.getData().isEmpty()){
			switch(carga.getId()){
			case "carga1":grafica.getData().add(s1);break;
			case "carga2":grafica.getData().add(s2);break;
			case "carga3":grafica.getData().add(s3);break;
			case "carga4":grafica.getData().add(s4);break;
			case "carga5":grafica.getData().add(s5);break;
			case "carga6":grafica.getData().add(s6);break;
			case "carga7":grafica.getData().add(s7);break;
			case "carga8":grafica.getData().add(s8);break;
			default:break;
		}
			
		}
	
		
		//////////////////////////////////
		TextField mm=(TextField)tabla.getChildren().get(tam-2);//18
		
		mm.textProperty().addListener((observable,lastValue,newValue)->{
			try{
				BigDecimal ini= new BigDecimal(((TextField)tabla.getChildren().get(7)).getText());
				BigDecimal rest= ini.subtract(new BigDecimal(mm.getText()));
				((TextField)tabla.getChildren().get(tam-2)).setText(String.valueOf(rest));
				((TextField)tabla.getChildren().get(tam-2)).setEditable(false);
				((TextField)tabla.getChildren().get(tam-1)).setEditable(false);
				
				((TextField)tabla.getChildren().get(tam-1)).setText(String.valueOf(rest)); //faltaría condición de si no es "mm" multiplicarlo por 25.3
				
			}catch(Exception ec){
				System.out.println(ec.getMessage());
			}
		});
		
		TextField min=(TextField)tabla.getChildren().get(tam-3);
		min.textProperty().addListener((observable,lastValue,newValue)->{
			try{
					switch(carga.getId()){
					case "carga1": s1.getData().clear();break;
					case "carga2": s2.getData().clear();break;
					case "carga3": s3.getData().clear();break;
					case "carga4": s4.getData().clear();break;
					case "carga5": s5.getData().clear();break;
					case "carga6": s6.getData().clear();break;
					case "carga7": s7.getData().clear();break;
					case "carga8": s8.getData().clear();break;
				}
				
				//int cantidad=((tam-16)/7)+1;
				//System.out.println(cantidad);
				for (int i=14; i<tabla.getChildren().size();i+=6){
					//si hay algun error, puede estar aqui
					BigDecimal m=new BigDecimal(((TextField)tabla.getChildren().get(i)).getText());
					BigDecimal df= new BigDecimal(((TextField)tabla.getChildren().get(i+3)).getText());
					switch(carga.getId()){
						case "carga1":	s1.getData().add(new XYChart.Data(m,df));break;
						case "carga2":	s2.getData().add(new XYChart.Data(m,df));break;
						case "carga3":	s3.getData().add(new XYChart.Data(m,df));break;
						case "carga4":  s4.getData().add(new XYChart.Data(m,df));break;
						case "carga5":	s5.getData().add(new XYChart.Data(m,df));break;
						case "carga6":	s6.getData().add(new XYChart.Data(m,df));break;
						case "carga7":	s7.getData().add(new XYChart.Data(m,df));break;
						case "carga8":	s8.getData().add(new XYChart.Data(m,df));break;
						default:break;
					}
				}
			}catch(Exception ec){
				System.out.println(ec.getMessage());
			}
		});
		
		TextField def=(TextField)tabla.getChildren().get(tam);
		def.textProperty().addListener((observable,lastValue,newValue)->{
			try{
				try{
					switch(carga.getId()){
					case "carga1": s1.getData().clear();break;
					case "carga2": s2.getData().clear();break;
					case "carga3": s3.getData().clear();break;
					case "carga4": s4.getData().clear();break;
					case "carga5": s5.getData().clear();break;
					case "carga6": s6.getData().clear();break;
					case "carga7": s7.getData().clear();break;
					case "carga8": s8.getData().clear();break;
				}
				
				//int cantidad=((tam-16)/7)+1;
				//System.out.println(cantidad);
				for (int i=14; i<tabla.getChildren().size();i+=6){
					BigDecimal m= new BigDecimal(((TextField)tabla.getChildren().get(i)).getText());
					BigDecimal df=new BigDecimal(((TextField)tabla.getChildren().get(i+3)).getText());
					switch(carga.getId()){
						case "carga1":	s1.getData().add(new XYChart.Data(m,df));break;
						case "carga2":	s2.getData().add(new XYChart.Data(m,df));break;
						case "carga3":	s3.getData().add(new XYChart.Data(m,df));break;
						case "carga4":  s4.getData().add(new XYChart.Data(m,df));break;
						case "carga5":	s5.getData().add(new XYChart.Data(m,df));break;
						case "carga6":	s6.getData().add(new XYChart.Data(m,df));break;
						case "carga7":	s7.getData().add(new XYChart.Data(m,df));break;
						case "carga8":	s8.getData().add(new XYChart.Data(m,df));break;
						default:break;
					}
				}
			}catch(Exception ec){
				System.out.println(ec.getMessage());
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
	            //Para cargas// Carga 1
	            if(cont==8){
	            	auxAbrirCargas(this.carga1,this.s1,scanner,linea);
	            }
	            //para carga 2
	            if(cont==9){
	            	auxAbrirCargas(this.carga2,this.s2,scanner,linea);
	            	
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
	
	BigDecimal []consta= new BigDecimal[5]; //0>diam 1>alt 2>ar 3>vol 4>pesoAnillo
	BigDecimal []humIni= new BigDecimal[4]; //0>wm 1>ws 2>wf 3>wpercent
	BigDecimal [] antPrueba=new BigDecimal[10];//0>wma 1>wmnatural 2>humedad 3>wmnaturals 4>volmnatural 5>wamnatural 6>voltmnatural 7>volvnatural 8>vacios 9>gradSat
	BigDecimal [] despPrueba= new BigDecimal[11];//0>pesomanillo 1>Wm 2>pesomseca 3>Ws 4>W% 5>Vm 6>Vs 7>Vv 8>e 9>G% 10>Ww 
	final BigDecimal PI= new BigDecimal("3.14159265");
	
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
				BigDecimal ss = new BigDecimal(Ss.getText());
				
				//float ss=Float.parseFloat(((String)Ss.getText()));
				
				antPrueba[4]= antPrueba[3].divide(ss);
				
				volmnatural.setText(String.valueOf(antPrueba[4]));
				despPrueba[6]=despPrueba[3].divide(ss);
				
				volsol.setText(String.valueOf(despPrueba[6]));
				despPrueba[7]=despPrueba[5].subtract(despPrueba[6]);
				volva.setText(String.valueOf(despPrueba[7]));
			}catch(Exception e){
				
			}
			
		});
		
		//para calcular las constantes de equipo/////////////////////////
		diametro.textProperty().addListener((observable, oldValue, newValue) -> {
		    try{
		    	consta[0]= new BigDecimal(diametro.getText());
		    	consta[2]=PI.multiply((consta[0].pow(2)).divide(new BigDecimal("4")));//consta[2]=PI*consta[0]²/4
		    	consta[3]=consta[1].multiply(consta[2]);
		    	
				area.setText(String.valueOf(consta[2]));
				volumen.setText(String.valueOf(consta[3]));
				gm.setText(String.valueOf(antPrueba[1].divide(consta[3])));
				volVm.setText(String.valueOf(consta[3]));
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.println("error, letras");
			}
		    System.out.println(consta[0]);
		});
		
		altura.textProperty().addListener((observable, oldValue, newValue) -> {
			try{
				consta[1]= new BigDecimal(altura.getText());
				consta[3]=consta[1].multiply(consta[2]);
				volumen.setText(String.valueOf(consta[3]));
				volVm.setText(String.valueOf(consta[3]));
				gm.setText(String.valueOf(antPrueba[1].divide(consta[3])));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
			System.out.println(consta[1]);
		});
		
		pesoAnillo.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				consta[4]= new BigDecimal(pesoAnillo.getText());
				
				wmnatural.setText( String.valueOf(antPrueba[0].subtract(consta[4])));	
				despPrueba[1]=despPrueba[0].subtract(consta[4]);
				despPrueba[2]=consta[4].add(antPrueba[3]);
									
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
				humIni[0]= new BigDecimal(wm.getText());
				humIni[3]=((humIni[0].subtract(humIni[1])).divide((humIni[1].subtract(humIni[2])))).multiply(new BigDecimal("100")); //((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
		ws.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[1]=new BigDecimal(ws.getText());
				humIni[3]=((humIni[0].subtract(humIni[1])).divide((humIni[1].subtract(humIni[2])))).multiply(new BigDecimal("100")); //((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		
		wf.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[2]= new BigDecimal(wf.getText());
				humIni[3]=((humIni[0].subtract(humIni[1])).divide((humIni[1].subtract(humIni[2])))).multiply(new BigDecimal("100")); //((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.valueOf(humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error, letras");
			}
		});
		
		//excepción
		wpercent.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				antPrueba[2] = new BigDecimal(wpercent.getText());
				hum.setText(wpercent.getText());
				antPrueba[3]= antPrueba[1].divide( (antPrueba[2].add(new BigDecimal("1"))).divide(new BigDecimal("100"))  ); //antPrueba[3]=antPrueba[1]/(1+antPrueba[2]/100);
				wmnaturals.setText(String.valueOf(antPrueba[3]));
				BigDecimal ss= new BigDecimal(Ss.getText());
				antPrueba[4]=antPrueba[3].divide(ss);
				volmnatural.setText(String.valueOf(antPrueba[4]));
				antPrueba[5]= (antPrueba[3].multiply(antPrueba[2])).divide(new BigDecimal("100"));//antPrueba[5]=antPrueba[3]*antPrueba[2]/100;//Peso(Ww)
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
		////////////////////////////////////////////////////////////////////
		/////////////////////ANTES DE LA PRUEBA////////////////////////////
		wma.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				antPrueba[0]= new BigDecimal(wma.getText());
				antPrueba[1]= antPrueba[0].subtract(consta[4]);
				
				wmnatural.setText( String.valueOf(antPrueba[1]));
				
				
				antPrueba[3]= antPrueba[1].divide( (antPrueba[2].add(new BigDecimal("1"))).divide(new BigDecimal("100"))  ); //antPrueba[3]=antPrueba[1]/(1+antPrueba[2]/100);
				wmnaturals.setText(String.valueOf(antPrueba[3]));
				
				gm.setText(String.valueOf(antPrueba[1].divide(consta[3])));//modifica valor en clasificacion 
				
				BigDecimal ss= new BigDecimal(Ss.getText());
				
				antPrueba[4]=antPrueba[3].divide(ss);
				volmnatural.setText(String.valueOf(antPrueba[4]));
				antPrueba[5]= (antPrueba[3].multiply(antPrueba[2])).divide(new BigDecimal("100"));//antPrueba[5]=antPrueba[3]*antPrueba[2]/100;//Peso(Ww)
				pesoWw.setText(String.valueOf(antPrueba[5]));
				antPrueba[6]=consta[3];
				antPrueba[7]=antPrueba[6].subtract(antPrueba[4]);
				volVv.setText(String.valueOf(antPrueba[7]));
				antPrueba[8]=antPrueba[7].divide(antPrueba[4]);
			
				vacios.setText(String.valueOf(antPrueba[8]));
				antPrueba[9]=(antPrueba[5].divide(antPrueba[7])).multiply(new BigDecimal("100"));
				gsat.setText(String.valueOf(antPrueba[9]));
				volVm.setText(String.valueOf(consta[3]));
				despPrueba[2]=consta[4].add(antPrueba[3]);
				
				wmsanillo.setText(String.valueOf(despPrueba[2]));
			}catch (Exception e) {
				// TODO: handle exception
			}
		});
	
		//////////////DESPUES DE LA PRUEBA////////////////////////////////
		wmsa.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				despPrueba[0] = new BigDecimal(wmsa.getText());
				despPrueba[1]= despPrueba[0].subtract(consta[4]);
				despPrueba[2]=consta[4].add(antPrueba[3]);
				despPrueba[3]=antPrueba[3];
				despPrueba[4]=((despPrueba[1].subtract(despPrueba[3])).divide(despPrueba[3])).multiply(new BigDecimal("100"));//despPrueba[4]=((despPrueba[1]-despPrueba[3])/despPrueba[3])*100;
				despPrueba[5]= consta[2].multiply( new BigDecimal("1"));//falta vincularlo con resultados
				despPrueba[6]=despPrueba[3].divide(new BigDecimal(Ss.getText()));
				despPrueba[7]=despPrueba[5].subtract(despPrueba[6]);
				despPrueba[8]= new BigDecimal("1");//datos de resultados
				despPrueba[10]=despPrueba[0].subtract(despPrueba[2]);
				despPrueba[9]=(despPrueba[10].divide(despPrueba[7])).multiply(new BigDecimal("100"));//despPrueba[9]=(despPrueba[10]/despPrueba[7])*100;
				
				
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
		
		
		//Datos///
		
		GridPane in1=(GridPane)((GridPane)((SplitPane)((SplitPane)carga1.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in2=(GridPane)((GridPane)((SplitPane)((SplitPane)carga2.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in3=(GridPane)((GridPane)((SplitPane)((SplitPane)carga3.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in4=(GridPane)((GridPane)((SplitPane)((SplitPane)carga4.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in5=(GridPane)((GridPane)((SplitPane)((SplitPane)carga5.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in6=(GridPane)((GridPane)((SplitPane)((SplitPane)carga6.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in7=(GridPane)((GridPane)((SplitPane)((SplitPane)carga7.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in8=(GridPane)((GridPane)((SplitPane)((SplitPane)carga8.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		
		TextField i1=(TextField)in1.getChildren().get(1);
		TextField i2=(TextField)in2.getChildren().get(1);
		TextField i3=(TextField)in3.getChildren().get(1);
		TextField i4=(TextField)in4.getChildren().get(1);
		TextField i5=(TextField)in5.getChildren().get(1);
		TextField i6=(TextField)in6.getChildren().get(1);
		TextField i7=(TextField)in7.getChildren().get(1);
		TextField i8=(TextField)in8.getChildren().get(1);
		
		i1.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				((TextField)in1.getChildren().get(5)).setText(i1.getText());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		i2.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in1.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i2.getText()));
				
				((TextField)in2.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		i3.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in2.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i3.getText()));
				
				((TextField)in3.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		i4.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in3.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i4.getText())); 
				((TextField)in4.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		i5.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in4.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i5.getText()));
				((TextField)in5.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		i6.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in5.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i6.getText()));
				((TextField)in6.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		i7.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in6.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i7.getText()));
				((TextField)in7.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		i8.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				BigDecimal c = new BigDecimal(((TextField)in7.getChildren().get(5)).getText());
				BigDecimal sum=c.add(new BigDecimal(i8.getText()));
				((TextField)in8.getChildren().get(5)).setText(String.valueOf(sum));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		});
		
		//descargas///////
		GridPane d1= (GridPane)((GridPane)((SplitPane)((SplitPane)carga1.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d2= (GridPane)((GridPane)((SplitPane)((SplitPane)carga2.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d3= (GridPane)((GridPane)((SplitPane)((SplitPane)carga3.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d4= (GridPane)((GridPane)((SplitPane)((SplitPane)carga4.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d5= (GridPane)((GridPane)((SplitPane)((SplitPane)carga5.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d6= (GridPane)((GridPane)((SplitPane)((SplitPane)carga6.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d7= (GridPane)((GridPane)((SplitPane)((SplitPane)carga7.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		GridPane d8= (GridPane)((GridPane)((SplitPane)((SplitPane)carga8.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1);
		
		descargas(d8,d7);
		descargas(d7,d6);
		descargas(d6,d5);
		descargas(d5,d4);
		descargas(d4,d3);
		descargas(d3,d2);
		descargas(d2,d1);
			
		
		///////////////////////////////////////////////////////////////////

	}
 @FXML void actualiza(MouseEvent e){

	 TitledPane cargaTemporal=(TitledPane)e.getSource();
	SplitPane splitTemporal=(SplitPane)cargaTemporal.getContent();
	System.out.println(splitTemporal);
	splitTemporal.autosize();
 }
 
	void auxAbrirCargas( TitledPane carga, XYChart.Series s,Scanner scanner,String []linea){
		List<GridPane> temp = getCadenaCargas(carga); //obtengo un List de GridPanes, de los elementos de cada carga
		insertarCadena(temp.get(0),linea);
		String textfile=scanner.nextLine();
        linea=textfile.split(";");
        
        
		int tam=linea.length;
	    
		int tamactual=(temp.get(1).getChildren().size()-1)-6;
		System.out.println(tamactual);
		if (tam>6){
			while (tamactual<tam){
				int lastRow=temp.get(1).getChildren().size()/6;
				lastRow+=1;
				for (int i=0;i<6;i++){
					temp.get(1).add(new TextField(), i, lastRow);	
				}

				TextField t=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-2);
				
				int tama=(temp.get(1).getChildren().size()-1);
				t.textProperty().addListener((observable,lastValue,newValue)->{
					try{
						
						BigDecimal ini = new BigDecimal(((TextField)temp.get(1).getChildren().get(7)).getText());
						BigDecimal rest= ini.subtract(new BigDecimal(t.getText()));
						
						((TextField)temp.get(1).getChildren().get(tama-2)).setText(String.valueOf(rest));
						((TextField)temp.get(1).getChildren().get(tama-2)).setEditable(false);
						((TextField)temp.get(1).getChildren().get(tama-1)).setEditable(false);
						
						((TextField)temp.get(1).getChildren().get(tama-1)).setText(String.valueOf(rest));
						
					}catch(Exception ec){
						System.out.println(ec.getMessage());
					}
					
				});
				temp.get(1).setGridLinesVisible(false);
				temp.get(1).setGridLinesVisible(true);
				
				//aqui la parte de la grafica
				SplitPane lder=(SplitPane)((SplitPane)carga.getContent()).getItems().get(1);
				LineChart<Number,Number> grafica=(LineChart<Number,Number>)lder.getItems().get(1);
				
				if(!grafica.getData().contains(s)){
					grafica.getData().add(s); //porque es carga 1
				}
				TextField tiempo=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-4);
				
				tiempo.textProperty().addListener((observable,lastValue,newValue)->{
					try{
					s.getData().clear();
					//int cantidad=((tam-16)/7)+1;
					//System.out.println(cantidad);
					for (int i=14; i<temp.get(1).getChildren().size();i+=6){
						float m=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i)).getText());
						float df=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i+3)).getText());
						s.getData().add(new XYChart.Data(m,df));
					}
				}catch(Exception ec){
					System.out.println(ec.getMessage());
				}
				});
				//el tam de los nodos, tienen un elemento de mas, quien sabe porque :I
				TextField def=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-1);
				def.textProperty().addListener((observable,lastValue,newValue)->{
					try{
					s.getData().clear();
					//int cantidad=((tam-16)/7)+1;
					//System.out.println(cantidad);
					for (int i=14; i<temp.get(1).getChildren().size();i+=6){
						BigDecimal m = new BigDecimal(((TextField)temp.get(1).getChildren().get(i)).getText());
						BigDecimal df= new BigDecimal(((TextField)temp.get(1).getChildren().get(i+3)).getText());
						s.getData().add(new XYChart.Data(m,df));
					}
				}catch(Exception ec){
					System.out.println(ec.getMessage());
				}
				});
				
				
				tam-=6;
			}
		}
		
		insertarCadena(temp.get(1),linea);
		
		textfile=scanner.nextLine();	        		
		linea=textfile.split(";");
		tam=linea.length;
		tamactual=(temp.get(2).getChildren().size()-1)-6;
		System.out.println(tamactual);
		if (tam>6){
			while (tamactual<tam){
					        				
				int lastRow=temp.get(2).getChildren().size()/6;
				lastRow+=1;
				for (int i=0;i<6;i++){
					temp.get(2).add(new TextField(), i, lastRow);	
				}
				temp.get(2).setGridLinesVisible(false);
				temp.get(2).setGridLinesVisible(true);
				
				tam-=6;
			}
		}
		insertarCadena(temp.get(2),linea);
	}
	
	void descargas(GridPane d, GridPane d2){
		TextField c=(TextField)d.getChildren().get(15);
		
		c.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				((TextField)d2.getChildren().get(7)).setText(c.getText());
				BigDecimal res= (new BigDecimal(c.getText())).subtract(new BigDecimal(((TextField)d.getChildren().get(7)).getText()));
				((TextField)d.getChildren().get(16)).setText(String.valueOf(res));
				((TextField)d.getChildren().get(17)).setText(String.valueOf(res));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
		});
	}

}
