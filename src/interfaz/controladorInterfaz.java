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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.data.xy.XYSeriesCollection; 

public class controladorInterfaz implements Initializable{

	//FXML//
	
	@FXML StackPane root;
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
	
	@FXML GridPane infoResultados;
	@FXML GridPane descarga;
	@FXML Tab tabResultados;
	//@FXML LineChart<String,Number> curvaCompresibilidad;
	@FXML StackPane graficaCompresibilidad;
	
	int bandResultados=0;
	JFreeChart grafica;
	static XYSeriesCollection s1 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s2 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s3 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s4 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s5 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s6 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s7 = new XYSeriesCollection(new XYSeries(""));
	static XYSeriesCollection s8 = new XYSeriesCollection(new XYSeries(""));
	
	static XYSeries resCarga=new XYSeries("Carga");
	static XYSeries resDescarga=new XYSeries("Descarga");
	
	static XYSeriesCollection resultados=new XYSeriesCollection();
	
	
	
	////////
	final int NumeroCargas=8;
	//List <Float>c1 = new ArrayList<Float>();
	
	
	
	//////variables iniciales para cálculos copn listenners
	float []consta= new float[6]; //0>diam 1>alt 2>ar 3>vol 4>pesoAnillo 5>constanteEquipo
	float []humIni= new float[4]; //0>wm 1>ws 2>wf 3>wpercent
	float [] antPrueba=new float[10];//0>wma 1>wmnatural 2>humedad 3>wmnaturals 4>volmnatural 5>wamnatural 6>voltmnatural 7>volvnatural 8>vacios 9>gradSat
	float [] despPrueba= new float[11];//0>pesomanillo 1>Wm 2>pesomseca 3>Ws 4>W% 5>Vm 6>Vs 7>Vv 8>e 9>G% 10>Ww 
	final float PI=3.14159265f;
	////////
	///metodo para creación de graficas de JFreeChart
	private SwingNode creaChart(){ 
		 JFreeChart chart = ChartFactory.createXYLineChart( null, // chart title 
		                                                   "X", // x axis label 
		                                                   "Y", // y axis label 
		                                                   null, // data 
		                                                   PlotOrientation.VERTICAL, true, // include legend 
		                                                   true, // tooltips 
		                                                   false // urls 
		 );
		 
		 chart.getXYPlot().setDomainAxis(new LogarithmicAxis("t, min"));
		 chart.getXYPlot().setRangeAxis(new org.jfree.chart.axis.NumberAxis("deform, mm"));
		 chart.getXYPlot().getRangeAxis().setInverted(true);
		 ChartPanel chartPane = new ChartPanel(chart); 
		 SwingNode sNode = new SwingNode(); 
		 sNode.setContent(chartPane); 
		 return sNode; 
	 } 
	
	
	
/////////////Métodos de FXML/////////////////////////////////////////
	@FXML public void prueba(){
		if(tabResultados.isSelected()){
			ProgressIndicator pi = new ProgressIndicator();
            VBox box = new VBox(pi);
            
            box.setAlignment(Pos.CENTER);
            box.setVisible(true);
            // Grey Background
            border.setDisable(true);
            root.getChildren().add(box);
            
			Task<Void> task = new Task<Void>(){
				@Override public Void call(){
		             while(true){
		            	 System.out.println("holis");
		            	 
		            	 if(bandResultados==1){
		            		 System.out.println("Entro en la condicional");
		            		// border.setDisable(false);            	
		            		 root.getChildren().get(1).setVisible(false);
		         			 System.out.println("Termina");
		         			 bandResultados=0;
		         			 break;
		         			 
		            	 }
		             }
		             return null;
				}
			};
		new Thread(task).start();
		auxPrueba(carga1);
		auxPrueba(carga2);
		auxPrueba(carga3);
		auxPrueba(carga4);
		auxPrueba(carga5);
		auxPrueba(carga6);
		auxPrueba(carga7);
		auxPrueba(carga8);
		System.out.println("Llego aqui");
		bandResultados=1;
		
		
			
			
		
			
		}
	}
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
		
		StackPane contenedorGrafica = (StackPane)lder.getItems().get(1);
		ChartPanel chartPane=(ChartPanel)((SwingNode)(contenedorGrafica.getChildren().get(0))).getContent();
		JFreeChart chart = chartPane.getChart();
				
		if(chart.getPlot().getDatasetGroup()==null){
			switch(carga.getId()){
			case "carga1":chart.getXYPlot().setDataset(s1);break;
			case "carga2":chart.getXYPlot().setDataset(s2);break;
			case "carga3":chart.getXYPlot().setDataset(s3);break;
			case "carga4":chart.getXYPlot().setDataset(s4);break;
			case "carga5":chart.getXYPlot().setDataset(s5);break;
			case "carga6":chart.getXYPlot().setDataset(s6);break;
			case "carga7":chart.getXYPlot().setDataset(s7);break;
			case "carga8":chart.getXYPlot().setDataset(s8);break;
			default:break;
		}
			
		}
	
		
		//////////////////////////////////
		TextField mm=(TextField)tabla.getChildren().get(tam-2);//18
		
		mm.textProperty().addListener((observable,lastValue,newValue)->{
			try{
				float ini=Float.parseFloat(((TextField)tabla.getChildren().get(7)).getText());
				System.out.println(ini);
				float rest=ini-(Float.parseFloat(mm.getText()));
				((TextField)tabla.getChildren().get(tam-2)).setText(String.format("%.3f",rest));
				((TextField)tabla.getChildren().get(tam-2)).setEditable(false);
				((TextField)tabla.getChildren().get(tam-1)).setEditable(false);
				((TextField)tabla.getChildren().get(tam-1)).setText(String.format("%.3f",rest)); //faltaría condición de si no es "mm" multiplicarlo por 25.3
			}catch(Exception ec){
				System.out.println("Error en función agregaFila (mm): "+ec.getMessage());
			}});
		
		TextField min=(TextField)tabla.getChildren().get(tam-3);
		min.textProperty().addListener((observable,lastValue,newValue)->{
			try{
					switch(carga.getId()){
					case "carga1": ((XYSeries)s1.getSeries().get(0)).clear();break;
					case "carga2": ((XYSeries)s2.getSeries().get(0)).clear();break;
					case "carga3": ((XYSeries)s3.getSeries().get(0)).clear();break;
					case "carga4": ((XYSeries)s4.getSeries().get(0)).clear();break;
					case "carga5": ((XYSeries)s5.getSeries().get(0)).clear();break;
					case "carga6": ((XYSeries)s6.getSeries().get(0)).clear();break;
					case "carga7": ((XYSeries)s7.getSeries().get(0)).clear();break;
					case "carga8": ((XYSeries)s8.getSeries().get(0)).clear();break;
				}
				
				//int cantidad=((tam-16)/7)+1;
				//System.out.println(cantidad);
				for (int i=14; i<tabla.getChildren().size();i+=6){
					try{float m=Float.parseFloat(((TextField)tabla.getChildren().get(i)).getText());
					float df=Float.parseFloat(((TextField)tabla.getChildren().get(i+3)).getText());
					switch(carga.getId()){
						case "carga1":	((XYSeries)s1.getSeries().get(0)).add(m,df);break;
						case "carga2":	((XYSeries)s2.getSeries().get(0)).add(m,df);break;
						case "carga3":	((XYSeries)s3.getSeries().get(0)).add(m,df);break;
						case "carga4":  ((XYSeries)s4.getSeries().get(0)).add(m,df);break;
						case "carga5":	((XYSeries)s5.getSeries().get(0)).add(m,df);break;
						case "carga6":	((XYSeries)s6.getSeries().get(0)).add(m,df);break;
						case "carga7":	((XYSeries)s7.getSeries().get(0)).add(m,df);break;
						case "carga8":	((XYSeries)s8.getSeries().get(0)).add(m,df);break;
						default:break;
					}}catch(Exception ec){}
				}
			}catch(Exception ec){
				System.out.println("Error en función agregaFila (min): "+ec.getMessage());
			}
		});
		
