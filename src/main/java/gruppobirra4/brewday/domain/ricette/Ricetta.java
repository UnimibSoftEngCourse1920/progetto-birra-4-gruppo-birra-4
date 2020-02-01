package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.util.Set;
import java.util.UUID;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;
import static gruppobirra4.brewday.domain.InputUtente.*;

public class Ricetta {
	
	private String id; 
	private String nome;
	private String descrizione;
	private Set<Ingrediente> ingredienti;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	
	private Ricetta(String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		this.id = UUID.randomUUID().toString();
		setNome(nome);
		setDescrizione(descrizione); 
		setIngredienti(ingredienti);
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
	}
	
	protected Ricetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
			String quantitaAcqua, String quantitaBirra) {
		this.id = id;
		setNome(nome);
		setDescrizione(descrizione); 
		setIngredienti(ingredienti);
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
	}	
	
	protected static Ricetta creaRicetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		boolean valid = validation(nome, descrizione, quantitaAcqua, quantitaBirra);
		if (!valid) {
			return null;
		}
		if(id == null)
			return new Ricetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
		else
			return new Ricetta(id, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
	}
	
	protected static boolean validation(String nome, String descrizione, String quantitaAcqua, 
										String quantitaBirra) {
		return validateNome(nome) & //NOSONAR
				validateDescrizione(descrizione) & //NOSONAR
				validateQuantitaAcqua(quantitaAcqua) & //NOSONAR
				validateQuantitaBirra(quantitaBirra) &&
				validateQuantita(quantitaBirra, quantitaAcqua);
	}

	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateDescrizione(String descrizione) {
		if(isStringaVuota(descrizione, "Descrizione")) {
			Notifica.getIstanza().addError("Inserire una descrizione della ricetta"); //Da modificare!!!!!!!!!!!!
			return false;
		}
		if (descrizione.length() >= 500) {  //Da modificare (probabilmente non necessario)!!!!!!!!!!!!
			Notifica.getIstanza().addError("Il nome deve contenere al massimo 500 caratteri"); //Da modificare!!!!!!!!!!!!
			return false;
		}
		return true; 
	}

	private static boolean validateQuantitaAcqua(String quantitaAcqua) {
		return !isStringaVuota(quantitaAcqua, "Quantita acqua")
				&& isNumber(quantitaAcqua, "Quantita' acqua") 
				&& isNotPositive(quantitaAcqua, "Quantita' acqua");
	}
	
	private static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, "Quantita birra")
				&& isNumber(quantitaBirra, "Quantita' birra") 
				&& isNotPositive(quantitaBirra, "Quantita' birra");
	}
	
	private static boolean validateQuantita(String quantitaBirra, String quantitaAcqua) {
		if (convertToNumber(quantitaAcqua) < convertToNumber(quantitaBirra)) {
			Notifica.getIstanza().addError("La quantità di acqua inserita deve essere maggiore della quantita di birra");
			return false;
		}
		return true;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeLW = rimuoviWhiteSpaces(nome);
		this.nome = nomeLW;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	private void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	private void setIngredienti(Set<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public double getQuantitaAcqua() {
		return quantitaAcqua;
	}

	private void setQuantitaAcqua(String quantitaAcqua) {
		double quantitaA = convertToNumber(quantitaAcqua);
		this.quantitaAcqua = quantitaA;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	private void setQuantitaBirra(String quantitaBirra) {
		double quantitaB = convertToNumber(quantitaBirra);
		this.quantitaAcqua = quantitaB;
	}

}
