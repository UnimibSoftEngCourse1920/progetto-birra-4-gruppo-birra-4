package gruppobirra4.brewday.application; //NOSONAR

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;
import gruppobirra4.brewday.errori.Notifica;

public class GestoreRicette {

	private static GestoreRicette istanza;
		
	private GestoreRicette() {
		super();
	}
		
	public static synchronized GestoreRicette getIstanza() {
		if (istanza == null){
			istanza = new GestoreRicette();	
		}
		return istanza;
	}
	
	public Collection<Ricetta> visualizzaRicettario() {
		try {
			Collection<Ricetta> ricettario = Ricettario.getIstanza().visualizzaRicettario();
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return Collections.emptyList();
			}
			return ricettario;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return Collections.emptyList();
		}
	}
	
	public Ricetta visualizzaRicetta(String id) {
		try {
			Ricetta ricetta = Ricettario.getIstanza().visualizzaRicetta(id);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return ricetta;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}
	
	public Ingrediente inserisciIngrediente(String id, String nome, String categoria, String quantita) {
		try {
			Ingrediente ingr = Ingrediente.creaIngrediente(id, nome, categoria, quantita);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return ingr;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}
	
	
	public Ricetta creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti,
			String quantitaAcqua, String quantitaBirra) {
		try {
			Ricetta nuovaRicetta = Ricettario.getIstanza().creaRicetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return nuovaRicetta;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}
	
	public boolean rimuoviRicetta(String id) {
		try {
			Ricettario.getIstanza().rimuoviRicetta(id);
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public Ricetta modificaRicetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti,
			String quantitaAcqua, String quantitaBirra) {
		try {
			Ricetta ricettaModificata = Ricettario.getIstanza().modificaRicetta(id, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return ricettaModificata;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}

}