		TextField def=(TextField)tabla.getChildren().get(tam);
		def.textProperty().addListener((observable,lastValue,newValue)->{
			try{
				try{
					switch(carga.getId()){
					case "carga1": ((XYSeries)s1.getSeries().get(0)).clear();break;
					case "carga2": ((XYSeries)s2.getSeries().get(0)).clear();break;
					case "carga3": ((XYSeries)s3.getSeries().get(0)).clear();break;
					case "carga4": ((XYSeries)s4.getSeries().get(0)).clear();break;
					case "carga5": ((XYSeries)s5.getSeries().get(0)).clear();break;
					case "carga6": ((XYSeries)s6.getSeries().get(0)).clear();break;
					case "carga7": ((XYSeries)s7.getSeries().get(0)).clear();break;
					case "carga8": ((XYSeries)s8.getSeries().get(0)).clear();break;
				}
				
				//int cantidad=((tam-16)/7)+1;
				//System.out.println(cantidad);
				for (int i=14; i<tabla.getChildren().size();i+=6){
					try{float m=Float.parseFloat(((TextField)tabla.getChildren().get(i)).getText());
					float df=Float.parseFloat(((TextField)tabla.getChildren().get(i+3)).getText());
					switch(carga.getId()){
					case "carga1":	((XYSeries)s1.getSeries().get(0)).add(m,df);break;
					case "carga2":	((XYSeries)s2.getSeries().get(0)).add(m,df);break;
					case "carga3":	((XYSeries)s3.getSeries().get(0)).add(m,df);break;
					case "carga4":  ((XYSeries)s4.getSeries().get(0)).add(m,df);break;
					case "carga5":	((XYSeries)s5.getSeries().get(0)).add(m,df);break;
					case "carga6":	((XYSeries)s6.getSeries().get(0)).add(m,df);break;
					case "carga7":	((XYSeries)s7.getSeries().get(0)).add(m,df);break;
					case "carga8":	((XYSeries)s8.getSeries().get(0)).add(m,df);break;
						default:break;
					}}catch(Exception ec){}
				}
			}catch(Exception ec){
				System.out.println("Error en agregaFila (def): "+ec.getMessage());
			}
				
								
				float maximo = Float.parseFloat(((TextField)tabla.getChildren().get(11)).getText());
				float minimo =Float.parseFloat(((TextField)tabla.getChildren().get(11)).getText());
				for (int i=11;i<tabla.getChildren().size();i+=6){
					if(!((TextField)tabla.getChildren().get(i)).getText().isEmpty()){
					float temp=Float.parseFloat(((TextField)tabla.getChildren().get(i)).getText());
					if(carga.getId().equals("carga2")){
						if(minimo>temp){
							minimo=temp;
						}
					}else{
						if (maximo<temp){
							maximo=temp;
						}	
					}
					}
					
				}
				
				//no puedo no se porque rayos obtener la variable global descarga, asi que lo que hago a continuación es adquirirla mediante padres
				TabPane tabpane=(TabPane)returnElement(grid,12);
				Tab resultados=(Tab)tabpane.getTabs().get(4);
				TitledPane tablaCalculos=(TitledPane)((VBox)((ScrollPane)resultados.getContent()).getContent()).getChildren().get(0);
				GridPane tablaResultados=(GridPane)((GridPane)((SplitPane)tablaCalculos.getContent()).getItems().get(1)).getChildren().get(0);
				
				switch(carga.getId()){
						case "carga1":	((TextField)tablaResultados.getChildren().get(21)).setText(String.format("%.3f",maximo)); 
						((TextField)tablaResultados.getChildren().get(22)).setText(String.format("%.3f",maximo));break;
						case "carga2":	((TextField)tablaResultados.getChildren().get(30)).setText(String.format("%.3f",minimo));
						((TextField)tablaResultados.getChildren().get(31)).setText(String.format("%.3f",minimo));break;
						case "carga3":	((TextField)tablaResultados.getChildren().get(39)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(40)).setText(String.format("%.3f",maximo));break;
						case "carga4":  ((TextField)tablaResultados.getChildren().get(48)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(49)).setText(String.format("%.3f",maximo));break;
						case "carga5":	((TextField)tablaResultados.getChildren().get(57)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(58)).setText(String.format("%.3f",maximo));break;
						case "carga6":	((TextField)tablaResultados.getChildren().get(66)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(67)).setText(String.format("%.3f",maximo));break;
						case "carga7":	((TextField)tablaResultados.getChildren().get(75)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(76)).setText(String.format("%.3f",maximo));break;
						case "carga8":	((TextField)tablaResultados.getChildren().get(84)).setText(String.format("%.3f",maximo));
						((TextField)tablaResultados.getChildren().get(85)).setText(String.format("%.3f",maximo));break;
					default:break;
				}
				//
				try{
				for (int i=23;i<tablaResultados.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)tablaResultados.getChildren().get(i-1)).getText()));
				}}catch(Exception ec){
					System.out.println("Error en función agregaFila (for->auxVv)"+ec.getMessage());
				}
				
				float sum1=0;
				float sum2=0;
				for (int i=13;i<=154;i+=9){
					if(i>85)
						sum2+=(Float.parseFloat(((TextField)tablaResultados.getChildren().get(i)).getText()));
					else
						sum1+=(Float.parseFloat(((TextField)tablaResultados.getChildren().get(i)).getText()));
				}
				TextField alturaFinal=(TextField)this.infoResultados.getChildren().get(5);
				float alturaInicial=Float.parseFloat(((TextField)this.infoResultados.getChildren().get(3)).getText());
				float resultado=alturaInicial-sum1/10+sum2/10;
				alturaFinal.setText(String.format("%.3f",resultado));
				
			}catch(Exception ec){
				System.out.println("error en función agregaFila: "+ec.getMessage());
			}
		});
		//System.out.println(grid.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent());
	
		
		tabla.setGridLinesVisible(false);
		tabla.setGridLinesVisible(true);
	}

