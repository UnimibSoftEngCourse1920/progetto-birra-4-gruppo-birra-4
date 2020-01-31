	package gruppobirra4.brewday.domain.ricette;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

public class Ricettario {
	
	private Map<String, Ricetta> ricette;
	private static Ricettario istanza;
	private static final String TABLE_RICETTARIO = "Ricettario";
	
	private Ricettario() {
		this.ricette = (HTreeMap<String, Ricetta>) Database.getIstanza()
				.getDb().hashMap(TABLE_RICETTARIO)
				.keySerializer(Serializer.STRING)
				.createOrOpen();
	}
		
	public static synchronized Ricettario getIstanza() {
		if (istanza == null){
			istanza = new Ricettario();	
		}
		return istanza;
	}
	
	private HTreeMap<String, Ricetta> openMapDB() {
		return (HTreeMap<String, Ricetta>) Database.getIstanza().openMapDB(TABLE_RICETTARIO);
	}
	
	public void creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti,
			String quantitaAcqua, String quantitaBirra) {
		
		Ricetta r = Ricetta.creaRicetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra); //Solleva eccezione
		if (r != null) {
			aggiungiRicetta(r);
		}		
	}
	
	private void aggiungiRicetta(Ricetta nuovaRicetta) {
		ricette = openMapDB();
		if(checkRicettario(nuovaRicetta.getNome())) {	
			Notifica.getIstanza().addError("E' gia' stata inserita una ricetta con questo nome");
			Database.getIstanza().closeDB();
			return;
		}
		ricette.put(nuovaRicetta.getId(), nuovaRicetta);
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
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
	
	public void rimuoviRicetta(Ricetta ricetta) {
		ricette = openMapDB();
		if(ricette.containsValue(ricetta)) { 
			ricette.remove(ricetta.getId()); 
			Database.getIstanza().getDb().commit();
		}
		Database.getIstanza().closeDB();
	}
	
	private SortedMap<String, Ricetta> getRicetteHelper() {
		SortedMap<String, Ricetta> returnMap = new TreeMap<>();
		for (Ricetta r : ricette.values()) {
			returnMap.put(r.getId(), new Ricetta(r.getNome(),
														r.getDescrizione(),
														r.getIngredienti(),
														Double.toString(r.getQuantitaAcqua()),
														Double.toString(r.getQuantitaBirra())));
		}
		return returnMap;
	}

	public Collection<Ricetta> visualizzaRicettario() {
		ricette = openMapDB();
		if (ricette.isEmpty()) {
			return Collections.emptyList();
		}
		Collection<Ricetta> returnMap = getRicetteHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	/*
	public String visualizzaRicetta(String nome) {
		Ricetta r = getRicettaFromRicettario(nome);
		if (r == null) {
			//Prendila dal database
		}
		return r.toString();
	}
	*/
	private Ricetta getRicettaFromRicettario(String nomeRicetta) {
		ricette = openMapDB();
		for (Ricetta r : ricette.values()) {
			if((r.getNome().equals(nomeRicetta))) {
			Database.getIstanza().closeDB();
				return r;
			}
		}
		Database.getIstanza().closeDB();
		return null;
	}
	/*
	public String toString() {
		return "Ricettario [ricette=" + ricette.keySet() + "]";
	}
	*/
}
