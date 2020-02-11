package gruppobirra4.brewday.domain; //NOSONAR

import java.util.Map;
import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;
import gruppobirra4.brewday.errori.Notifica;

public class BirraDelGiorno {
	
	private String idRicetta;

	public BirraDelGiorno() {
		this.idRicetta = null;
	}
	
	public Ricetta calcolaBirraDelGiorno(String quantita) {
		if(!(Ricetta.validateQuantitaBirra(quantita))) {
			return null;
		}
		double quantitaBirra = Double.parseDouble(quantita);
		
		if (Ricettario.getIstanza().isRicettarioVuoto()) {
			Notifica.getIstanza().addError("Non ci sono ricette nel ricettario");
			return null;
		}
		if (CatalogoIngredienti.getIstanza().isCatalogoVuoto()) {
			Notifica.getIstanza().addError("Non ci sono ingredienti nel catalogo");
			return null;
		}
		
		Map<String, Ricetta> ricette = Ricettario.getIstanza().getRicetteDB();
		Map<String, Ingrediente> catalogo = CatalogoIngredienti.getIstanza().getIngredientiDB();
		
		double max = 0;
		double sommaQuantita = 0;
		for (Ricetta r: ricette.values()) {
			sommaQuantita = calcolaQuantitaMassima(catalogo, r, quantitaBirra);
			max = calcolaMassimo(max, sommaQuantita, r.getId());
		}
		
		if(Double.doubleToLongBits(max) == Double.doubleToLongBits(0.0)) {
			Database.getIstanza().closeDB();
			return null;
		}
		
		if(idRicetta == null) {
			Notifica.getIstanza().addError("Nessuna ricetta producibile con il quantitativo inserito");
			Database.getIstanza().closeDB();
			return null;
		}
		return ricette.get(idRicetta);
	}

	private double calcolaMassimo(double max, double sommaQuantita, String idRicetta) {
		if ((int) Math.round(sommaQuantita - max) > 0) {
			double massimo = sommaQuantita;
			this.idRicetta = idRicetta;
			return massimo;
		}
		return max;
	}
	
	private double calcolaQuantitaMassima(Map<String, Ingrediente> catalogo, Ricetta ricetta, double quantitaBirra) {
		double sommaQuantita = 0;
	
		for (Ingrediente ingRicetta : ricetta.getIngredienti()) {
			Ingrediente ingCatalogo = getIngredienteByNomeCategoria(catalogo, ingRicetta.getNome(), ingRicetta.getCategoria());
			if(ingCatalogo == null) {
				return 0;
			}
			double quantitaCatalogo = ingCatalogo.getQuantita();
			double quantitaRicetta = Math.round(ingRicetta.getQuantita() * quantitaBirra);
			if((int) Math.round(quantitaCatalogo - (quantitaRicetta)) < 0) {
				return 0;
			}
			sommaQuantita = sommaQuantita + quantitaRicetta;
		}
		return sommaQuantita;
	}
	
	private static Ingrediente getIngredienteByNomeCategoria(Map<String, Ingrediente> catalogo, String nome, String categoria) {
		for (Ingrediente ing : catalogo.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria))) {
				return ing;
			}
		}
		return null;
	}

}
