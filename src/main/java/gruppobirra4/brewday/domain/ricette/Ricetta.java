package gruppobirra4.brewday.domain.ricette;

import java.util.Set;
import java.util.UUID;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

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
	
	protected static Ricetta creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		boolean valid = validation(nome, descrizione, quantitaAcqua, quantitaBirra);
		if (!valid) {
			return null;
		}
		return new Ricetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
	}
	
	private static boolean validation(String nome, String descrizione, String quantitaAcqua, 
										String quantitaBirra) {
		return validateNome(nome) &&
				validateDescrizione(descrizione) &&
				validateQuantitaAcqua(quantitaAcqua) &&
				validateQuantitaBirra(quantitaBirra) &&
				validateQuantita(quantitaBirra, quantitaAcqua);
	}

	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateDescrizione(String descrizione) {
		String descrizione2 = descrizione.replaceAll("\\s+", " ").trim();
		if (descrizione2.length() >= 500) {  //Da modificare!!!!!!!!!!!!
			Notifica.getIstanza().addError("Il nome deve contenere al massimo 500 caratteri"); //Da modificare!!!!!!!!!!!!
			return false;
		}
		return true; 
	}

	private static boolean validateQuantitaAcqua(String quantitaAcqua) {
		return !isStringaVuota(quantitaAcqua, "Quantita disponibile")
				&& isNumber(quantitaAcqua) 
				&& isNotPositive(quantitaAcqua);
	}
	
	private static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, "Quantita disponibile")
				&& isNumber(quantitaBirra) 
				&& isNotPositive(quantitaBirra);
	}
	
	private static boolean validateQuantita(String quantitaBirra, String quantitaAcqua) {
		if (convertToNumber(quantitaAcqua) < convertToNumber(quantitaBirra)) {
			Notifica.getIstanza().addError("La quantità di acqua inserita deve essere maggiore della quantita di birra");
			return false;
		}
		return true;
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
	
	@Override
	public String toString() {
		return "Ricetta [nome=" + nome + ", descrizione=" + descrizione + ", ingredienti=" + ingredienti
				+ ", quantitaAcqua=" + quantitaAcqua + ", quantitaBirra=" + quantitaBirra + "]";
	}	
	
}