//Función recursiva que me regresa el elemento padre (TabPane) para poder acceder a otros datos
	Node returnElement(Node elemento,int cont){
		if (cont<=0)
			return elemento;
		return returnElement(elemento.getParent(),--cont);
	}
	
	void graficaResultados(){
		((XYSeries)this.resultados.getSeries().get(0)).clear();
		((XYSeries)this.resultados.getSeries().get(1)).clear();
	
		for(int i=11;i<147;i+=9){
			if (i>=92){
				float descargaX=Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText());
				float descargaY=Float.parseFloat(((TextField)descarga.getChildren().get(i+4)).getText());
				((XYSeries)this.resultados.getSeries().get(1)).add(descargaX,descargaY);
			}else{
				if(i==83){
					float descargaX=Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText());
					float descargaY=Float.parseFloat(((TextField)descarga.getChildren().get(i+4)).getText());
					((XYSeries)this.resultados.getSeries().get(1)).add(descargaX,descargaY);
				}
				float cargaX=Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText());
				float cargaY=Float.parseFloat(((TextField)descarga.getChildren().get(i+4)).getText());
				((XYSeries)this.resultados.getSeries().get(0)).add(cargaX,cargaY);
			}
		}
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
		String infoResultadosString=cadena(this.infoResultados);
		
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
        	SaveFile(""+info+granulometria+limites+clasificacion+constantes+humedadInicial+antesDeLaPrueba+despuesDeConsolidar+cargas+infoResultadosString,file);
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
	            
	            switch(cont){
		            case 0:
		            	this.nombre.setText(linea[0]);
			            this.ubicacion.setText(linea[1]);
			            this.muestra.setText(linea[2]);
			            this.pca.setText(linea[3]);
			            this.profundidad.setText(linea[4]);
		            	break;
		            case 1: //cont=1 es Granulometría
		            	insertarCadena( this.granulometria, linea);
		            	break;
		            case 2://cont=2 es limites
		            	insertarCadena(this.limites,linea);
		            	break;
		            case 3: //cont=3 es clasificacion
		            	insertarCadena(this.clasificacion,linea);
		            	break;
		            case 4://constantes del equipo
		            	insertarCadena(this.constantes,linea);
		            	break;
		            case 5://Humedad inicial
		            	insertarCadena(this.humedadInicial,linea);
		            	break;
		            case 6: //antesdeLaPrueba
		            	insertarCadena(this.antesDeLaPrueba,linea);
		            	break;
		            case 7: //despuesDeConsolidar
		            	insertarCadena(this.despuesDeConsolidar,linea);
		            	break;
		            case 8://Para cargas// Carga 1
		            	auxAbrirCargas(this.carga1,controladorInterfaz.s1,scanner,linea);
		            	break;
		            case 9: //para carga 2
		            	auxAbrirCargas(this.carga2,controladorInterfaz.s2,scanner,linea);
		            	break;
		            case 10://PARA carga 3
		            	auxAbrirCargas(this.carga3,controladorInterfaz.s3,scanner,linea);
		            	break;
		            case 11://PARA carga 4
		            	auxAbrirCargas(this.carga4,controladorInterfaz.s4,scanner,linea);
		            	break;
		            case 12://PARA carga 5
		            	auxAbrirCargas(this.carga5,controladorInterfaz.s5,scanner,linea);
		            	break;
		            case 13://PARA carga 6
		            	auxAbrirCargas(this.carga6,controladorInterfaz.s6,scanner,linea);
		            	break;
		            case 14://PARA carga 7
		            	auxAbrirCargas(this.carga7,controladorInterfaz.s7,scanner,linea);
		            	break;
		            case 15://PARA carga 8
		            	auxAbrirCargas(this.carga8,controladorInterfaz.s8,scanner,linea);
		            	break;
		            case 16://PARA carga 8
		            	insertarCadena(this.infoResultados,linea);
		            	break;
		            default : break;
	            }
	            
	            
	            cont++;
			}
			reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga8.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga7.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga6.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga5.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga4.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga3.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga2.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
            reactiva((TextField)((GridPane)((GridPane)((SplitPane)((SplitPane)carga1.getContent()).getItems().get(1)).getItems().get(0)).getChildren().get(1)).getChildren().get(15));
	    } catch (Exception e) {
	        System.out.println("Error en función abrir: "+e.getMessage());
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
            System.out.println("Error en función SaveFile: "+ex.getMessage());
        }
         
    }
	
	void generaGraficas(){
		StackPane content1=((StackPane)((SplitPane)((SplitPane)carga1.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content2=((StackPane)((SplitPane)((SplitPane)carga2.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content3=((StackPane)((SplitPane)((SplitPane)carga3.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content4=((StackPane)((SplitPane)((SplitPane)carga4.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content5=((StackPane)((SplitPane)((SplitPane)carga5.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content6=((StackPane)((SplitPane)((SplitPane)carga6.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content7=((StackPane)((SplitPane)((SplitPane)carga7.getContent()).getItems().get(1)).getItems().get(1));
		StackPane content8=((StackPane)((SplitPane)((SplitPane)carga8.getContent()).getItems().get(1)).getItems().get(1));
		
		content1.getChildren().add(this.creaChart());
		content2.getChildren().add(this.creaChart());
		content3.getChildren().add(this.creaChart());
		content4.getChildren().add(this.creaChart());
		content5.getChildren().add(this.creaChart());
		content6.getChildren().add(this.creaChart());
		content7.getChildren().add(this.creaChart());
		content8.getChildren().add(this.creaChart());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try{
			
			generaFilasResultados();
			initialize();
			generaGraficas();
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	
				
	}
	
	void generaFilasResultados(){ //genera los TextFields de la tabla de resultados (descargas)
		try{
			for(int row=1; row<17;row++){
				descarga.setGridLinesVisible(false);
				descarga.setGridLinesVisible(true);
				for (int column=0; column<9; column++){
					descarga.add(new TextField(), column, row);
				}
				descarga.setGridLinesVisible(false);
				descarga.setGridLinesVisible(true);
			}
				//////condiciones/////////
				//valores iniciales
				((TextField)descarga.getChildren().get(9)).setText("0.01");
				
				TextField deltaQ=((TextField)descarga.getChildren().get(9));
				TextField sigmaQ=((TextField)descarga.getChildren().get(10));
				TextField deltaE=((TextField)descarga.getChildren().get(16));
				
				sigmaQ.setText("0.01");
				
				deltaQ.textProperty().addListener((observable, oldValue, newValue)->{
					try{
						sigmaQ.setText(deltaQ.getText());
					}catch(Exception e){
						System.out.println("error en funcion generaFilasResultados (deltaQ): "+e.getMessage() );
					}
				});
				
				sigmaQ.textProperty().addListener((observable,oldValue, newValue)->{
					try{
						TextField sigmaMenor =(TextField)descarga.getChildren().get(11);
						float a=Float.parseFloat(sigmaQ.getText());
						float b=consta[5]; //constante del aparato
						float c=consta[2];//area de la parte de constantes del equipo
						float res=a*b/c;
						sigmaMenor.setText(String.format("%.3f",res));
					}catch(Exception e){
						System.out.println("error en funcion generaFilasResultados (sigmaQ) : "+e.getMessage() );
					}
				});
				
				((TextField)descarga.getChildren().get(12)).setText("0.00");
				((TextField)descarga.getChildren().get(13)).setText("0.00");
				
				deltaE.textProperty().addListener((observable, oldValue, newValue)->{
					try{
						//delta e de la tabla de resultados
						TextField e=(TextField)descarga.getChildren().get(15);
						TextField deltaEcuacion=(TextField)descarga.getChildren().get(17);
						deltaEcuacion.setText(String.valueOf ((Float.parseFloat(deltaE.getText()))/(1+(Float.parseFloat(e.getText()))) *100) );
					}catch(Exception ec){
						System.out.println("Error en función generaFilasResultados(deltaE): "+ ec.getMessage());
					}
				});
				deltaE.setText("0.00");
				/////////////////////////////
				
				for (int i=14;i<152;i+=9){
					auxE(i);
				}
				
				//agregar listenners 
				TextField segundoCampoe=(TextField)descarga.getChildren().get(24);
				TextField tercerCampoe=(TextField)descarga.getChildren().get(33);
				segundoCampoe.textProperty().addListener((observable,oldValue,newValue)->{
					try{
						float camp2=Float.parseFloat(segundoCampoe.getText());
						float camp3=Float.parseFloat(tercerCampoe.getText());
						((TextField)this.infoResultados.getChildren().get(11)).setText(String.format("%.3f",((camp3-camp2)/(1+camp2))*100));
						
					}catch(Exception e){
						System.out.println("Error en segundoCampoe:"+e.getMessage());
					}
				});
				tercerCampoe.textProperty().addListener((observable,oldValue,newValue)->{
					try{
						float camp2=Float.parseFloat(segundoCampoe.getText());
						float camp3=Float.parseFloat(tercerCampoe.getText());
						((TextField)this.infoResultados.getChildren().get(11)).setText(String.format("%.3f",((camp3-camp2)/(1+camp2))*100));
					}catch(Exception e){
						System.out.println("Error en tercerCampoe:"+e.getMessage());
					}
				});
				
				for(int i=12;i<152;i+=9){
					listennersLectMicro(i);
				}
				
				reactiva((TextField)this.despuesDeConsolidar.getChildren().get(1));
				
				SwingNode nodechart=this.creaChart();
				this.graficaCompresibilidad.getChildren().add(nodechart);
				ChartPanel chartPane=(ChartPanel)nodechart.getContent();
				this.grafica=chartPane.getChart();
				this.resultados.addSeries(resCarga);//resCarga
				this.resultados.addSeries(resDescarga);//resDescarga
				this.grafica.getXYPlot().setDataset(this.resultados);
				
				this.grafica.getXYPlot().setDomainAxis(new LogarithmicAxis("Esfuerzo (kg/cm²"));
				this.grafica.getXYPlot().setRangeAxis(new org.jfree.chart.axis.NumberAxis("Relación de vacíos, \"e\""));
				
		}catch(Exception  e){
			System.out.println("Error en funcion generaFilasResultados: "+e.getMessage());
		}
		
		
		
	}
	
	void listennersLectMicro(int indice){
		TextField lect=(TextField)descarga.getChildren().get(indice);
		lect.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				if(indice!=12)
				auxVv(indice+2,Float.parseFloat((String)((TextField)descarga.getChildren().get(indice)).getText()));
				//e de la tabla de resultados
				TextField e=(TextField)descarga.getChildren().get(indice+3);
				float temp=Float.parseFloat(((TextField)descarga.getChildren().get(indice+2)).getText());
				e.setText(String.format("%.3f",temp/antPrueba[4]));
				//delta e de la tabla de resultados
				TextField deltaE=(TextField)descarga.getChildren().get(16);
				if(indice!=12){
					TextField e1=(TextField)descarga.getChildren().get(indice+4);
					float a=Float.parseFloat(((TextField)descarga.getChildren().get((indice+4)-10)).getText());
					float b=Float.parseFloat(((TextField)descarga.getChildren().get((indice+4)-1)).getText());
					float temp1=Math.abs(a-b);
					e1.setText(String.format("%.3f",temp1));
				}
				TextField deltaEcuacion=(TextField)descarga.getChildren().get(indice+5);
				float deltae=(Float.parseFloat(((TextField)descarga.getChildren().get((indice+5)-1)).getText()));
				float e2=(Float.parseFloat(((TextField)descarga.getChildren().get((indice+5)-2)).getText()));
				deltaEcuacion.setText(String.valueOf (deltae/(1+ e2 )));	
				
				try{ 
				if((indice+9)<152){
					if(!((TextField)descarga.getChildren().get(indice)).getText().isEmpty())
						reactiva((TextField)descarga.getChildren().get(indice+9));
				}
				}catch(Exception excep){
					System.out.println("Error en listennersLectMicro (indice+9<152): "+excep.getMessage());
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("Error en función listennersLectMicro"+e.getMessage());
			}
		});
	}
	void auxE(int indice){
		TextField vv=(TextField)descarga.getChildren().get(indice);
		vv.textProperty().addListener((observable,oldValue,newValue)->{
			try{TextField e=(TextField)descarga.getChildren().get(indice+1);
			float temp=Float.parseFloat(vv.getText());
			e.setText(String.format("%.3f",temp/antPrueba[4]));
			
			TextField deltaE=(TextField)descarga.getChildren().get(16);
			try{
			for (int i=25;i<descarga.getChildren().size();i+=9){
				
				TextField e1=(TextField)descarga.getChildren().get(i);
				float a=Float.parseFloat(((TextField)descarga.getChildren().get(i-10)).getText());
				float b=Float.parseFloat(((TextField)descarga.getChildren().get(i-1)).getText());
						
				float temp1;
				if(i>=97)
					temp1=Math.abs(a-b);
				else
					temp1=a-b;
				e1.setText(String.format("%.3f",temp1));
			}
			}catch(Exception exception){
				System.out.println("Error en función auxE (deltaE)"+exception.getMessage());
			}
			
			//delta ecuación
			try{
			for (int i=17;i<descarga.getChildren().size();i+=9){
				TextField deltaEcuacion=(TextField)descarga.getChildren().get(i);
				float deltae=(Float.parseFloat(((TextField)descarga.getChildren().get(i-1)).getText()));
				float e1=(Float.parseFloat(((TextField)descarga.getChildren().get(15)).getText()));
				deltaEcuacion.setText(String.valueOf (deltae/(1+ e1) ));
				
			}}catch(Exception exception){
				System.out.println("Error en función auxE (deltaE): "+exception.getMessage());
			}
			graficaResultados();
			}catch(Exception e){
				System.out.println("Error en función auxE: "+e.getMessage());
			}
		});
	}
	 void auxSumaDeltaSigma(){
		 try{
		 for (int i=19;i<descarga.getChildren().size();i+=9){
				TextField res= (TextField)descarga.getChildren().get(i);
				TextField a=(TextField)descarga.getChildren().get(i-1);
				TextField b=(TextField)descarga.getChildren().get(i-9);
				if (!(a.getText().isEmpty()||b.getText().isEmpty()))
					res.setText(String.format("%.3f", (Float.parseFloat(a.getText()))+(Float.parseFloat(b.getText()))  ) );
			}
		
		 }catch(Exception e){
			 System.out.println("Error en función auxSumaDeltaSigma: "+e.getMessage());
		 }
	 }
	
	
@FXML
public void initialize() {
		TextField gm = (TextField)this.clasificacion.getChildren().get(1);
		TextField Ss=(TextField)this.clasificacion.getChildren().get(3);
		
		TextField diametro=(TextField)this.constantes.getChildren().get(1);
		TextField altura=(TextField)this.constantes.getChildren().get(3);
		TextField area=(TextField)this.constantes.getChildren().get(5);
		TextField volumen=(TextField)this.constantes.getChildren().get(7);
		TextField pesoAnillo=(TextField)this.constantes.getChildren().get(9);
		TextField constanteAparato=(TextField)this.constantes.getChildren().get(11);
		
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
		//---------------------------------
		TextField areaAnillo = (TextField)this.infoResultados.getChildren().get(1);
		TextField alturaInicial=(TextField)this.infoResultados.getChildren().get(3);
		TextField vs=(TextField)this.infoResultados.getChildren().get(9);
		//------------------------------
		//////////////////////////////////////////////
		//Datos///
		GridPane in1=(GridPane)((GridPane)((SplitPane)((SplitPane)carga1.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in2=(GridPane)((GridPane)((SplitPane)((SplitPane)carga2.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in3=(GridPane)((GridPane)((SplitPane)((SplitPane)carga3.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in4=(GridPane)((GridPane)((SplitPane)((SplitPane)carga4.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in5=(GridPane)((GridPane)((SplitPane)((SplitPane)carga5.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in6=(GridPane)((GridPane)((SplitPane)((SplitPane)carga6.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in7=(GridPane)((GridPane)((SplitPane)((SplitPane)carga7.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		GridPane in8=(GridPane)((GridPane)((SplitPane)((SplitPane)carga8.getContent()).getItems().get(0)).getItems().get(0)).getChildren().get(0);  //Datos
		

		TextField cargaTotal1=(TextField)in1.getChildren().get(5);
		TextField cargaTotal2=(TextField)in2.getChildren().get(5);
		TextField cargaTotal3=(TextField)in3.getChildren().get(5);
		TextField cargaTotal4=(TextField)in4.getChildren().get(5);
		TextField cargaTotal5=(TextField)in5.getChildren().get(5);
		TextField cargaTotal6=(TextField)in6.getChildren().get(5);
		TextField cargaTotal7=(TextField)in7.getChildren().get(5);
		TextField cargaTotal8=(TextField)in8.getChildren().get(5);
		/////////////////////////////////////
		//Los listener se aplican a los input, al cambiar alguno, modificaran los cálculos
		//Listener Ss en clasificación...
		
		
		
		nombre.textProperty().addListener((observable,oldValue,newValue)->{
		try{
			name.setText(nombre.getText());
		}catch(Exception e){
			System.out.println("Error en función initialize (nombre): "+e.getMessage());
		}	
			
		});
		
		Ss.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				reactiva(wma);
				reactiva(wmsa);
			}catch(Exception e){
				System.out.println("error en la función initialize (Ss)"+e.getMessage());
			}
			
		});
		
		//para calcular las constantes de equipo/////////////////////////
		diametro.textProperty().addListener((observable, oldValue, newValue) -> {
		    try{
		    	consta[0]=Float.parseFloat(((String)diametro.getText()));	
		    	consta[2]=PI*consta[0]*consta[0]/4; 
				area.setText(String.format("%.3f",consta[2]));
				reactiva(altura); //calcula el volumen con el area nueva, gm(clasificación), volVm
				//infoResultados
				areaAnillo.setText(String.format("%.3f",consta[2]));
				
				//parte de sigmaMenor en resultados... se necesita en el calculo el area, por lo tanto, para no agregar un listenner al area, desde el mismo calculo de esta agrego nuevos calculos
				//Función que crea listenners en campos repetidos variando el campo anterior de este
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				//parte de Vv
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				auxCargaTotal(in1,cargaTotal1);
				auxCargaTotal(in2,cargaTotal2);
				auxCargaTotal(in3,cargaTotal3);
				auxCargaTotal(in4,cargaTotal4);
				auxCargaTotal(in5,cargaTotal5);
				auxCargaTotal(in6,cargaTotal6);
				auxCargaTotal(in7,cargaTotal7);
				auxCargaTotal(in8,cargaTotal8);
				
				
				float sum1=0;
				float sum2=0;
				for (int i=13;i<=154;i+=9){
					if(i>85)
						sum2+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));
					else
						sum1+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));
				}
				TextField alturaFinal=(TextField)this.infoResultados.getChildren().get(5);
				float alturaIni=Float.parseFloat(((TextField)this.infoResultados.getChildren().get(3)).getText());
				float resultado=alturaIni-sum1/10+sum2/10;
				alturaFinal.setText(String.format("%.3f",resultado));
				graficaResultados();
		    }catch (Exception e) {
				// TODO: handle exception
		    	System.out.println("error en función initialize (diametro): "+e.getMessage());
			}
		});
		
		altura.textProperty().addListener((observable, oldValue, newValue) -> {
			try{
				consta[1]=Float.parseFloat(((String)altura.getText()));
				consta[3]=consta[1]*consta[2];
				volumen.setText(String.format("%.3f",consta[3]));
				volVm.setText(String.format("%.3f",consta[3]));
				gm.setText(String.format("%.3f",antPrueba[1]/consta[3]));
				//infoResultados
				alturaInicial.setText(String.format("%.3f",consta[1]));
				reactiva(wma);
				reactiva(wmsa);
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (altura): "+e.getMessage());
			}
		});
		
		pesoAnillo.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				consta[4]=Float.parseFloat(((String)pesoAnillo.getText()));
				reactiva(wma);
				reactiva(wmsa);
			}catch (Exception e) {
				System.out.println("error en función initialize (pesoAnillo): "+e.getMessage());
			}
		});
		
		constanteAparato.textProperty().addListener((observable, oldValue,newValue)->{
			try{
				consta[5]=Float.parseFloat((String)constanteAparato.getText());
				//funcion que realiza calculos repetitivos del campo de sigmaMenor de la tabla de resultados
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				
				auxCargaTotal(in1,cargaTotal1);
				auxCargaTotal(in2,cargaTotal2);
				auxCargaTotal(in3,cargaTotal3);
				auxCargaTotal(in4,cargaTotal4);
				auxCargaTotal(in5,cargaTotal5);
				auxCargaTotal(in6,cargaTotal6);
				auxCargaTotal(in7,cargaTotal7);
				auxCargaTotal(in8,cargaTotal8);
				
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (pesoAnillo): "+e.getMessage());
			}
		});
		//////////////////////////////////////////////////////////////////////////////
		
	///Para calcular la humedad inicial
		wm.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[0]=Float.parseFloat(((String)wm.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.format("%.3f",humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (wm): "+e.getMessage());
			}
		});
		
		ws.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[1]=Float.parseFloat(((String)ws.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.format("%.3f",humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (ws): "+e.getMessage());

			}
		});
		
		wf.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				humIni[2]=Float.parseFloat(((String)wf.getText()));
				humIni[3]=((humIni[0]-humIni[1])/(humIni[1]-humIni[2]))*100;
				wpercent.setText(String.format("%.3f",humIni[3]));
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (wf): "+e.getMessage());
				System.out.println("error, letras");
			}
		});
		
		//excepción
		wpercent.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				antPrueba[2]=Float.parseFloat((String)wpercent.getText());
				hum.setText(wpercent.getText());
				reactiva(wma);
				reactiva(Ss);
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (wpercent): "+e.getMessage());
			}
		});
		////////////////////////////////////////////////////////////////////
		/////////////////////ANTES DE LA PRUEBA////////////////////////////
		wma.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				antPrueba[0]=Float.parseFloat(((String)wma.getText()));
				antPrueba[1]=antPrueba[0]-consta[4];
				wmnatural.setText( String.format("%.3f",antPrueba[1]));
				antPrueba[3]=antPrueba[1]/(1+antPrueba[2]/100);
				wmnaturals.setText(String.format("%.3f",antPrueba[3]));
				gm.setText(String.format("%.3f",antPrueba[1]/consta[3]));//modifica valor en clasificacion 
				
				float ss=Float.parseFloat(((String)Ss.getText()));
				antPrueba[4]=antPrueba[3]/ss;
				volmnatural.setText(String.format("%.3f",antPrueba[4]));
				antPrueba[5]=antPrueba[3]*antPrueba[2]/100;//Peso(Ww)
				pesoWw.setText(String.format("%.3f",antPrueba[5]));
				antPrueba[6]=consta[3];
				antPrueba[7]=antPrueba[6]-antPrueba[4];
				volVv.setText(String.format("%.3f",antPrueba[7]));
				antPrueba[8]=antPrueba[7]/antPrueba[4];
				vacios.setText(String.format("%.3f",antPrueba[8]));
				antPrueba[9]=(antPrueba[5]/antPrueba[7])*100;
				gsat.setText(String.format("%.3f",antPrueba[9]));
				volVm.setText(String.format("%.3f",consta[3]));
				
				despPrueba[2]=consta[4]+antPrueba[3];
				wmsanillo.setText(String.format("%.3f",despPrueba[2]));
				
				//infoResultados
				vs.setText(String.format("%.3f",antPrueba[4]));
				
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				//Vv de la tabla de resultados
				TextField Vv = (TextField)descarga.getChildren().get(14);
				Vv.setText(String.format("%.3f",antPrueba[7]));
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				
				//e de la tabla de resultados
				for (int i=15;i<descarga.getChildren().size();i+=9){
					TextField e=(TextField)descarga.getChildren().get(i);
					float temp=Float.parseFloat(((TextField)descarga.getChildren().get(i-1)).getText());
					e.setText(String.format("%.3f",temp/antPrueba[4]));
				}
				//delta e de la tabla de resultados
				TextField deltaE=(TextField)descarga.getChildren().get(16);
				try{
				for (int i=25;i<descarga.getChildren().size();i+=9){
					TextField e=(TextField)descarga.getChildren().get(i);
					float a=Float.parseFloat(((TextField)descarga.getChildren().get(i-10)).getText());
					float b=Float.parseFloat(((TextField)descarga.getChildren().get(i-1)).getText());
					float temp=Math.abs(a-b);
					e.setText(String.format("%.3f",temp));
				}
				}catch(Exception exception){}
				
				//delta ecuación 
				try{
				for (int i=17;i<descarga.getChildren().size();i+=9){
					TextField deltaEcuacion=(TextField)descarga.getChildren().get(i);
					float deltae=(Float.parseFloat(((TextField)descarga.getChildren().get(i-1)).getText()));
					float e=(Float.parseFloat(((TextField)descarga.getChildren().get(i-2)).getText()));
					deltaEcuacion.setText(String.valueOf (deltae/(1+ e )));
				}
				}catch(Exception exception){}
				
				graficaResultados();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("error en función initialize (wma): "+e.getMessage());

			}
		});
	
		//////////////DESPUES DE LA PRUEBA////////////////////////////////
		wmsa.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				despPrueba[0]=Float.parseFloat(((String)wmsa.getText()));
				despPrueba[1]=despPrueba[0]-consta[4];
				wmsat.setText(String.format("%.3f",despPrueba[1]));
				despPrueba[2]=consta[4]+antPrueba[3];
				wmsanillo.setText(String.format("%.3f",despPrueba[2]));
				despPrueba[10]=despPrueba[0]-despPrueba[2];
				wamuestrasat.setText(String.format("%.3f",despPrueba[10]));
				despPrueba[3]=antPrueba[3];
				wms.setText(String.format("%.3f",despPrueba[3]));
				despPrueba[4]=((despPrueba[1]-despPrueba[3])/despPrueba[3])*100;
				wper.setText(String.format("%.3f",despPrueba[4]));
				despPrueba[6]=despPrueba[3]/Float.parseFloat(((String)Ss.getText()));
				volsol.setText(String.format("%.3f",despPrueba[6]));
				despPrueba[5]=consta[2]*(Float.parseFloat(((TextField)infoResultados.getChildren().get(5)).getText()));//vinculo con resultados
				voltotmsat.setText(String.format("%.3f",despPrueba[5]));
				despPrueba[7]=despPrueba[5]-despPrueba[6];
				volva.setText(String.format("%.3f",despPrueba[7]));
				float maximo=Float.parseFloat(((TextField)descarga.getChildren().get(96)).getText());
				for (int i=105;i<152;i+=9){
					if (maximo<Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText())){
						maximo=Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText());
					}
				}
				
				despPrueba[8]=maximo;//datos de resultados
				despPrueba[9]=(despPrueba[10]/despPrueba[7])*100;
				va.setText(String.format("%.3f",despPrueba[8]));
				gpercent.setText(String.format("%.3f",despPrueba[9]));
			}catch(Exception e){
				System.out.println("error en función initialize (wmsa): "+e.getMessage());
				//System.out.println("Error en wmsa: "+e.getMessage());
			}
		});
		
		TextField i1=(TextField)in1.getChildren().get(1); //incremento de la carga (carga1)
		TextField i2=(TextField)in2.getChildren().get(1);
		TextField i3=(TextField)in3.getChildren().get(1);
		TextField i4=(TextField)in4.getChildren().get(1);
		TextField i5=(TextField)in5.getChildren().get(1);
		TextField i6=(TextField)in6.getChildren().get(1);
		TextField i7=(TextField)in7.getChildren().get(1);
		TextField i8=(TextField)in8.getChildren().get(1);
		
		TextField aprox=(TextField)in1.getChildren().get(3);
		aprox.textProperty().addListener((observable,oldValue,newValue)->{
			String aproxTemp=aprox.getText();
			((TextField)in2.getChildren().get(3)).setText(aproxTemp);
			((TextField)in3.getChildren().get(3)).setText(aproxTemp);
			((TextField)in4.getChildren().get(3)).setText(aproxTemp);
			((TextField)in5.getChildren().get(3)).setText(aproxTemp);
			((TextField)in6.getChildren().get(3)).setText(aproxTemp);
			((TextField)in7.getChildren().get(3)).setText(aproxTemp);
			((TextField)in8.getChildren().get(3)).setText(aproxTemp);
		});
		
		
		
		cargaTotal1.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in1,cargaTotal1);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal1): "+e.getMessage());}});
		cargaTotal2.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in2,cargaTotal2);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal2): "+e.getMessage());}});
		cargaTotal3.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in3,cargaTotal3);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal3): "+e.getMessage());}});
		cargaTotal4.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in4,cargaTotal4);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal4): "+e.getMessage());}});
		cargaTotal5.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in5,cargaTotal5);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal5): "+e.getMessage());}});
		cargaTotal6.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in6,cargaTotal6);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal6): "+e.getMessage());}});
		cargaTotal7.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in7,cargaTotal7);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal7): "+e.getMessage());}});
		cargaTotal8.textProperty().addListener((observable,oldValue,newValue)->{try{auxCargaTotal(in8,cargaTotal8);	}catch(Exception e){System.out.println("error en función initialize (cargaTotal8): "+e.getMessage());}});
	
		i1.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				((TextField)in1.getChildren().get(5)).setText(i1.getText());
				reactiva(i2);
				((TextField)descarga.getChildren().get(18)).setText(i1.getText());
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				//Gráfica de resultados//
				graficaResultados();
				////////////////////////////
			}catch(Exception e){
				System.out.println("error en función initialize (i1): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		i2.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in1.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i2.getText()));
				((TextField)in2.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i3);
				((TextField)descarga.getChildren().get(27)).setText(i2.getText());
				
				((TextField)descarga.getChildren().get(144)).setText(String.format("%.3f",Float.parseFloat(i2.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i2): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		
		i3.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in2.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i3.getText()));
				((TextField)in3.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i4);
				((TextField)descarga.getChildren().get(36)).setText(i3.getText());
				((TextField)descarga.getChildren().get(135)).setText(String.format("%.3f",Float.parseFloat(i3.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i3): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		
		i4.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in3.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i4.getText()));
				((TextField)in4.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i5);
				((TextField)descarga.getChildren().get(45)).setText(i4.getText());
				((TextField)descarga.getChildren().get(126)).setText(String.format("%.3f",Float.parseFloat(i4.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i4): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		
		i5.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in4.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i5.getText()));
				((TextField)in5.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i6);
				((TextField)descarga.getChildren().get(54)).setText(i5.getText());
				((TextField)descarga.getChildren().get(117)).setText(String.format("%.3f",Float.parseFloat(i5.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i5): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		i6.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in5.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i6.getText()));
				((TextField)in6.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i7);
				((TextField)descarga.getChildren().get(63)).setText(i6.getText());
				((TextField)descarga.getChildren().get(108)).setText(String.format("%.3f",Float.parseFloat(i6.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i6): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		
		i7.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in6.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i7.getText()));
				((TextField)in7.getChildren().get(5)).setText(String.format("%.3f",sum));
				reactiva(i8);
				((TextField)descarga.getChildren().get(72)).setText(i7.getText());
				((TextField)descarga.getChildren().get(99)).setText(String.format("%.3f",Float.parseFloat(i7.getText())*-1));
				auxSumaDeltaSigma();
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i7): "+e.getMessage());
				//System.out.println(e.getMessage());
			}
		});
		
		i8.textProperty().addListener((observable,oldValue,newValue)->{
			try{
				float c=Float.parseFloat(((TextField)in7.getChildren().get(5)).getText());
				float sum=c+(Float.parseFloat(i8.getText()));
				((TextField)in8.getChildren().get(5)).setText(String.format("%.3f",sum));
				((TextField)descarga.getChildren().get(81)).setText(i8.getText());
				((TextField)descarga.getChildren().get(90)).setText(String.format("%.3f",Float.parseFloat(i8.getText())*-1));
				auxSumaDeltaSigma();
				
				for(int i=11;i<descarga.getChildren().size();i+=9){
					auxSigmaMenor(i);
				}
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}
				graficaResultados();
			}catch(Exception e){
				System.out.println("error en función initialize (i8): "+e.getMessage());
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
		
		descargas(d8,d7,10);
		descargas(d7,d6,11);
		descargas(d6,d5,12);
		descargas(d5,d4,13);
		descargas(d4,d3,14);
		descargas(d3,d2,15);
		descargas(d2,d1,-1);
		descargas(d1,null,16);
			
		
		///////////////////////////////////////////////////////////////////

	}

void reactiva(TextField t){
	if(!(t.getText().equals(" ")|| t.getText().isEmpty())){
		t.setText(t.getText()+" ");
		t.setText(String.format("%.3f",Float.parseFloat(t.getText())));
	}
}
void auxCargaTotal(GridPane in,TextField cargaTotal){
		TextField presTemp=(TextField)in.getChildren().get(7); 
		float res=(Float.parseFloat(cargaTotal.getText())*(consta[5]/consta[2]));
		presTemp.setText(String.format("%.3f",res));
}

 @FXML void actualiza(MouseEvent e){

	 TitledPane cargaTemporal=(TitledPane)e.getSource();
	SplitPane splitTemporal=(SplitPane)cargaTemporal.getContent();
	splitTemporal.autosize();
 }
 
 	void auxSigmaMenor(int indice){
 		try{
		TextField sigmaMenor =(TextField)descarga.getChildren().get(indice);
		if(((TextField)descarga.getChildren().get(indice-1)).getText().isEmpty())
			return;
		float a=Float.parseFloat(((TextField)descarga.getChildren().get(indice-1)).getText());
		float b=consta[5]; //constante del aparato
		float c=consta[2];//area de la parte de constantes del equipo
		float res=a*b/c;
		sigmaMenor.setText(String.format("%.3f",res));
		}catch(Exception e){
			System.out.println("error en función auxSigmaMenor: "+e.getMessage());
		}
 	}
 	
 	void auxPrueba(TitledPane carga){
 		List<GridPane> temp = getCadenaCargas(carga);
 		float maximo = Float.parseFloat(((TextField)temp.get(1).getChildren().get(11)).getText());
		float minimo =Float.parseFloat(((TextField)temp.get(1).getChildren().get(11)).getText());
		for (int i=11;i<temp.get(1).getChildren().size();i+=6){
			if(!((TextField)temp.get(1).getChildren().get(i)).getText().isEmpty()){
			float temp1=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i)).getText());
			if(carga.getId().equals("carga2")){
				if(minimo>temp1){
					minimo=temp1;
				}
			}else{
				if (maximo<temp1){
					maximo=temp1;
				}	
			}
			}
			
		}			

		//agregar elementos 
		switch(carga.getId()){
			case "carga1":	((TextField)descarga.getChildren().get(21)).setText(String.format("%.3f",maximo)); 
							((TextField)descarga.getChildren().get(22)).setText(String.format("%.3f",maximo));
							break;
			case "carga2":	((TextField)descarga.getChildren().get(30)).setText(String.format("%.3f",minimo));
							((TextField)descarga.getChildren().get(31)).setText(String.format("%.3f",minimo));
							break;
			case "carga3":	((TextField)descarga.getChildren().get(39)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(40)).setText(String.format("%.3f",maximo));
							break;
			case "carga4":  ((TextField)descarga.getChildren().get(48)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(49)).setText(String.format("%.3f",maximo));
							break;
			case "carga5":	((TextField)descarga.getChildren().get(57)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(58)).setText(String.format("%.3f",maximo));
							break;
			case "carga6":	((TextField)descarga.getChildren().get(66)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(67)).setText(String.format("%.3f",maximo));
							break;
			case "carga7":	((TextField)descarga.getChildren().get(75)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(76)).setText(String.format("%.3f",maximo));
							break;
			case "carga8":	((TextField)descarga.getChildren().get(84)).setText(String.format("%.3f",maximo));
							((TextField)descarga.getChildren().get(85)).setText(String.format("%.3f",maximo));
							break;
			default:break;
		}
 	}
 	
	void auxAbrirCargas( TitledPane carga, XYSeriesCollection s,Scanner scanner,String []linea){
		//aqui la parte de la grafica
		SplitPane lder=(SplitPane)((SplitPane)carga.getContent()).getItems().get(1);
		StackPane contenedorGrafica = (StackPane)lder.getItems().get(1);
		ChartPanel chartPane=(ChartPanel)((SwingNode)(contenedorGrafica.getChildren().get(0))).getContent();
		JFreeChart chart = chartPane.getChart();
		//aguas!
		if(chart.getPlot().getDatasetGroup()==null)
			chart.getXYPlot().setDataset(s);
		
		List<GridPane> temp = getCadenaCargas(carga); //obtengo un List de GridPanes, de los elementos de cada carga
		insertarCadena(temp.get(0),linea);
		String textfile=scanner.nextLine();
        linea=textfile.split(";");
		int tam=linea.length;
		int tamactual=(temp.get(1).getChildren().size()-1)-6;
		System.out.println(tamactual);
		if (tam>6){
			while (tamactual<tam){
				int lastRow=(temp.get(1).getChildren().size()/6)+1;
				for (int i=0;i<6;i++){
					temp.get(1).add(new TextField(), i, lastRow);	
				}

				TextField t=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-2);
				
				int tama=(temp.get(1).getChildren().size()-1);
				t.textProperty().addListener((observable,lastValue,newValue)->{
					try{
						float ini=Float.parseFloat(((TextField)temp.get(1).getChildren().get(7)).getText());
						float rest=ini-(Float.parseFloat(t.getText()));
						((TextField)temp.get(1).getChildren().get(tama-2)).setText(String.format("%.3f",rest));
						((TextField)temp.get(1).getChildren().get(tama-2)).setEditable(false);
						((TextField)temp.get(1).getChildren().get(tama-1)).setEditable(false);
						((TextField)temp.get(1).getChildren().get(tama-1)).setText(String.format("%.3f",rest));
					}catch(Exception ec){
						System.out.println("error en función auxAbrirCargas (t): "+ec.getMessage());
					}
					
				});
				temp.get(1).setGridLinesVisible(false);
				temp.get(1).setGridLinesVisible(true);
				
				
				TextField tiempo=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-4);
				
				tiempo.textProperty().addListener((observable,lastValue,newValue)->{
					try{
					((XYSeries)s.getSeries().get(0)).clear();
					//int cantidad=((tam-16)/7)+1;
					//System.out.println(cantidad);
					for (int i=14; i<temp.get(1).getChildren().size();i+=6){
						try{float m=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i)).getText());
						float df=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i+3)).getText());
						((XYSeries)s.getSeries().get(0)).add(m,df);}catch(Exception ec){}
					}
				}catch(Exception ec){
					System.out.println("error en función auxAbrirCargas (tiempo): "+ec.getMessage());
				}
				});
				//el tam de los nodos, tienen un elemento de mas, quien sabe porque :I
				TextField def=(TextField)temp.get(1).getChildren().get((temp.get(1).getChildren().size()-1)-1);
 				def.textProperty().addListener((observable,lastValue,newValue)->{
					try{
						((XYSeries)s.getSeries().get(0)).clear();
					//int cantidad=((tam-16)/7)+1;
					//System.out.println(cantidad);
					for (int i=14; i<temp.get(1).getChildren().size();i+=6){
						try{float m=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i)).getText());
						float df=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i+3)).getText());
						((XYSeries)s.getSeries().get(0)).add(m,df);}catch(Exception ec){}
					}
					
