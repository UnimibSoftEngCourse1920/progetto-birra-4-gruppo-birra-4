package GruppoBirra4.BrewDay.domain;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import GruppoBirra4.BrewDay.errori.Notifica;

public class Ricettario {
	
	private Map<String, Ricetta> ricette;
	private static Ricettario istanza;
	
	private Ricettario() {
		this.ricette = new TreeMap<String, Ricetta>();
	}
		
	public static synchronized Ricettario getIstanza() {
		if (istanza == null){
			istanza = new Ricettario();	
		}
		return istanza;
	}
	
	public void creaRicetta(String nome, String descrizione, Set<QuantitaIngrediente> quantitaIngredienti,
			double quantitaAcqua, double quantitaBirra) {
		
		Ricetta r = new Ricetta(nome, descrizione, quantitaIngredienti, quantitaAcqua, quantitaBirra); //Solleva eccezione
		if (r == null) {
			return;
		}		
		aggiungiRicetta(r);
	}
	
	private void aggiungiRicetta(Ricetta r) {
		if (ricette.put(r.getNome(), r) != null) {
			Notifica.getIstanza().addError("E' gia' stata inserita una ricetta con questo nome");
		}	
	}

	public String visualizzaRicettario() {
		return toString();
	}

	public String visualizzaRicetta(String nomeRicetta) {
		Ricetta r = ricette.get(nomeRicetta);
		if (r == null) {
			//Prendila dal database
		}
		return r.toString();
	}
	
	public String toString() {
		return "Ricettario [ricette=" + ricette.keySet() + "]";
	}
	
}
