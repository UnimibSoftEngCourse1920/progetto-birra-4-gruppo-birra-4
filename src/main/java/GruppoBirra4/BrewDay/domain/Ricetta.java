package GruppoBirra4.BrewDay.domain;

import java.util.Set;
import java.util.UUID;

import GruppoBirra4.BrewDay.errori.Notifica;

public class Ricetta {
	
	private String id; 
	private String nome;
	private String descrizione;
	private Set<Ingrediente> ingredienti;
	//private String passaggi;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	
	private Ricetta(String nome, String descrizione, Set<Ingrediente> ingredienti, 
					double quantitaAcqua, double quantitaBirra) {
		if (validation(nome, descrizione, quantitaAcqua, quantitaBirra)) {
			return;
		}
		this.id = UUID.randomUUID().toString();
		setNome(nome);
		setDescrizione(descrizione); 
		setIngredienti(ingredienti); //Solleva eccezione
		setQuantitaAcqua(quantitaAcqua); //Solleva eccezione
		setQuantitaBirra(quantitaBirra); //Solleva eccezione	
	}
	
	protected static Ricetta creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti, 
					double quantitaAcqua, double quantitaBirra) {
		boolean valid = validation(nome, descrizione, quantitaAcqua, quantitaBirra);
		if (!valid) {
			return null;
		}
		return new Ricetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
	}
	
	private static boolean validation(String nome, String descrizione, double quantitaAcqua, double quantitaBirra) {
		return validateNome(nome) &&
				validateDescrizione(descrizione) &&
				validateQuantitaAcqua(quantitaAcqua) &&
				validateQuantitaBirra(quantitaBirra) &&
				validateQuantita(quantitaBirra, quantitaAcqua);
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
	
	private static boolean validateDescrizione(String descrizione) {
		String descrizione2 = descrizione.replaceAll("\\s+", " ").trim();
		if (descrizione2.length() >= 500) {  //Da modificare!!!!!!!!!!!!
			Notifica.getIstanza().addError("Il nome deve contenere al massimo 500 caratteri"); //Da modificare!!!!!!!!!!!!
			return false;
		}
		return true; 
	}

	private static boolean validateQuantitaAcqua(double quantitaAcqua) {
		if (quantitaAcqua <= 0) {
			Notifica.getIstanza().addError("La quantità inserita deve essere maggiore di zero");
			return false;
		}			
		return true;
	}
	
	private static boolean validateQuantitaBirra(double quantitaBirra) {
		if (quantitaBirra <= 0) {
			Notifica.getIstanza().addError("La quantità inserita deve essere maggiore di zero");
			return false;
		}	
		return true;
	}
	
	private static boolean validateQuantita(double quantitaBirra, double quantitaAcqua) {
		if (quantitaAcqua < quantitaBirra) {
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
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
		this.nome = nomeUC;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	private void setDescrizione(String descrizione) {
		String descrizione2 = descrizione.replaceAll("\\s+", " ").trim();
		this.descrizione = descrizione2;
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

	private void setQuantitaAcqua(double quantitaAcqua) {
		this.quantitaAcqua = quantitaAcqua;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	private void setQuantitaBirra(double quantitaBirra) {
		this.quantitaBirra = quantitaBirra;
	}

	@Override
	public String toString() {
		return "Ricetta [nome=" + nome + ", descrizione=" + descrizione + ", ingredienti=" + ingredienti
				+ ", quantitaAcqua=" + quantitaAcqua + ", quantitaBirra=" + quantitaBirra + "]";
	}	
	
	
	

}
