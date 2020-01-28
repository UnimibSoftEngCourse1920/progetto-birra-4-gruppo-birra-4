package gruppoBirra4.brewDay.domain.ricette;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import gruppoBirra4.brewDay.domain.ingredienti.Ingrediente;
import gruppoBirra4.brewDay.errori.Notifica;

public class Ricettario {
	
	private Map<String, Ricetta> ricette;
	private static Ricettario istanza;
	
	private Ricettario() {
		this.ricette = new TreeMap<>();
	}
		
	public static synchronized Ricettario getIstanza() {
		if (istanza == null){
			istanza = new Ricettario();	
		}
		return istanza;
	}
	
	public void creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti,
			double quantitaAcqua, double quantitaBirra) {
		
		Ricetta r = Ricetta.creaRicetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra); //Solleva eccezione
		if (r != null) {
			aggiungiRicetta(r);
		}		
	}
	
	private void aggiungiRicetta(Ricetta nuovaRicetta) {
		/*if (ricette.put(r.getNome(), r) != null) {
			Notifica.getIstanza().addError("E' gia' stata inserita una ricetta con questo nome");
		}*/
		if(checkRicettario(nuovaRicetta.getNome())) {	
			Notifica.getIstanza().addError("E' gia' stata inserita una ricetta con questo nome");
			return;
		}
		ricette.put(nuovaRicetta.getId(), nuovaRicetta);
	}
	
	//Controlla se e' gia' presente una ricetta con lo stesso nome
	private boolean checkRicettario(String nomeRicetta) {
		for (Ricetta r : ricette.values()) {
			if((r.getNome().equals(nomeRicetta))) {
				return true;
			}
		}
		return false;
	}

	public String visualizzaRicettario() {
		//Prendi i nomi delle ricette dal database
		return toString();
	}

	public String visualizzaRicetta(String nomeRicetta) {
		Ricetta r = getRicettaFromRicettario(nomeRicetta);
		if (r == null) {
			//Prendila dal database
		}
		return r.toString();
	}
	
	private Ricetta getRicettaFromRicettario(String nomeRicetta) {
		for (Ricetta r : ricette.values()) {
			if((r.getNome().equals(nomeRicetta))) {
				return r;
			}
		}
		return null;
	}
	
	public String toString() {
		return "Ricettario [ricette=" + ricette.keySet() + "]";
	}
	
}
