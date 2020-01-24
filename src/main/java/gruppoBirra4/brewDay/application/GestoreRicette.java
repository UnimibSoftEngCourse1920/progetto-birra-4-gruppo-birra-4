package GruppoBirra4.BrewDay.application;

import java.util.TreeSet;

import GruppoBirra4.BrewDay.domain.Ingrediente;
import GruppoBirra4.BrewDay.domain.Ricetta;
import GruppoBirra4.BrewDay.domain.Ricettario;

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
