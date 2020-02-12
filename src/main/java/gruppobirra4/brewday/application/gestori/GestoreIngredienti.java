	package gruppobirra4.brewday.application.gestori; //NOSONAR

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
		try {
			Collection<Ingrediente> catalogo = CatalogoIngredienti.getIstanza().visualizzaCatalogo();
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return Collections.emptyList();
			}
			return catalogo;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return Collections.emptyList();
		}
	}
	
	public Ingrediente creaIngrediente(String nome, String categoria, String quantita) {
		try {
			Ingrediente nuovoIngrediente = CatalogoIngredienti.getIstanza().creaIngrediente(nome, categoria, quantita);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return nuovoIngrediente;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}
	
	public boolean rimuoviIngrediente(String id) {
		try {
			CatalogoIngredienti.getIstanza().rimuoviIngrediente(id);
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public Ingrediente modificaIngrediente(String id, String nome, String categoria, String quantita) {
		try {
			Ingrediente ingredienteModificato = CatalogoIngredienti.getIstanza().modificaIngrediente(id, nome, categoria, quantita);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return ingredienteModificato;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}

}