package GruppoBirra4.BrewDay.domain;

import java.util.TreeMap;

import GruppoBirra4.BrewDay.domain.Ingrediente;

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

	public void rimuoviIngrediente(Ingrediente ingrediente) {
		if(ingredienti.containsValue(ingrediente)) {
			ingredienti.remove(ingrediente.getNome());
		}
	}
		
	public void aggiungiIngrediente(Ingrediente nuovoIngrediente) {
		for (Ingrediente ing : ingredienti.values()) {
			if((nuovoIngrediente.getNome().equals(ing.getNome())) && ((nuovoIngrediente.getCategoria()).equals(ing.getCategoria())))
					return; //da modificare
		}
		ingredienti.put(nuovoIngrediente.getId(), nuovoIngrediente);
	}
/*
	public boolean checkNome(Ingrediente) {
		
	return false;	
	}
*/	
}
