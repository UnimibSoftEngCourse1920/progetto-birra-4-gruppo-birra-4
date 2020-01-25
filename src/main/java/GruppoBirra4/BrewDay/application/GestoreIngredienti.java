package GruppoBirra4.BrewDay.application;

import GruppoBirra4.BrewDay.domain.CatalogoIngredienti;
import GruppoBirra4.BrewDay.domain.Ingrediente;
import GruppoBirra4.BrewDay.domain.Ingrediente.Categoria;

public class GestoreIngredienti {

	private static GestoreIngredienti istanza;
			
	private GestoreIngredienti() {
		super();
	}
			
	public static synchronized GestoreIngredienti getIstanza() {
		if (istanza == null){
			istanza = new GestoreIngredienti();	
		}
		return istanza;
	}

	public void creaIngrediente(String nome, Categoria categoria, double quantitaDisponibile) {
		if(CatalogoIngredienti.getIstanza().checkNome(nome))
			return;
		Ingrediente ingrediente = Ingrediente.creaIngrediente(nome, categoria, quantitaDisponibile);
		CatalogoIngredienti.getIstanza().aggiungiIngrediente(ingrediente);
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		CatalogoIngredienti.getIstanza().rimuoviIngrediente(ingrediente);
	}
		
}
