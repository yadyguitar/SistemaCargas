package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DI_ConstanteEquipo {
	static float diametro,altura,area,volumen,pesoAnillo, cteAparato;
	@FXML static TextField diametroFXML,alturaFXML,areaFXML,volumenFXML,pesoAnilloFXML,cteAparatoFXML;
	
	public float getDiametro(){
		diametro= Float.parseFloat(diametroFXML.getText());
		return diametro;
	}
	public void setDiametro(float diametro){
		diametroFXML.setText(String.format("%.3f",diametro));
	}
	
	public float getAltura(){
		altura= Float.parseFloat(alturaFXML.getText());
		return altura;
	}
	public void setAltura(float altura){
		alturaFXML.setText(String.format("%.3f",altura));
	}
	
	public float getArea(){
		area= Float.parseFloat(areaFXML.getText());
		return area;
	}
	public void setArea(float area){
		areaFXML.setText(String.format("%.3f",area));
	}
	
	public float getVolumen(){
		volumen= Float.parseFloat(volumenFXML.getText());
		return volumen;
	}
	public void setVolumen(float volumen){
		volumenFXML.setText(String.format("%.3f",volumen));
	}

	public float getPesoAnillo(){
		pesoAnillo= Float.parseFloat(pesoAnilloFXML.getText());
		return pesoAnillo;
	}
	public void setPesoAnillo(float pesoAnillo){
		pesoAnilloFXML.setText(String.format("%.3f",pesoAnillo));
	}

	public float getCteAparato(){
		cteAparato= Float.parseFloat(cteAparatoFXML.getText());
		return volumen;
	}
	public void setCteAparato(float cteAparato){
		cteAparatoFXML.setText(String.format("%.3f",cteAparato));
	}
	
	
	//pendiente!
	@FXML
	public void initialize() {
		try{
		System.out.println(diametroFXML.getText());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println("hola");
	}

}
