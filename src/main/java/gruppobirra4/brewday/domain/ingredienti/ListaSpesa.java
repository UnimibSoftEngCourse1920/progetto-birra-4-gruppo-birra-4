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
		lista = openMapDB();
		if(!(Ingrediente.validation(nome, quantita))) {
			Database.getIstanza().closeDB();
			return null;
		}
		Ingrediente ingrediente = Ingrediente.creaIngrediente(null, nome, categoria, "0");
		String id = CatalogoIngredienti.getIstanza().checkCatalogo(nome, categoria, ingrediente.getId());
		if(id != null) {
			lista.put(id, Double.parseDouble(quantita));
			Database.getIstanza().closeDB();
			return new QuantitaListaSpesa(CatalogoIngredienti.getIstanza().getIngredienteById(id),
					Double.parseDouble(quantita));
		}
		else {
			CatalogoIngredienti.getIstanza().aggiungiIngrediente(ingrediente);
			lista.put(ingrediente.getId(), Double.parseDouble(quantita));
			return new QuantitaListaSpesa(ingrediente, Double.parseDouble(quantita));
		}
	}
	
	public void rimuoviIngrediente(String id) {
		lista = openMapDB();
		lista.remove(id);
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
		if(!lista.isEmpty())
			lista.clear();
		Database.getIstanza().closeDB();
	}
}