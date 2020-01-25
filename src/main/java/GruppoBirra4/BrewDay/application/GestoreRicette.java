package GruppoBirra4.BrewDay.application;

import java.util.TreeSet;

import GruppoBirra4.BrewDay.domain.QuantitaIngrediente;
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
	
	public void creaRicetta(String nome, String descrizione, QuantitaIngrediente[] quantitaIngredienti,
							double quantitaAcqua, double quantitaBirra) {
		Ricettario.getIstanza().creaRicetta (nome, descrizione, quantitaIngredienti, quantitaAcqua, quantitaBirra);
	}
	

}
