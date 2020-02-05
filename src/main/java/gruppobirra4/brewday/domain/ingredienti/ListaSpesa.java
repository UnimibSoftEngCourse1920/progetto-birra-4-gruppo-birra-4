package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import gruppobirra4.brewday.database.Database;

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
	
	private HTreeMap<String, Double> openMapDB() {
		return (HTreeMap<String, Double>) Database.getIstanza().openMapDB(TABLE_LISTASPESA);
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
	
	private Collection<QuantitaListaSpesa> getIngredientiHelper() {
		Collection<QuantitaListaSpesa> returnSet = new HashSet<>();
		for (Map.Entry<String, Double> entry : lista.entrySet()) {
			returnSet.add(new QuantitaListaSpesa(CatalogoIngredienti.getIstanza().getIngredienteById(entry.getKey()),
												entry.getValue()));
		}
		return returnSet;
	}

	public QuantitaListaSpesa aggiungiIngrediente(String nome, String categoria, String quantita) {
		Ingrediente tempIng = Ingrediente.creaIngrediente("", nome, categoria, quantita);
		Ingrediente ing = CatalogoIngredienti.getIstanza().checkCatalogoPerSpesa(tempIng.getNome(), tempIng.getCategoria());

		if(ing != null) {	
			lista = openMapDB();
			lista.put(ing.getId(), Double.parseDouble(quantita));
			Database.getIstanza().closeDB();
			return new QuantitaListaSpesa(ing, Double.parseDouble(quantita));
		} else {
			Database.getIstanza().closeDB();
			return null;
		}
	}
	
	public void rimuoviIngrediente(String id) {
		lista = openMapDB();
		if(lista.containsKey(id)) {
			lista.remove(id);
		}
		Database.getIstanza().closeDB();
	}
	
	public QuantitaListaSpesa modificaQuantita(String id, String quantita) {
		lista = openMapDB();
		if(!(Ingrediente.validateQuantita(quantita))) {
			Database.getIstanza().closeDB();
			return null;
		}
		lista.replace(id, Double.parseDouble(quantita));
		Database.getIstanza().closeDB();
		return new QuantitaListaSpesa(CatalogoIngredienti.getIstanza().getIngredienteById(id),
				Double.parseDouble(quantita));
	}
		
	
	public void svuotaLista(){
		lista = openMapDB();
		if(!lista.isEmpty()) {
			lista.clear();
		}
		Database.getIstanza().closeDB();
	}
	
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
}