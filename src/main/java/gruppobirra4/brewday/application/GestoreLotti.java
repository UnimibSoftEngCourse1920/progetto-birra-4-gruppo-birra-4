package gruppobirra4.brewday.application; //NOSONAR

import java.util.Collection;
import java.util.Collections;

import gruppobirra4.brewday.domain.ricette.ListaLotti;
import gruppobirra4.brewday.domain.ricette.Lotto;
import gruppobirra4.brewday.errori.Notifica;

public class GestoreLotti {

	private static GestoreLotti istanza;
	
	private GestoreLotti() {
		super();
	}
			
	public static synchronized GestoreLotti getIstanza() {
		if (istanza == null){
			istanza = new GestoreLotti();	
		}
		return istanza;
	}
	
	public Lotto creaLotto(String nomeRicetta, String quantitaBirra) {
		try {
			Lotto lotto = ListaLotti.getIstanza().creaLotto(nomeRicetta, quantitaBirra);			
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				if (Notifica.getIstanza().getTipoErrori() != null) {
					Notifica.getIstanza().setNullTipoErrori();
					return lotto;
				}
				return null;
			}
			return lotto;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}
	
	public boolean modificaNote(String idLotto, String noteGusto, String noteProblemi) {
		try {
			ListaLotti.getIstanza().modificaNote(idLotto, noteGusto, noteProblemi);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return false;
			}
			return true;
		} catch (Exception e){
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public boolean rimuoviLotto(String idLotto) {
		try {
			ListaLotti.getIstanza().rimuoviLotto(idLotto);
			return true;			
		} catch (Exception e){
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return false;
		}
	}
	
	public Collection<Lotto> visualizzaListaLotti() {
		try {
			Collection<Lotto> lotti = ListaLotti.getIstanza().visualizzaListaLotti();
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return Collections.emptyList();
			}
			return lotti;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return Collections.emptyList();
		}
	}
	
	public Lotto visualizzaLotto(String idLotto) {
		try {
			Lotto lotto = ListaLotti.getIstanza().visualizzaLotto(idLotto);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return lotto;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}

}