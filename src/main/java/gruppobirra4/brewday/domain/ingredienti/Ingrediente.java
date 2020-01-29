package gruppobirra4.brewday.domain.ingredienti;

import java.io.Serializable;
import java.util.UUID;

import org.mapdb.Serializer;

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
		boolean valid = validation(nome, categoria, quantita);
		if (!valid)
			return null;
		else
			return new Ingrediente(nome, categoria, quantita);
		
	}
	
	private static boolean validation(String nome, String categoria, double quantita) {
		return validateNome(nome) &&
				validateQuantita(quantita); //&&
				//validateCategoria(categoria);
	}
	
	private static boolean validateNome(String nome) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
		if(nomeUC.isEmpty()) {				
			Notifica.getIstanza().addError("Il nome deve contenere dei caratteri");
			return false;
		} else if (nomeUC.length() >= 30) {
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
	
	/*
	// sarà necessario???
	private static boolean validateCategoria(String categoria) {
		for (String categ : Categoria.values()) {
				if(categ.equals(categoria))
						return true;
		}
		Notifica.getIstanza().addError("L'ingrediente inserito non fa parte di nessuna categoria ammissibile");
		return false;
	}
*/
	public  String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
				//sostituisce tutti i whitespaces (spazi + newline + tab +ecc)
				//con un singolo spazio e rimuove tutti gli spazi iniziali e finali
		this.nome = nomeUC;
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
	
	/*
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.setQuantita(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovaCategoria) {
		ingrediente.setCategoria(nuovaCategoria);
	}
*/	
}
