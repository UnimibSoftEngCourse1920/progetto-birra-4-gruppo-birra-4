package gruppobirra4.brewday.application.gestori; //NOSONAR

import java.util.Collection;
import java.util.Collections;

import gruppobirra4.brewday.domain.ingredienti.ListaSpesa;
import gruppobirra4.brewday.domain.ingredienti.QuantitaListaSpesa;
import gruppobirra4.brewday.errori.Notifica;

public class GestoreListaSpesa {
	
	private static GestoreListaSpesa istanza;
	
	private GestoreListaSpesa() {
		super();
	}
			
	public static synchronized GestoreListaSpesa getIstanza() {
		if (istanza == null){
			istanza = new GestoreListaSpesa();	
		}
		return istanza;
	}
	
	public Collection<QuantitaListaSpesa> visualizzaListaSpesa() {
		try {
			Collection<QuantitaListaSpesa> listaSpesa = ListaSpesa.getIstanza().visualizzaListaSpesa();
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return Collections.emptyList();
			}
			return listaSpesa;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return Collections.emptyList();
		}
	}
	
	public QuantitaListaSpesa aggiungiIngrediente(String nome, String categoria, String quantita) {
		try {
			QuantitaListaSpesa nuovoIngrediente = ListaSpesa.getIstanza().aggiungiIngrediente(nome, categoria, quantita);
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
	
	public QuantitaListaSpesa modificaQuantita(String id, String quantita) {
		try {
			QuantitaListaSpesa ingredienteModificato = ListaSpesa.getIstanza().modificaQuantita(id, quantita);
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
	
	public boolean rimuoviIngrediente(String id) {
		try {
			ListaSpesa.getIstanza().rimuoviIngrediente(id);
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public boolean svuotaLista() {
		try {
			ListaSpesa.getIstanza().svuotaLista();
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public boolean acquistaIngrediente(String id) {
		try {
			ListaSpesa.getIstanza().acquistaIngrediente(id);
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public boolean acquistaTutto() {
		try {
			ListaSpesa.getIstanza().acquistaTutto();
			return true;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
}
