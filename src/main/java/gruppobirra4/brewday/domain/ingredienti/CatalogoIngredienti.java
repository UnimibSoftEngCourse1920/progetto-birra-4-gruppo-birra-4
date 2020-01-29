package gruppobirra4.brewday.domain.ingredienti;

import java.util.TreeMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.application.database.Database;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;
import gruppobirra4.brewday.application.database.Database;

public class CatalogoIngredienti {
	private HTreeMap<String,Ingrediente> ingredienti;
	private static CatalogoIngredienti istanza;
	
	private CatalogoIngredienti() {
		this.ingredienti = (HTreeMap<String, Ingrediente>) Database.getIstanza()
				.getDb().hashMap("CatalogoIngredienti")
				.create();
	}
	
	public static synchronized CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti();	
		}
		return istanza;
	}
	
	public Ingrediente creaIngrediente(String nome, String categoria, double quantitaDisponibile) {
		Ingrediente ingrediente = Ingrediente.creaIngrediente(nome, categoria, quantitaDisponibile);
		if(ingrediente != null)
			aggiungiIngrediente(ingrediente);
		return ingrediente;
	}
		
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		ingredienti = (HTreeMap<String, Ingrediente>) Database.getIstanza().getDb()
				.hashMap("CatalogoIngredienti").createOrOpen();
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			return;
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
		ingredienti.close();
		
	}
	
	public boolean checkCatalogo(String nome, String categoria) {
		for (Ingrediente ing : ingredienti.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria)))
				return true;
		}
		return false;
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		if(ingredienti.containsValue(ingrediente)) { 
			ingredienti.remove(ingrediente.getId()); 
		}
	}
	
	/*
	public Ingrediente[] visualizzaCatalogo() {
		Ingrediente[] catalogo = ingredienti.values().toArray(new Ingrediente[0]);
		return catalogo;
	}
	*/
}
