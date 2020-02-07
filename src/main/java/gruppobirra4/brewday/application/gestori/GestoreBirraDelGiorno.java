package gruppobirra4.brewday.application.gestori;

import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;

public class GestoreBirraDelGiorno {
	
	private static GestoreBirraDelGiorno istanza;
	
	private GestoreBirraDelGiorno() {
		super();
	}
			
	public static synchronized GestoreBirraDelGiorno getIstanza() {
		if (istanza == null){
			istanza = new GestoreBirraDelGiorno();	
		}
		return istanza;
	}
	
	public Ricetta calcolaBirraDelGiorno(String quantitaBirra) {
		try {
			Ricetta birraDelGiorno = calcolaBirraDelGiorno(quantitaBirra);
			if (Notifica.getIstanza().hasErrors()) {
				Notifica.getIstanza().notificaErrori();
				Notifica.getIstanza().svuotaNotificheErrori();
				return null;
			}
			return birraDelGiorno;
		} catch (Exception e) {
			Notifica.getIstanza().svuotaNotificheErrori();
			Notifica.getIstanza().notificaEccezione(e);
			return null;
		}
	}

}
