	package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.util.Collection;
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
	
	public Ricetta creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti,
			String quantitaAcqua, String quantitaBirra) {
		Ricetta ricetta = Ricetta.creaRicetta(null, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
		if(ricetta != null && aggiungiRicetta(ricetta)) {
			return ricetta;
		}
		return null;		
	}
	
	private boolean aggiungiRicetta(Ricetta nuovaRicetta) {
		ricette = openMapDB();
		if(checkRicettario(nuovaRicetta.getNome(), nuovaRicetta.getId())) {	
			Notifica.getIstanza().addError("La ricetta è già nel ricettario");
			Database.getIstanza().closeDB();
			return false;
		}
		ricette.put(nuovaRicetta.getId(), nuovaRicetta);
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
		return true;
	}
	
	//Controlla se e' gia' presente una ricetta con lo stesso nome (che non sia lo stesso)
	private boolean checkRicettario(String nomeRicetta, String id) {
		if (ricette.isEmpty()) {
			return false;
		}
		for (Ricetta r : ricette.values()) {
			if((r.getNome().equals(nomeRicetta) && (!(r.getId().equals(id))))) {
				return true;
			}
		}
		return false;
	}
	
	public void rimuoviRicetta(String id) {
		ricette = openMapDB();
		ricette.remove(id); 
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
	}
	
	//Ritorna una mappa di java che contiene tutte le ricette nel ricettario
	private SortedMap<String, Ricetta> getRicetteHelper() {
		SortedMap<String, Ricetta> returnMap = new TreeMap<>();
		for (Ricetta r : ricette.values()) {
			returnMap.put(r.getId(), new Ricetta(r.getId(),
												r.getNome(),
												r.getDescrizione(),
												r.getIngredienti(),
												Double.toString(r.getQuantitaAcqua()),
												Double.toString(r.getQuantitaBirra())));
		}
		return returnMap;
	}

	public SortedMap<String, Ricetta> getRicette() {
		ricette = openMapDB();
		SortedMap<String, Ricetta> returnMap = getRicetteHelper();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	public Collection<Ricetta> visualizzaRicettario() {
		ricette = openMapDB();
		if (ricette.isEmpty()) {
			return null;
		}
		Collection<Ricetta> returnMap = getRicetteHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	/*
	private Ricetta getRicettaFromRicettario(String id) {
		ricette = openMapDB();
		for (Ricetta r : ricette.values()) {
			if((r.getId().equals(id))) {
			Database.getIstanza().closeDB();
				return r;
			}
		}
		Database.getIstanza().closeDB();
		return null;
	}
	*/
	
	public Ricetta modificaRicetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti,
			String quantitaAcqua, String quantitaBirra) {
		ricette=openMapDB();
		Ricetta ricModificata = Ricetta.creaRicetta(id, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
		if (ricModificata == null) {
			Database.getIstanza().closeDB();
			return null;
		}
		if(!(checkRicettario(ricModificata.getNome(), ricModificata.getId()))) {
			ricette.replace(id, ricModificata);
			Database.getIstanza().getDb().commit();
			Database.getIstanza().closeDB();
			return ricModificata;
		}
		Notifica.getIstanza().addError("E' già presente un ingrediente con lo stesso nome e categoria");
		Database.getIstanza().closeDB();
		return null;
	}

}
