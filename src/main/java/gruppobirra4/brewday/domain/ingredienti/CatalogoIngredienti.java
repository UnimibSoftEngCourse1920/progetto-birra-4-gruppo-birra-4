package gruppobirra4.brewday.domain.ingredienti;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

public class CatalogoIngredienti {
	private SortedMap<String, Ingrediente> ingredienti;
	private static CatalogoIngredienti istanza;
	
	private CatalogoIngredienti() {
		this.ingredienti = new TreeMap<>();
	}
	
	public static synchronized CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti();	
		}
		return istanza;
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
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			return;
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
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
		if(ingredienti.containsValue(ingrediente)) { 
			ingredienti.remove(ingrediente.getId()); 
		}
	}

	public SortedMap<String, Ingrediente> getIngredienti() {
		return ingredienti;
	}
	
	public Collection<Ingrediente> visualizzaCatalogo() {
		if (ingredienti.isEmpty()) {
			return null;
		}
		return ingredienti.values();
	}
	
	
	/*
	public Ingrediente[] visualizzaCatalogo() {
		Ingrediente[] catalogo = ingredienti.values().toArray(new Ingrediente[0]);
		return catalogo;
	}
	*/
	
	
}
