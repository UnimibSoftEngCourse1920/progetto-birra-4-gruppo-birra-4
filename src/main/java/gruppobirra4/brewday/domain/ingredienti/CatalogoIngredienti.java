package gruppobirra4.brewday.domain.ingredienti;

import java.util.TreeMap;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente.Categoria;
import gruppobirra4.brewday.errori.Notifica;

public class CatalogoIngredienti {
	private TreeMap<String, Ingrediente> ingredienti;
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
	
	public void creaIngrediente(String nome, Categoria categoria, double quantitaDisponibile) {
		Ingrediente ingrediente = Ingrediente.creaIngrediente(nome, categoria, quantitaDisponibile);
		if(ingrediente != null)
			aggiungiIngrediente(ingrediente);
	}
		
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			Notifica.getIstanza().addError("L'ingrediente è già presente nel catalogo");
			return;
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
	}
	
	public boolean checkCatalogo(String nome, Categoria categoria) {
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
