package gruppobirra4.brewday.application.gestori;

import java.util.Collection;
import java.util.Collections;

import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;

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
	
	public Collection<Ingrediente> visualizzaCatalogo() {
		Collection<Ingrediente> catalogo = CatalogoIngredienti.getIstanza().visualizzaCatalogo();
		if (Notifica.getIstanza().hasErrors()) {
			Notifica.getIstanza().notificaErrori();
			Notifica.getIstanza().svuotaNotificheErrori();
			return Collections.emptyList();
		}
		return catalogo;
	}
	
	public Ingrediente creaIngrediente(String nome, String categoria, String quantita) {
		//try {
		Ingrediente nuovoIngrediente = CatalogoIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
		//} catch (Exception e) {
		//	Notifica.getIstanza().svuotaNotificheErrori();
		//	Notifica.getIstanza().notificaEccezione(e);
		//}
		if (Notifica.getIstanza().hasErrors()) {
			Notifica.getIstanza().notificaErrori();
			Notifica.getIstanza().svuotaNotificheErrori();
			return null;
		}
		return nuovoIngrediente;
	}
	
	public void rimuoviIngrediente(Ingrediente ingrediente) {
		CatalogoIngredienti.getIstanza().rimuoviIngrediente(ingrediente);
	}
	/*
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.modificaIngrediente(ingrediente, nuovaQuantita);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.modificaIngrediente(ingrediente, nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, Categoria nuovaCategoria) {
		ingrediente.modificaIngrediente(ingrediente, nuovaCategoria);
	}
	*/
}
