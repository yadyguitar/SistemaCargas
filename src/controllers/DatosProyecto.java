package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DatosProyecto implements Initializable{

	static String nombre,ubicacion, muestra,pca,profundidad;
	@FXML TextField nombreFXML,ubicacionFXML,muestraFXML,pcaFXML,profundidadFXML;
	
	
	public String getNombre(){
		nombre=nombreFXML.getText();
		return nombre;
	}
	public String getUbicacion(){
		ubicacion=ubicacionFXML.getText();
		return ubicacion;
	}
	public String getMuestra(){
		muestra=muestraFXML.getText();
		return muestra;
	}
	public String getPca(){
		pca=pcaFXML.getText();
		return pca;
	}
	public String getProfundidad(){
		profundidad=profundidadFXML.getText();
		return profundidad;
	}
	public List<String> getDatosProyecto(){
		//Devuelve un list<String> de los atributos de la clase
		List<String> datosProyecto= new ArrayList<String>();
		datosProyecto.add(getNombre());
		datosProyecto.add(getUbicacion());
		datosProyecto.add(getMuestra());
		datosProyecto.add(getPca());
		datosProyecto.add(getProfundidad());
		return datosProyecto;
	}
	public String getDatosCadena(){
		//Devuelve una cadena con los atributos en la siguiente forma: nombre;ubicacion;...\n
		String cadena=getNombre()+";"+getUbicacion()+";"+getMuestra()+";"+getPca()+";"+getProfundidad()+"\n";
		return cadena;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
