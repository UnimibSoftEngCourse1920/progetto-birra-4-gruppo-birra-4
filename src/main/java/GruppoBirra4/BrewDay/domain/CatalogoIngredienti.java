package GruppoBirra4.BrewDay.domain;

import java.util.TreeMap;

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

	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
			ingrediente.setQuantitaDisponibile(nuovaQuantita);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
			ingrediente.setNome(nuovoNome);
	
	}
	
	public void modificaIngrediene(Ingrediente ingrediente, String nuovoNome, double nuovaQuantita) {
		modificaIngrediente(ingrediente, nuovaQuantita);
		modificaIngrediente(ingrediente, nuovoNome);
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		if(ingredienti.containsValue(ingrediente)) {
			ingredienti.remove(ingrediente.getNome());
		}
	}
	
	public void aggiungiIngrediente(String nome, Categoria categoria, double quantita) {
		if(!(ingredienti.containsKey(nome))) {
			Ingrediente nuovoIngrediente = new Ingrediente(nome, categoria, quantita);
			ingredienti.put(nome, nuovoIngrediente);
		}
	}
}
