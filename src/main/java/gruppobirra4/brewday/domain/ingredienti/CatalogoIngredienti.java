package gruppobirra4.brewday.domain.ingredienti;

import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

public class CatalogoIngredienti {
	private HTreeMap<String,Ingrediente> ingredienti;
	private static CatalogoIngredienti istanza;
	
	private CatalogoIngredienti() {
		this.ingredienti = (HTreeMap<String, Ingrediente>) Database.getIstanza()
				.getDb().hashMap("CatalogoIngredienti")
				.keySerializer(Serializer.STRING)
				.createOrOpen();
	}
	
	public static synchronized CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti();	
		}
		return istanza;
	}
	
	private DB getDb() {
		return Database.getIstanza().getDb();
	}
	
	public Ingrediente creaIngrediente(String nome, String categoria, double quantitaDisponibile) {
		Ingrediente ingrediente = Ingrediente.creaIngrediente(nome, categoria, quantitaDisponibile);
		if(ingrediente != null)
			aggiungiIngrediente(ingrediente);
		return ingrediente;
	}
		
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		ingredienti = (HTreeMap<String, Ingrediente>) getDb()
				.hashMap("CatalogoIngredienti").open();
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			ingredienti.close();
			return;
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
		getDb().commit();
		getDb().close();
		
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
