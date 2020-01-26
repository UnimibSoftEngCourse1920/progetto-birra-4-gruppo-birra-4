package GruppoBirra4.BrewDay.application;

import java.util.Set;
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
	
	
	public String visualizzaRicettario() {
		return Ricettario.getIstanza().visualizzaRicettario();
	}
	
	public String visualizzaRicetta(String nomeRicetta) {
		return Ricettario.getIstanza().visualizzaRicetta(nomeRicetta);
	}
	
	public void creaRicetta(String nome, String descrizione, Set<QuantitaIngrediente> quantitaIngredienti,
							double quantitaAcqua, double quantitaBirra) {
		Ricettario.getIstanza().creaRicetta (nome, descrizione, quantitaIngredienti, quantitaAcqua, quantitaBirra);
	}
	
	

}