//					float maximo = Float.parseFloat(((TextField)temp.get(1).getChildren().get(11)).getText());
//					float minimo =Float.parseFloat(((TextField)temp.get(1).getChildren().get(11)).getText());
//					for (int i=11;i<temp.get(1).getChildren().size();i+=6){
//						if(!((TextField)temp.get(1).getChildren().get(i)).getText().isEmpty()){
//						float temp1=Float.parseFloat(((TextField)temp.get(1).getChildren().get(i)).getText());
//						if(carga.getId().equals("carga2")){
//							if(minimo>temp1){
//								minimo=temp1;
//							}
//						}else{
//							if (maximo<temp1){
//								maximo=temp1;
//							}	
//						}
//						}
//						
//					}			
//
//					//agregar elementos 
//					switch(carga.getId()){
//						case "carga1":	((TextField)descarga.getChildren().get(21)).setText(String.format("%.3f",maximo)); 
//										((TextField)descarga.getChildren().get(22)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga2":	((TextField)descarga.getChildren().get(30)).setText(String.format("%.3f",minimo));
//										((TextField)descarga.getChildren().get(31)).setText(String.format("%.3f",minimo));
//										break;
//						case "carga3":	((TextField)descarga.getChildren().get(39)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(40)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga4":  ((TextField)descarga.getChildren().get(48)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(49)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga5":	((TextField)descarga.getChildren().get(57)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(58)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga6":	((TextField)descarga.getChildren().get(66)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(67)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga7":	((TextField)descarga.getChildren().get(75)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(76)).setText(String.format("%.3f",maximo));
//										break;
//						case "carga8":	((TextField)descarga.getChildren().get(84)).setText(String.format("%.3f",maximo));
//										((TextField)descarga.getChildren().get(85)).setText(String.format("%.3f",maximo));
//										break;
//						default:break;
//					}
					
					try{
						for (int i=23;i<descarga.getChildren().size();i+=9){
							auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
						}}catch(Exception ec){
							System.out.println("error en función auxAbrirCargas (for->auxVv): "+ec.getMessage());
						}
					
					float sum1=0;
					float sum2=0;
					for (int i=13;i<=154;i+=9){
						if(i>85){							
							sum2+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));
						}else{
							sum1+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));}
					}
					TextField alturaFinal=(TextField)this.infoResultados.getChildren().get(5);
					float alturaInicial=Float.parseFloat(((TextField)this.infoResultados.getChildren().get(3)).getText());
					float resultado=alturaInicial-sum1/10+sum2/10;
					alturaFinal.setText(String.format("%.3f",resultado));
				}catch(Exception ec){
					System.out.println("error en función auxAbrirCargas (def): "+ec.getMessage());
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
	
	void auxVv(int indice,float deltah){
		try{
			float arriba=Float.parseFloat((String)((TextField)descarga.getChildren().get(indice-9)).getText());
			float res;
			if(indice>=95)
				res=arriba+((deltah/10)*consta[2]);
			else
				res=arriba-((deltah/10)*consta[2]);
			
			((TextField)descarga.getChildren().get(indice)).setText(String.format("%.3f",res));
		}catch(Exception e){
			System.out.println("error en funcion auxVv: "+e.getMessage());
		}}
	
	
	void descargas(GridPane d, GridPane d2,int indice){
		TextField c=(TextField)d.getChildren().get(15);
		
		c.textProperty().addListener((observable, oldValue, newValue)->{
			try{
				if(indice!=-1){
					((TextField)descarga.getChildren().get((indice*9)+3)).setText(((TextField)d.getChildren().get(17)).getText());
					((TextField)descarga.getChildren().get((indice*9)+4)).setText(((TextField)d.getChildren().get(17)).getText());
				}
				if (d2!=null)
					((TextField)d2.getChildren().get(7)).setText(c.getText());
				float res = - Float.parseFloat( ((TextField)d.getChildren().get(7)).getText() ) + Float.parseFloat(c.getText());
				((TextField)d.getChildren().get(16)).setText(String.format("%.3f",res));
				((TextField)d.getChildren().get(17)).setText(String.format("%.3f",res));
				
				try{
				for (int i=23;i<descarga.getChildren().size();i+=9){
					auxVv(i,Float.parseFloat((String)((TextField)descarga.getChildren().get(i-1)).getText()));
				}}catch(Exception e){}

				float sum1=0;
				float sum2=0;
				for (int i=13;i<=154;i+=9){
					if(i>85)
						sum2+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));
					else
						sum1+=(Float.parseFloat(((TextField)descarga.getChildren().get(i)).getText()));
				}
				TextField alturaFinal=(TextField)this.infoResultados.getChildren().get(5);
				float alturaInicial=Float.parseFloat(((TextField)this.infoResultados.getChildren().get(3)).getText());
				float resultado=alturaInicial-sum1/10+sum2/10;
				alturaFinal.setText(String.format("%.3f",resultado));
				graficaResultados();
				
				
			}catch(Exception e){
				System.out.println("Error en función descargas: "+e.getMessage());
			}
			
		});
	}

}
