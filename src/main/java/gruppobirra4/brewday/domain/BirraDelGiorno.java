package gruppobirra4.brewday.domain; //NOSONAR

import java.util.Collections;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;
import gruppobirra4.brewday.errori.Notifica;

public class BirraDelGiorno {
	
	private static TreeMap<String, Double> valori = new TreeMap<>();
	

	private BirraDelGiorno() {
		super();
	}
	
	public static Ricetta calcolaBirraDelGiorno(String quantitaBirra) {
		if(!(Ricetta.validateQuantitaBirra(quantitaBirra))) {
			return null;
		}
		SortedMap<String, Ricetta> ricette = Ricettario.getIstanza().getRicette();
		if(ricette.isEmpty()) {
			Database.getIstanza().closeDB();
			Notifica.getIstanza().addError("Non ci sono ricette nel ricettario");
			return null;
		}
		SortedMap<String, Ingrediente> catalogo = CatalogoIngredienti.getIstanza().getIngredienti();
		if(catalogo.isEmpty()) {
			Database.getIstanza().closeDB();
			Notifica.getIstanza().addError("Non ci sono ingredienti nel catalogo");
			return null;
		}
		for (Ricetta r: ricette.values()) {
			valori.put(r.getId(), calcolaDifferenza(catalogo, r, Double.parseDouble(quantitaBirra)));
		}
		Ricetta risultato = ricette.get(trovaValoreMax(valori));
		if(risultato == null) {
			Notifica.getIstanza().addError("Nessuna ricetta producibile");
		}
		return risultato;
		
		
	}

	private static Double calcolaDifferenza(SortedMap<String, Ingrediente> catalogo, Ricetta ricetta, double quantitaBirra) {
		double differenza = 0;
		for (Ingrediente ingRicetta : ricetta.getIngredienti()) {
			Ingrediente ingCatalogo = getIngredienteByNomeCategoria(catalogo, ingRicetta.getNome(), ingRicetta.getCategoria());
			if(ingCatalogo == null) {
				return Double.MAX_VALUE;
			}
			double quantitaCatalogo = ingCatalogo.getQuantita();
			double quantitaRicetta = ingRicetta.getQuantita() * quantitaBirra;
			if(quantitaCatalogo - (quantitaRicetta)<0)
				return Double.MAX_VALUE;
			differenza = differenza +(quantitaCatalogo - quantitaRicetta);
		}
		return differenza;
	}
	
	private static String trovaValoreMax(TreeMap<String, Double> valori) {
		Double max = Collections.max(valori.values());
		String key = null;
		for(Entry<String, Double> entry : valori.entrySet()) {
			if(entry.getValue() < max) {
				key = entry.getKey();
				max = entry.getValue();
			}
		}
		if(max == Double.MAX_VALUE) {
			return null;
		}
		return key;
	}
	
	private static Ingrediente getIngredienteByNomeCategoria(SortedMap<String, Ingrediente> catalogo, String nome, String categoria) {
		for (Ingrediente ing : catalogo.values()) {
			if((ing.getNome().equals(nome)) && (ing.getCategoria().equals(categoria))) {
				return ing;
			}
		}
		return null;
	}
	
}
