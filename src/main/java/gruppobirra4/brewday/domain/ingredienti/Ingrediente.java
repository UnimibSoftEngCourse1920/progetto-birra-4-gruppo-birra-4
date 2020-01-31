package gruppobirra4.brewday.domain.ingredienti;

import java.io.Serializable;
import java.util.UUID;

import static gruppobirra4.brewday.domain.InputUtente.*;


public class Ingrediente implements Serializable {
	
	private String id;
	private String nome;
	private String categoria;
	private double quantita;
	
	private static final String CAMPO_QUANTITA = "Quantita";
	
	

	protected Ingrediente(String nome, String categoria, String quantita) {
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
	
	static boolean validation(String nome, String quantita) {
		return validateNome(nome) & //NOSONAR
				validateQuantita(quantita);
	}
	
	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateQuantita(String quantita) {
		return !isStringaVuota(quantita, CAMPO_QUANTITA) && 
				isNumber(quantita, CAMPO_QUANTITA) &&
				isNotPositive(quantita, CAMPO_QUANTITA);	
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

	protected void modificaIngrediente(String nuovoNome, String nuovaCategoria, String nuovaQuantita) {
		if(validation(nuovoNome, nuovaQuantita)) {
			setNome(nuovoNome);
			setCategoria(nuovaCategoria);
			setQuantita(nuovaQuantita);			
		}
	}
	
	
}
