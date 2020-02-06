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
		Database.getIstanza().closeDB();
		return true;
	}
	
	//Controlla se e' presente nel catalogo un ingrediente con lo stesso nome e con la stessa categoria (che non sia se stesso)
	public boolean checkCatalogo(String nome, String categoria, String id) {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			return false;
		}
		for (Ingrediente i : ingredienti.values()) {
			if((i.getNome().equals(nome)) && (i.getCategoria().equals(categoria)) && (!(i.getId().equals(id))))
				return true;
		}
		return false;
	}
	
	public Ingrediente checkCatalogoPerSpesa(String nome, String categoria) {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			Database.getIstanza().closeDB();
			return creaIngrediente(nome, categoria, "0");
		}
		/*for (Ingrediente ing : ingredienti.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria))) {
				Database.getIstanza().closeDB();
				return ing;
			}
		}
		Database.getIstanza().closeDB();*/
		Ingrediente ing = getIngredienteByNomeCategoria(nome, categoria);
		if (ing != null) {
			return ing;
		}
		ing = getIstanza().creaIngrediente(nome, categoria, "0");
		return ing;
	}
	
	public boolean checkDisponibilitaInCatalogo(Ingrediente ingrRicetta) {
		ingredienti = openMapDB();
		if (ingredienti.isEmpty()) {
			Notifica.getIstanza().addError("Non sono presenti ingredienti disponibili nel catalogo");
			Database.getIstanza().closeDB();
			return false;
		}
		/*for (Ingrediente ing : ingredienti.values()) {
			if(ing.equals(ingrRicetta) && (int)Math.round(ing.getQuantita()) < (int)Math.round(ingrRicetta.getQuantita())) {
				Database.getIstanza().closeDB();
				return false;
			}
		}*/
		Ingrediente ing = getIngredienteByNomeCategoria(ingrRicetta.getNome(), ingrRicetta.getCategoria());
		if (ing != null && (int)Math.round(ing.getQuantita()) >= (int)Math.round(ingrRicetta.getQuantita()) ){
			return true;
		}
		Notifica.getIstanza().addError("L'ingrediente "+ "\"" + ingrRicetta.getCategoria() + " " + 
										ingrRicetta.getNome() +	"\" non è disponibile nel catalogo");
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
		Collection<Ingrediente> returnMap = getIngredientiDisponibiliHelper().values();
		Database.getIstanza().closeDB();
		return returnMap;
	}
	
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
		ingredienti = openMapDB();	
		Ingrediente ingrModificato =  Ingrediente.creaIngrediente(id, nome, categoria, quantita);
		if (ingrModificato == null) {
			Database.getIstanza().closeDB();
			return null;
		}
		if(!(checkCatalogo(ingrModificato.getNome(), ingrModificato.getCategoria(), ingrModificato.getId()))) {		
			ingredienti.replace(id, ingrModificato);
			Database.getIstanza().closeDB();
			return ingrModificato;
		}	
		Notifica.getIstanza().addError("E' già presente un ingrediente con lo stesso nome e categoria");
		Database.getIstanza().closeDB();
		return null;
	}
	
	public void aggiornaIngrCatalogo(Ingrediente ingrRicetta) {
		ingredienti = openMapDB();
		Ingrediente ingr = getIngredienteByNomeCategoria(ingrRicetta.getNome(), ingrRicetta.getCategoria());
		modificaIngrediente(ingr.getId(), ingr.getNome(), ingr.getCategoria(), 
							Double.toString((int)Math.round(ingr.getQuantita() - ingrRicetta.getQuantita())));		
	}
	
	
	public Ingrediente getIngredienteById(String id) {
		ingredienti = openMapDB();
		return ingredienti.get(id);
		//Database.getIstanza().closeDB();
	}
	
	public Ingrediente getIngredienteByNomeCategoria(String nome, String categoria) {
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
