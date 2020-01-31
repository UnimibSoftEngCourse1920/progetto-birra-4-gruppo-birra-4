package gruppobirra4.brewday.domain.ingredienti;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

public class CatalogoIngredienti {
	
	private HTreeMap<String, Ingrediente> ingredienti;
	private static CatalogoIngredienti istanza;
	private static final String TABLE_CATALOGO = "CatalogoIngredienti";
	
	private CatalogoIngredienti() {
		this.ingredienti = (HTreeMap<String, Ingrediente>) Database.getIstanza()
				.getDb().hashMap(TABLE_CATALOGO)
				.keySerializer(Serializer.STRING)
				.createOrOpen();
	}
	
	public static synchronized CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti();	
		}
		return istanza;
	}
	
	private HTreeMap<String, Ingrediente> openMapDB() {
		return (HTreeMap<String, Ingrediente>) Database.getIstanza().openMapDB(TABLE_CATALOGO);
	}
	
	public Ingrediente creaIngrediente(String nome, String categoria, String quantitaDisponibile) {
		Ingrediente ingrediente = Ingrediente.creaIngrediente(nome, categoria, quantitaDisponibile);
		if(ingrediente != null) {
			aggiungiIngrediente(ingrediente);
			return ingrediente;
		}
		return null;
	}
	
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		ingredienti = openMapDB();
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			return;
			
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
	}
	
	public boolean checkCatalogo(String nome, String categoria) {
		if (ingredienti.isEmpty()) {
			return false;
		}
		for (Ingrediente ing : ingredienti.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria)))
				return true;
		}
		return false;
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		ingredienti = openMapDB();
		if(ingredienti.containsValue(ingrediente)) { 
			ingredienti.remove(ingrediente.getId()); 
			Database.getIstanza().getDb().commit();
		}
		Database.getIstanza().closeDB();
	}
	
	public SortedMap<String, Ingrediente> getIngredienti() {
		ingredienti = openMapDB();
		return getIngredientiHelper();
	}
	
	public Collection<Ingrediente> visualizzaCatalogo() {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			return Collections.emptyList();
		}
		Collection<Ingrediente> returnMap = getIngredientiHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	private SortedMap<String, Ingrediente> getIngredientiHelper() {
		SortedMap<String, Ingrediente> returnMap = new TreeMap<>();
		ingredienti = openMapDB();
		for (Ingrediente i : ingredienti.values()) {
			returnMap.put(i.getId(), new Ingrediente(i.getNome(),
														i.getCategoria(),
														Double.toString(i.getQuantita())));
		}
		Database.getIstanza().closeDB();
		return returnMap;
		
	}

}
