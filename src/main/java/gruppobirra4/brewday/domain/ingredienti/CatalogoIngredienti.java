package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

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
	
	//Per test
	public static synchronized void setIstanzaNull() {
		istanza = null;
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
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria(), nuovoIngrediente.getId())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			return false;
			
		}
		ingredienti = openMapDB();
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
		Database.getIstanza().closeDB();
		return true;
	}
	
	//Controlla se e' presente nel catalogo un ingrediente con lo stesso nome e con la stessa categoria (che non sia se stesso)
	public boolean checkCatalogo(String nome, String categoria, String id) {
		if (isCatalogoVuoto()) {
			return false;
		}
		ingredienti = openMapDB();
		for (Ingrediente i : ingredienti.values()) {
			if((i.getNome().equals(nome)) && (i.getCategoria().equals(categoria)) && (!(i.getId().equals(id)))) {
				Database.getIstanza().closeDB();
				return true;
			}
		}
		Database.getIstanza().closeDB();
		return false;
	}
	
	public Ingrediente checkCatalogoPerSpesa(String nome, String categoria) {
		if (isCatalogoVuoto()) {
			return creaIngrediente(nome, categoria, "0");
		}
		Ingrediente ing = getIngredienteByNomeCategoria(nome, categoria);
		if (ing != null) {
			return ing;
		}
		ing = creaIngrediente(nome, categoria, "0");
		return ing;
	}
	
	//Controlla se ingrRicetta e' disponibile in catalogo e se e' in quantita sufficiente
	public boolean checkDisponibilitaInCatalogo(Ingrediente ingrRicetta) {
		Ingrediente ing = getIngredienteByNomeCategoria(ingrRicetta.getNome(), ingrRicetta.getCategoria());
		return ing != null && (int)Math.round(ing.getQuantita()) >= (int)Math.round(ingrRicetta.getQuantita());			
	}
	
	public boolean isCatalogoVuoto() {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			Database.getIstanza().closeDB();
			return true;
		}
		Database.getIstanza().closeDB();
		return false;
	}
	
	
	public void rimuoviIngrediente(String id) {
		ingredienti = openMapDB();
		ingredienti.remove(id); 
		ListaSpesa.getIstanza().rimuoviIngrediente(id);
		Database.getIstanza().closeDB();
	}
	
	public Collection<Ingrediente> visualizzaCatalogo() {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			Database.getIstanza().closeDB();
			return Collections.emptyList();
		}
		Collection<Ingrediente> returnMap = getIngredientiHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
	public HTreeMap<String, Ingrediente> getIngredientiDB() {
		ingredienti = openMapDB();
		return ingredienti;
	}
	
	/*
	//Ritorna una mappa di java che contiene tutti gli ingredienti disponibili nel catalogo
	private SortedMap<String, Ingrediente> getIngredientiDisponibiliHelper() {
		SortedMap<String, Ingrediente> returnMap = new TreeMap<>();
		for (Ingrediente i : ingredienti.values()) {
			if ((Double.doubleToLongBits(i.getQuantita()) != Double.doubleToLongBits(0.0))) {
				returnMap.put(i.getId(), new Ingrediente(i.getId(),
						i.getNome(),
						i.getCategoria(),
						Double.toString(i.getQuantita())));
			}
		}
		return returnMap;
	}
	*/
	public SortedMap<String, Ingrediente> getIngredienti() {
		ingredienti = openMapDB();
		SortedMap<String, Ingrediente> returnMap = getIngredientiHelper();
		Database.getIstanza().closeDB();
		return returnMap;
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

	public Ingrediente modificaIngrediente(String id, String nome, String categoria, String quantita) {
		Ingrediente ingrModificato =  Ingrediente.creaIngrediente(id, nome, categoria, quantita);
		if (ingrModificato == null) {
			return null;
		}
		if(!(checkCatalogo(ingrModificato.getNome(), ingrModificato.getCategoria(), ingrModificato.getId()))) {		
			ingredienti = openMapDB();
			ingredienti.replace(id, ingrModificato);
			Database.getIstanza().closeDB();
			return ingrModificato;
		}	
		Notifica.getIstanza().addError("E' già presente un ingrediente con lo stesso nome e categoria");
		return null;
	}
	
	public void aggiornaIngrCatalogo(Ingrediente ingrRicetta) {
		//ingredienti = openMapDB();
		Ingrediente ingr = getIngredienteByNomeCategoria(ingrRicetta.getNome(), ingrRicetta.getCategoria());
		modificaIngrediente(ingr.getId(), ingr.getNome(), ingr.getCategoria(), 
							Double.toString(Math.round(ingr.getQuantita() - ingrRicetta.getQuantita())));
		//Database.getIstanza().closeDB();
	}
	
	
	public Ingrediente getIngredienteById(String id) {
		ingredienti = openMapDB();
		Ingrediente ing = ingredienti.get(id);
		Database.getIstanza().closeDB();
		return ing;
	}
	
	//A differenza di getIngredienteById, non chiude il Database
	public Ingrediente getIngredienteByIdDB(String id) {
		ingredienti = openMapDB();
		return ingredienti.get(id);
	}
	
	public Ingrediente getIngredienteByNomeCategoria(String nome, String categoria) {
		ingredienti = openMapDB();
		for (Ingrediente ing : ingredienti.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria))) {
				Database.getIstanza().closeDB();
				return ing;
			}
		}
		Database.getIstanza().closeDB();
		return null;
	}

}
