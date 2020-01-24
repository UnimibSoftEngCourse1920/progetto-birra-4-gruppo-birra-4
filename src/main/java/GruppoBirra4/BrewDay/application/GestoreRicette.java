package gruppoBirra4.brewDay.application;

import java.util.TreeSet;

import gruppoBirra4.brewDay.domain.Ingrediente;
import gruppoBirra4.brewDay.domain.Ricetta;
import gruppoBirra4.brewDay.domain.Ricettario;

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
	
	/*public String visualizzaRicettario() {

	}*/
	
	public void creaRicetta(String nome, String descrizione, Ingrediente[] ingredienti, String passaggi, double quantitaAcqua, double quantitaBirra) {
		Ricetta ricetta = Ricetta.creaRicetta (nome, descrizione, ingredienti, passaggi, quantitaAcqua, quantitaBirra);
		Ricettario.getIstanza().aggiungiRicetta(ricetta);

	}
	

}
