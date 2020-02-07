package gruppobirra4.brewday.domain; //NOSONAR

import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.mapdb.HTreeMap;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;

public class BirraDelGiorno {
	
	private TreeMap<String, Double> valori;
	

	private BirraDelGiorno() {
		super();
	}
	
	private HTreeMap<String, Ricetta> openMapDB() {
		return (HTreeMap<String, Ricetta>) Database.getIstanza().openMapDB("Ricettario");
	}
	
	public Ricetta calcolaBirraDelGiorno(String quantitaBirra) {
		if(!(Ricetta.validateQuantitaBirra(quantitaBirra))) {
			return null;
		}
		HTreeMap<String, Ricetta> ricette = openMapDB();
		if(ricette.isEmpty()) {
			Database.getIstanza().closeDB();
			Notifica.getIstanza().addError("Non ci sono ricette nel ricettario");
			return null;
		}
		for (Ricetta r: ricette.values()) {
			valori.put(r.getId(), calcolaDifferenza(r, Double.parseDouble(quantitaBirra)));
		}
		return ricette.get(trovaValoreMax(valori));
		
		
	}

	private Double calcolaDifferenza(Ricetta ricetta, double quantitaBirra) {
		double differenza = 0;
		for (Ingrediente ingRicetta : ricetta.getIngredienti()) {
			String id = ingRicetta.getId();
			double quantitaCatalogo = CatalogoIngredienti.getIstanza().getIngredienteById(id).getQuantita();
			double quantitaRicetta = ingRicetta.getQuantita() * quantitaBirra;
			if(quantitaCatalogo - (quantitaRicetta)<0)
				return Double.MAX_VALUE;
			differenza = quantitaCatalogo - quantitaRicetta;
		}
		return differenza;
	}
	
	private String trovaValoreMax(TreeMap<String, Double> valori) {
		Double max = Collections.max(valori.values());
		String key = null;
		for(Entry<String, Double> entry : valori.entrySet()) {
			if(entry.getValue() < max) {
				key = entry.getKey();
				max = entry.getValue();
			}
		}
		return key;
	}
	
}
