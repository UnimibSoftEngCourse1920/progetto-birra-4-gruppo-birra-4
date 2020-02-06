package gruppobirra4.brewday.domain.ricette;

import java.util.Map;
import java.util.Set;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

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
	
	public Lotto creaLotto(String idRicetta, String quantitaBirra) {
		Ricetta ricetta = getRicettaFromRicettario(idRicetta);
		ricetta.convertiRicettaInValoreNormale();
		Lotto lotto = Lotto.creaLotto(quantitaBirra, ricetta);
	
		if(lotto != null && aggiungiLotto(lotto)) {
			aggiornaCatalogo(lotto.getRicetta().getIngredienti());
			return lotto;
		}
		return null;
	}
	
	private boolean aggiungiLotto(Lotto lotto) {
		lotti = openMapDB();
		if(checkDisponibilitaIngredienti(lotto.getRicetta().getIngredienti())) {
			lotti.put(lotto.getId(), lotto);
			Database.getIstanza().closeDB();
			return true;
		}
		Database.getIstanza().closeDB();
		return false;
	}
	
	private boolean checkDisponibilitaIngredienti(Set<Ingrediente> ingredientiRicetta) {
		for (Ingrediente ingr: ingredientiRicetta) {
			if(!CatalogoIngredienti.getIstanza().checkDisponibilitaInCatalogo(ingr)) {
				return false;
			}
		}
		return true; 
	}
	
	private void aggiornaCatalogo(Set<Ingrediente> ingredientiRicetta) {
		for (Ingrediente ingr: ingredientiRicetta) {
			CatalogoIngredienti.getIstanza().aggiornaIngrCatalogo(ingr);			
		}
	}
	
	private Ricetta getRicettaFromRicettario(String idRicetta) {
		Ricetta r = Ricettario.getIstanza().getRicetta(idRicetta);
		r.setDescrizione(""); //La descrizione non è necessaria
		return r;
	}

	/*public Collection<Lotto> visualizzaListaLotti() {
		lotti = openMapDB();
		if (lotti.isEmpty()) {
			Database.getIstanza().closeDB();
			return Collections.emptyList();
		}
		Collection<Lotto> returnMap = getLottiHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}*/
	
	//Ritorna una mappa di java che contiene tutti i lotti nella lista dei lotti
	/*private SortedMap<String, Lotto> getLottiHelper() {
		SortedMap<String, Lotto> returnMap = new TreeMap<>();
		for (Lotto l : lotti.values()) {
			returnMap.put(l.getId(), new Lotto(l.getId(),
												l.getData(),
												l.getNoteProblemi(),
												l.getNoteGusto(),
												l.getIdRicetta()));
		}
		return returnMap;
	}*/
	


}
