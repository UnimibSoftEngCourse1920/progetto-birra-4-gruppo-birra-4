package gruppobirra4.brewday.domain.ricette;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;

public class ListaLotti {
	
	private Map<String, Lotto> lotti;
	private static ListaLotti istanza;
	private static final String TABLE_LOTTI = "ListaLotti";
	
	
	private ListaLotti() {
		this.lotti = (HTreeMap<String, Lotto>) Database.getIstanza()
				.getDb().hashMap(TABLE_LOTTI)
				.keySerializer(Serializer.STRING)
				.createOrOpen();
	}
	
	public static synchronized ListaLotti getIstanza() {
		if (istanza == null){
			istanza = new ListaLotti();	
		}
		return istanza;
	}
	
	private HTreeMap<String, Lotto> openMapDB() {
		return (HTreeMap<String, Lotto>) Database.getIstanza().openMapDB(TABLE_LOTTI);
	}
	
	public Lotto creaLotto(String idRicetta) {
		Lotto lotto = new Lotto(idRicetta);
		aggiungiLotto(idRicetta, lotto);
		return lotto;
	}
	
	private void aggiungiLotto(String idRicetta, Lotto lotto) {
		lotti = openMapDB();
		lotti.put(idRicetta, lotto);
		Database.getIstanza().closeDB();
	}

	public Collection<Lotto> visualizzaListaLotti() {
		lotti = openMapDB();
		if (lotti.isEmpty()) {
			Database.getIstanza().closeDB();
			return Collections.emptyList();
		}
		Collection<Lotto> returnMap = getLottiHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	//Ritorna una mappa di java che contiene tutti i lotti nella lista dei lotti
	private SortedMap<String, Lotto> getLottiHelper() {
		SortedMap<String, Lotto> returnMap = new TreeMap<>();
		for (Lotto l : lotti.values()) {
			returnMap.put(l.getId(), new Lotto(l.getId(),
												l.getData(),
												l.getNoteProblemi(),
												l.getNoteGusto(),
												l.getIdRicetta()));
		}
		return returnMap;
	}
	
	
	
	
	

}
