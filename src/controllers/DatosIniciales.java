package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class DatosIniciales implements Initializable{

	ConstanteEquipo ce;
	HumedadInicial hi;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

class HumedadInicial{
	static String flanera;
	static float wm,ws,wf,wp;
	@FXML TextField flaneraFXML,wmFXML,wsFXML,wfFXML,wpFXML;
	
	public String getFlanera(){
		flanera= flaneraFXML.getText();
		return flanera;
	}
	public void setFlanera(String flanera){
		flaneraFXML.setText(flanera);
	}
	
	public float getWm(){
		wm= Float.parseFloat(wmFXML.getText());
		return wm;
	}
	public void setWm(float wm){
		wmFXML.setText(String.format("%.3f",wm));
	}

	public float getWs(){
		ws= Float.parseFloat(wsFXML.getText());
		return ws;
	}
	public void setWs(float ws){
		wsFXML.setText(String.format("%.3f",ws));
	}
	
	public float getWf(){
		wf= Float.parseFloat(wfFXML.getText());
		return wf;
	}
	public void setWf(float wf){
		wfFXML.setText(String.format("%.3f",wf));
	}

	public float getWp(){
		wp= Float.parseFloat(wpFXML.getText());
		return wp;
	}
	public void setWp(float wp){
		wpFXML.setText(String.format("%.3f",wp));
	}
}

class ConstanteEquipo{	
	static float diametro,altura,area,volumen,pesoAnillo, cteAparato;
	@FXML TextField diametroFXML,alturaFXML,areaFXML,volumenFXML,pesoAnilloFXML,cteAparatoFXML;
	
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
}