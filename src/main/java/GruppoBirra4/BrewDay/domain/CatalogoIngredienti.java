package GruppoBirra4.BrewDay.domain;

import java.util.TreeMap;

import GruppoBirra4.BrewDay.domain.Ingrediente;
import GruppoBirra4.BrewDay.domain.Ingrediente.Categoria;

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
		aggiungiIngrediente(ingrediente);
	}
		
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		if(checkCatalogo(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria())) {	
			//Solleva eccezione
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
