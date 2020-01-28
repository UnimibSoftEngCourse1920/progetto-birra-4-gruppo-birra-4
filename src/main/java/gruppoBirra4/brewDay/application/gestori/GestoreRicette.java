package gruppoBirra4.brewDay.application.gestori;

import java.util.Set;

import gruppoBirra4.brewDay.domain.ingredienti.Ingrediente;
import gruppoBirra4.brewDay.domain.ricette.Ricettario;
import gruppoBirra4.brewDay.errori.Notifica;

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
	
	
	public String visualizzaRicettario() {
		return Ricettario.getIstanza().visualizzaRicettario();
	}
	
	public String visualizzaRicetta(String nomeRicetta) {
		return Ricettario.getIstanza().visualizzaRicetta(nomeRicetta);
	}
	
	public void creaRicetta(String nome, String descrizione, Set<Ingrediente> ingredienti,
							double quantitaAcqua, double quantitaBirra) {
		
		//try {
		Ricettario.getIstanza().creaRicetta (nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
		//} catch (Exception e) {
			//Notifica.getIstanza().svuotaNotificheErrori();
			//Notifica.getIstanza().notificaEccezione(e);
		//}
		if (Notifica.getIstanza().hasErrors()) {
			Notifica.getIstanza().notificaErrori();
		}
		
	}
	
	

}
