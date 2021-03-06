package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

import static gruppobirra4.brewday.foundation.utility.InputUtente.convertToNumber;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.errori.Notifica;

public class ListaSpesa {
	
	private HTreeMap<String, Double> lista;
	private static ListaSpesa istanza;
	
	private static final String TABLE_LISTASPESA = "ListaSpesa";
	
	private ListaSpesa() {
		this.lista = Database.getIstanza()
				.getDb().hashMap(TABLE_LISTASPESA)
				.keySerializer(Serializer.STRING)
				.valueSerializer(Serializer.DOUBLE)
				.createOrOpen();
	}
	
	public static synchronized ListaSpesa getIstanza() {
		if (istanza == null){
			istanza = new ListaSpesa();	
		}
		return istanza;
	}
	
	//Per test
	public static synchronized void setIstanzaNull() {
		istanza = null;
	}
	
	private HTreeMap<String, Double> openMapDB() {
		return (HTreeMap<String, Double>) Database.getIstanza().openMapDB(TABLE_LISTASPESA);
	}
	
	//Acquista un ingrediente togliendolo dalla lista della spesa e aumenta la quantita' disponibile nel catalogo
	public void acquistaIngrediente(String id) {
		lista = openMapDB();
		double qt = lista.get(id);
		Database.getIstanza().closeDB();
		Ingrediente ing = CatalogoIngredienti.getIstanza().getIngredienteById(id);
		ing = CatalogoIngredienti.getIstanza().modificaIngrediente(id, ing.getNome(), ing.getCategoria(), Double.toString(ing.getQuantita()+qt));
		if (ing != null) {
			rimuoviIngrediente(id);
		}
	}
	
	public void acquistaTutto() {
		Collection<QuantitaListaSpesa> copiaLista = visualizzaListaSpesa();
		for (QuantitaListaSpesa entry : copiaLista) {
			acquistaIngrediente(entry.getIngrediente().getId());
		}
	}
	
	//Aggiunge un ingrediente alla lista della spesa, se non e' gia' presente, creandolo nel catalogo con quantita' pari a 0 se non e' gia' presente
	public QuantitaListaSpesa aggiungiIngrediente(String nome, String categoria, String quantita) {
		Ingrediente tempIng = Ingrediente.creaIngrediente("", nome, categoria, quantita);
		if(tempIng == null) {
			return null;
		}
		double quantitaDaAcquistare = tempIng.getQuantita();
		if(Double.doubleToLongBits(quantitaDaAcquistare) == Double.doubleToLongBits(0.0)) {
			Notifica.getIstanza().addError("Il campo \" Quantita \" deve essere un numero maggiore di zero");		
			return null;
		}
		Ingrediente ing = CatalogoIngredienti.getIstanza().checkCatalogoPerSpesa(tempIng.getNome(), tempIng.getCategoria());
		if(ing != null) {	
			lista = openMapDB();
			if(checkLista(ing.getId())) {
				Database.getIstanza().closeDB();
				Notifica.getIstanza().addError("L'ingrediente è già nella lista");
				return null;
			}
			lista.put(ing.getId(), quantitaDaAcquistare);
			Database.getIstanza().closeDB();
			return new QuantitaListaSpesa(ing, quantitaDaAcquistare);
		} else {
			Database.getIstanza().closeDB();
			return null;
		}
	}
	
	//Controlla se un ingrediente e' nella lista della spesa
	private boolean checkLista(String id) {
		if (lista.isEmpty()) {
			return false;
		}
		for (Map.Entry<String, Double> entry : lista.entrySet()) {
			if(entry.getKey().equals(id))
				return true;
		}
		return false;	
	}
	
	private Collection<QuantitaListaSpesa> getIngredientiHelper() {
		Collection<QuantitaListaSpesa> returnSet = new HashSet<>();
		for (Map.Entry<String, Double> entry : lista.entrySet()) {
			returnSet.add(new QuantitaListaSpesa(CatalogoIngredienti.getIstanza().getIngredienteByIdDB(entry.getKey()),
												entry.getValue()));
		}
		return returnSet;
	}
	
	//Modifica la quantita' da acquistare di un ingrediente
	public QuantitaListaSpesa modificaQuantita(String id, String quantita) {
		lista = openMapDB();
		if(!(Ingrediente.validateQuantita(quantita))) {
			Database.getIstanza().closeDB();
			return null;
		}
		double quantitaDaAcquistare = convertToNumber(quantita);
		if(Double.doubleToLongBits(quantitaDaAcquistare) == Double.doubleToLongBits(0.0)) {
			Notifica.getIstanza().addError("Il campo \" Quantita \" deve essere un numero maggiore di zero");
			Database.getIstanza().closeDB();
			return null;
		}		
		lista.replace(id, quantitaDaAcquistare);
		Database.getIstanza().closeDB();
		return new QuantitaListaSpesa(CatalogoIngredienti.getIstanza().getIngredienteById(id), 
									quantitaDaAcquistare);
	}
	
	public void rimuoviIngrediente(String id) {
		lista = openMapDB();
		if(lista.containsKey(id)) {
			lista.remove(id);
		}
		Database.getIstanza().closeDB();
	}
	
	//Svuota la lista della spesa senza acquistare alcun ingrediente
	public void svuotaLista(){
		lista = openMapDB();
		if(!lista.isEmpty()) {
			lista.clear();
		}
		Database.getIstanza().closeDB();
	}
	
	public Collection<QuantitaListaSpesa> visualizzaListaSpesa() {
		lista = openMapDB();
		if (lista.isEmpty()) {
			Database.getIstanza().closeDB();
			return Collections.emptySet();
		}
		Collection<QuantitaListaSpesa> returnSet = getIngredientiHelper();
		Database.getIstanza().closeDB();
		return returnSet;
	}

}