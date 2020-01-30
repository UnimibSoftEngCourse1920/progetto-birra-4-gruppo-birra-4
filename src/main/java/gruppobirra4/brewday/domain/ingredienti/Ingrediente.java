package gruppobirra4.brewday.domain.ingredienti;

import java.io.Serializable;
import java.util.UUID;

import gruppobirra4.brewday.errori.Notifica;


public class Ingrediente implements Serializable {
	
	private String id;
	private String nome;
	private String categoria;
	private double quantita;
	
	

	Ingrediente(String nome, String categoria, String quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	

	public static Ingrediente creaIngrediente(String nome, String categoria, String quantita) {
		boolean valid = validation(nome, quantita);
		if (!valid)
			return null;
		else
			return new Ingrediente(nome, categoria, quantita);
		
	}
	
	private static boolean validation(String nome, String quantita) {
		return validateNome(nome) &&
				validateQuantita(quantita);
	}
	
	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateQuantita(String quantita) {
		return !isStringaVuota(quantita, "Quantita disponibile") && isNumber(quantita) && isNotPositive(quantita);	
	}
	
	private static boolean isNotPositive(String quantita) {
		double quantitaDisponibile = convertToNumber(quantita);
		if(quantitaDisponibile < 0) {
			Notifica.getIstanza().addError("La quantità inserita non può essere negativa");
			return false;
		}
		return true;
	}

	private static boolean isNumber(String str) {
		String strNum = rimuoviWhiteSpaces(str);
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	    	Notifica.getIstanza().addError("La quantità inserita deve essere un numero");
	    	return false;
	    }
	    return true;
	}
	
	private static boolean isStringaVuota(String str, String field) {
		String s = str;
		if (str == null) {
			s = "";
		}
		String strLW = rimuoviWhiteSpaces(s);
		if (strLW.isEmpty()) {				
			Notifica.getIstanza().addError("Il campo \"" + field + "\" deve contenere dei caratteri");
			return true;
		}
		return false;
	}
	
	private static String rimuoviWhiteSpaces(String str) {
		if (str != null) {
			return str.replaceAll("\\s+", " ").trim().toLowerCase();
		}
		return null;
	}
	
	private static double convertToNumber(String str) {
		String strNum = rimuoviWhiteSpaces(str);
		return Double.parseDouble(strNum);
	}

	public  String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeLW = rimuoviWhiteSpaces(nome);
		this.nome = nomeLW;

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

	private void setQuantita(String quantita) {
		double quantitaD = convertToNumber(quantita);
		this.quantita = quantitaD;
	}
	
	/*public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.setQuantita(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovaCategoria) {
		ingrediente.setCategoria(nuovaCategoria);
	}*/
	
}
