package GruppoBirra4.BrewDay.domain;

import java.util.Set;

public class Ricetta {
	
	private String nome;
	private String descrizione;
	private Set<QuantitaIngrediente> quantitaIngredienti;
	//private String passaggi;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	
	private Ricetta(String nome, String descrizione, Set<QuantitaIngrediente> quantitaIngredienti, 
					double quantitaAcqua, double quantitaBirra) {
		if (validation(nome, descrizione, quantitaAcqua, quantitaBirra)) {
			return;
		}
		setNome(nome); //Solleva eccezione
		setDescrizione(descrizione); 
		setQuantitaIngredienti(quantitaIngredienti); //Solleva eccezione
		//setPassaggi(passaggi);
		setQuantitaAcqua(quantitaAcqua); //Solleva eccezione
		setQuantitaBirra(quantitaBirra); //Solleva eccezione	
	}
	
	protected static Ricetta creaRicetta(String nome, String descrizione, Set<QuantitaIngrediente> quantitaIngredienti, 
					double quantitaAcqua, double quantitaBirra) {
		//boolean b validation(nome, descrizione, quantitaAcqua, quantitaBirra);
		return new Ricetta(nome, descrizione, quantitaIngredienti, quantitaAcqua, quantitaBirra);
	}
	
	private boolean validation(String nome, String descrizione, double quantitaAcqua, double quantitaBirra) {
		validateNome(nome);
		
	}

	private void validateNome(String nome2) {
		// TODO Auto-generated method stub
		
	}

	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeUC = nome.replaceAll("\\s+", " ").trim().toUpperCase();
		//sostituisce tutti i whitespaces (spazi + newline + tab +ecc)
		if(nomeUC.isEmpty()) {				//con un singolo spazio e rimuove tutti gli spazi iniziali e finali
			//Solleva eccezione
		} else if (nomeUC.length() >= 30) {
			//Solleve eccezione
		}	
		this.nome = nomeUC;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	private void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<QuantitaIngrediente> getQuantitaIngredienti() {
		return quantitaIngredienti;
	}

	private void setQuantitaIngredienti(Set<QuantitaIngrediente> quantitaIngredienti) {
		this.quantitaIngredienti = quantitaIngredienti;
	}

	/*public String getPassaggi() {
		return passaggi;
	}

	private void setPassaggi(String passaggi) {
		this.passaggi = passaggi;
	}*/

	public double getQuantitaAcqua() {
		return quantitaAcqua;
	}

	private void setQuantitaAcqua(double quantitaAcqua) {
		if (quantitaAcqua <= 0) {
			// Solleva eccezione
		} else if (quantitaAcqua < quantitaBirra) {
			//Solleva eccezione
		}
		this.quantitaAcqua = quantitaAcqua;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	private void setQuantitaBirra(double quantitaBirra) {
		if (quantitaBirra <= 0) {
			// Solleva eccezione
		} else if (quantitaAcqua < quantitaBirra) {
			//Solleva eccezione
		}
		this.quantitaBirra = quantitaBirra;
	}	
	

}
