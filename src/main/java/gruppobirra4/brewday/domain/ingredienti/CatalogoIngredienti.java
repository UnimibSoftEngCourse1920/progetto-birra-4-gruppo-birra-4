package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

import java.util.Collection;
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
		Ingrediente ingrediente = Ingrediente.creaIngrediente(null, nome, categoria, quantitaDisponibile);
		if(ingrediente != null && aggiungiIngrediente(ingrediente)) {
			return ingrediente;
		}
		return null;
	}
	
	public boolean aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		ingredienti = openMapDB();
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria(), nuovoIngrediente.getId())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			Database.getIstanza().closeDB();
			return false;
			
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
		return true;
	}
	
	//Controlla se e' presente nel catalogo un ingrediente con lo stesso nome e con la stessa categoria (che non sia se stesso)
	public boolean checkCatalogo(String nome, String categoria, String id) {
		if (ingredienti.isEmpty()) {
			return false;
		}
		for (Ingrediente i : ingredienti.values()) {
			if((i.getNome().equals(nome)) && (i.getCategoria().equals(categoria)) && (!(i.getId().equals(id))))
				return true;
		}
		return false;
	}
	
	public void rimuoviIngrediente(String id) {
		ingredienti = openMapDB();
		ingredienti.remove(id); 
		Database.getIstanza().getDb().commit();
		Database.getIstanza().closeDB();
	}
	
	//Ritorna una mappa di java che contiene tutti gli ingredienti nel catalogo
	private SortedMap<String, Ingrediente> getIngredientiHelper() {
		SortedMap<String, Ingrediente> returnMap = new TreeMap<>();
		for (Ingrediente i : ingredienti.values()) {
			returnMap.put(i.getId(), new Ingrediente(i.getId(),
													i.getNome(),
													i.getCategoria(),
													Double.toString(i.getQuantita())));
		}
		return returnMap;
	}
	
	public SortedMap<String, Ingrediente> getIngredienti() {
		ingredienti = openMapDB();
		SortedMap<String, Ingrediente> returnMap = getIngredientiHelper();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	public Collection<Ingrediente> visualizzaCatalogo() {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			return null;
		}
		Collection<Ingrediente> returnMap = getIngredientiHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	public Ingrediente modificaIngrediente(String id, String nome, String categoria, String quantita) {
		ingredienti = openMapDB();	
		Ingrediente ingrModificato =  Ingrediente.creaIngrediente(id, nome, categoria, quantita);
		if (ingrModificato == null) {
			Database.getIstanza().closeDB();
			return null;
		}
		if(!checkCatalogo(ingrModificato.getNome(), ingrModificato.getCategoria(), ingrModificato.getId())) {		
			ingredienti.replace(id, ingrModificato);
			Database.getIstanza().getDb().commit();
			Database.getIstanza().closeDB();
			return ingrModificato;
		}	
		Notifica.getIstanza().addError("E' già presente un ingrediente con lo stesso nome e categoria");
		Database.getIstanza().closeDB();
		return null;
	}
	
	/*
	public Ingrediente aggiungiIngrediente(String idRicetta, String idIngr, String nomeIngr, String categoriaIngr, String quantitaIngr) {
	ingredienti = openMapDB();
	Ingrediente ingrModificato
	}
	
	public modificaIngrediente() {
	}
	
	public eliminaIngrediente() {
	}
	*/
}
