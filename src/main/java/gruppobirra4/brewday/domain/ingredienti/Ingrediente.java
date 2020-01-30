package gruppobirra4.brewday.domain.ingredienti;

import java.io.Serializable;
import java.util.UUID;

import gruppobirra4.brewday.errori.Notifica;

public class Ingrediente implements Serializable {
	
	private String id;
	private String nome;
	private String categoria;
	private double quantita;
	
	
	private Ingrediente(String nome, String categoria, double quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	
	public static Ingrediente creaIngrediente(String nome, String categoria, double quantita) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
		boolean valid = validation(nomeUC, quantita);
		if (!valid)
			return null;
		else
			return new Ingrediente(nomeUC, categoria, quantita);
		
	}
	
	private static boolean validation(String nome, double quantita) {
		return validateNome(nome) &&
				validateQuantita(quantita);
	}
	
	private static boolean validateNome(String nome) {
		if(nome.isEmpty()) {				
			Notifica.getIstanza().addError("Il nome deve contenere dei caratteri");
			return false;
		} else if (nome.length() >= 30) {
			Notifica.getIstanza().addError("Il nome deve contenere al massimo 30 caratteri");
			return false;
		}
		return true;
	}
	
	private static boolean validateQuantita(double quantita) {
		if(quantita<0) {
			Notifica.getIstanza().addError("La quantità inserita non può essere negativa");
			return false;
		}
		return true;
	}
	
	public  String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCategoria() {
		return categoria;
	}

	private void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getQuantita() {
		return quantita;
	}

	private void setQuantita(double quantita) {
		this.quantita = quantita;
	}
	
}
