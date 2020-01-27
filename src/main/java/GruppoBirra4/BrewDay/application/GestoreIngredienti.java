package GruppoBirra4.BrewDay.application;

import GruppoBirra4.BrewDay.domain.CatalogoIngredienti;
import GruppoBirra4.BrewDay.domain.Ingrediente;
import GruppoBirra4.BrewDay.domain.Ingrediente.Categoria;
import GruppoBirra4.BrewDay.errori.Notifica;

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
	/*
	public Ingrediente[] visualizzaCatalogo() {
		return CatalogoIngredienti.getIstanza().visualizzaCatalogo();
	}
*/
	public void creaIngrediente(String nome, Categoria categoria, double quantita) {
		try {
			CatalogoIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
		}
		if (Notifica.getIstanza().hasErrors()) {
		Notifica.getIstanza().notificaErrori();
		}
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		CatalogoIngredienti.getIstanza().rimuoviIngrediente(ingrediente);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.modificaIngrediente(ingrediente, nuovaQuantita);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.modificaIngrediente(ingrediente, nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, Categoria nuovaCategoria) {
		ingrediente.modificaIngrediente(ingrediente, nuovaCategoria);
	}
	
}
