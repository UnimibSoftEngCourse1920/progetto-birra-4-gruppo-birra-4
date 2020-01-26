package GruppoBirra4.BrewDay.domain;

import java.util.Map;
import java.util.TreeMap;

public class Ricettario {
	
	private Map<String, Ricetta> ricette;
	private static Ricettario istanza;
	
	private Ricettario() {
		super();
		this.ricette = new TreeMap<String, Ricetta>();
	}
		
	public static synchronized Ricettario getIstanza() {
		if (istanza == null){
			istanza = new Ricettario();	
		}
		return istanza;
	}
	
	private void aggiungiRicetta(Ricetta ricetta) {
		if (ricette.put(ricetta.getNome(), ricetta) != null) {
			//Solleva eccezione
		}	
	}
	
	public void creaRicetta(String nome, String descrizione, QuantitaIngrediente[] quantitaIngredienti,
			double quantitaAcqua, double quantitaBirra) {
		
		Ricetta r = new Ricetta(nome, descrizione, quantitaIngredienti, quantitaAcqua, quantitaBirra); //Solleva eccezione
		aggiungiRicetta(r); //Solleva eccezione
	}
	
}
