package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

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
	
	private void aggiornaCatalogo(Set<Ingrediente> ingredientiRicetta) {
		for (Ingrediente ingr: ingredientiRicetta) {
			CatalogoIngredienti.getIstanza().aggiornaIngrCatalogo(ingr);			
		}
	}
	
	private boolean aggiungiLotto(Lotto lotto) {
		if(checkDisponibilitaIngredienti(lotto.getRicetta().getIngredienti())) {
			lotti = openMapDB();
			lotti.put(lotto.getId(), lotto);
			Database.getIstanza().closeDB();
			return true;
		}
		return false;
	}
	
	//Controlla se gli ingredienti della ricetta sono disponibili nel catalogo e se sono in quantita' sufficiente
	private boolean checkDisponibilitaIngredienti(Set<Ingrediente> ingredientiRicetta) {
		if (CatalogoIngredienti.getIstanza().isCatalogoVuoto() ) {
			Notifica.getIstanza().addError("Non sono presenti ingredienti disponibili nel catalogo");
			return false;
		}
		for (Ingrediente ingr: ingredientiRicetta) {
			if(!CatalogoIngredienti.getIstanza().checkDisponibilitaInCatalogo(ingr)) {
				Notifica.getIstanza().addError("L'ingrediente "+ "\"" + ingr.getCategoria() + " "  
								+ ingr.getNome() +	"\" non è presente o non è disponibile in "
								+ "quantità sufficiente nel catalogo degli ingredienti");
				return false;
			}
		}
		return true; 
	}
	
	public Lotto creaLotto(String nomeRicetta, String quantitaBirra) {
		Ricetta ricetta = getRicettaFromRicettario(nomeRicetta);
		Lotto lotto = Lotto.creaLotto(quantitaBirra, ricetta);
	
		if(lotto != null && aggiungiLotto(lotto)) {
			aggiornaCatalogo(lotto.getRicetta().getIngredienti());
			verificaIngredientiPerProssimaProduzione(lotto.getRicetta().getIngredienti());
			return lotto;
		}
		return null;
	}
	
	public SortedMap<String, Lotto> getListaLotti() {
		lotti = openMapDB();
		SortedMap<String, Lotto> returnMap = getLottiHelper();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	//Ritorna una mappa di java che contiene tutti i lotti nella lista dei lotti
	private SortedMap<String, Lotto> getLottiHelper() {
		SortedMap<String, Lotto> returnMap = new TreeMap<>();
		for (Lotto l : lotti.values()) {
			returnMap.put(l.getId(), new Lotto(l.getId(),
												l.getData(),
												l.getNoteGusto(),
												l.getNoteProblemi(),
												l.getRicetta()));
		}
		return returnMap;
	}
	
	private Ricetta getRicettaFromRicettario(String nomeRicetta) {
		Ricetta r = Ricettario.getIstanza().getRicettaByNome(nomeRicetta);
		r.setDescrizione(""); //La descrizione non è necessaria
		return r;
	}
	
	public void modificaNote(String idLotto, String noteGusto, String noteProblemi) {
		lotti = openMapDB();
		Lotto l = lotti.get(idLotto);
		Lotto lottoModificato = new Lotto(l.getId(), l.getData(), noteGusto, noteProblemi, l.getRicetta());
		lotti.replace(idLotto, lottoModificato);
		Database.getIstanza().closeDB();		
	}
	
	public void rimuoviLotto(String idLotto) {
		if (idLotto != null && !idLotto.isEmpty()) {
			lotti = openMapDB();
			lotti.remove(idLotto);
			Database.getIstanza().closeDB();
		}	
	}
	
	//Controlla che ci siano abbastanza ingredienti nel catalogo per una successiva produzione
	private boolean verificaIngredientiPerProssimaProduzione(Set<Ingrediente> ingredientiRicetta) {
		for (Ingrediente ingr: ingredientiRicetta) {
			if(!CatalogoIngredienti.getIstanza().checkDisponibilitaInCatalogo(ingr)) {
				Notifica.getIstanza().addError("L'ingrediente "+ "\"" + ingr.getCategoria() + " "  
								+ ingr.getNome() +	"\" non sarà disponibile in quantità sufficiente "
								+ "per la prossima produzione");
				Notifica.getIstanza().setTipoErrori("Avvertenza per la prossima produzione");
				return false;
			}
		}
		return true; 
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
	
	public Lotto visualizzaLotto(String idLotto) {
		lotti = openMapDB();
		Lotto lotto = lotti.get(idLotto);
		Database.getIstanza().closeDB();
		return lotto;
	}

}
