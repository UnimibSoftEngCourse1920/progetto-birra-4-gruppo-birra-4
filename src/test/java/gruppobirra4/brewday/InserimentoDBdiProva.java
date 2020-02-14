package gruppobirra4.brewday;

import java.util.HashSet;
import java.util.Set;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ingredienti.ListaSpesa;
import gruppobirra4.brewday.domain.ingredienti.QuantitaListaSpesa;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class InserimentoDBdiProva {

	private InserimentoDBdiProva() {
		super();
	}

	public static void main(String[] args) {
		CancellazioneDB.eliminaDB();
		Ricettario r = Ricettario.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		ListaSpesa s = ListaSpesa.getIstanza();
		
		//Inserimento ingredienti
		c.creaIngrediente("Pale", "Malto", "5000");
		c.creaIngrediente("Crystal", "Malto", "1000");
		c.creaIngrediente("Columbus", "Luppolo", "500");
		c.creaIngrediente("Pils", "Malto", "3000");
		c.creaIngrediente("Wheat", "Malto", "4500");
		c.creaIngrediente("Hattertauer", "Luppolo", "50");
		
		//Creazione ricette
		Set<Ingrediente> ingredientiRicetta1 = new HashSet<>();
		Ingrediente ing1 = Ingrediente.creaIngrediente(null, "Pale", "Malto", "3800");
		ingredientiRicetta1.add(ing1);
		Ingrediente ing2 = Ingrediente.creaIngrediente(null, "Crystal", "Malto", "200");
		ingredientiRicetta1.add(ing2);
		Ingrediente ing3 = Ingrediente.creaIngrediente(null, "Columbus", "Luppolo", "45");
		ingredientiRicetta1.add(ing3);
		Ricetta ricetta1 = r.creaRicetta("Giacomino's beer", "E così domani ti sposi? Si, ma niente di serio.", ingredientiRicetta1, "30", "23");
		
		Set<Ingrediente> ingredientiRicetta2 = new HashSet<>();
		Ingrediente ing4 = Ingrediente.creaIngrediente(null, "Pils", "Malto", "2100");
		ingredientiRicetta2.add(ing4);
		Ingrediente ing5 = Ingrediente.creaIngrediente(null, "Wheat", "Malto", "2100");
		ingredientiRicetta2.add(ing5);
		Ingrediente ing6 = Ingrediente.creaIngrediente(null, "Hattertauer", "Luppolo", "17");
		ingredientiRicetta2.add(ing6);
		Ricetta ricetta2 = r.creaRicetta("Morgan's beer", "Dov'è Bugo?", ingredientiRicetta2, "30", "23");
		
		Set<Ingrediente> ingredientiRicetta3 = new HashSet<>();
		Ingrediente ing7 = Ingrediente.creaIngrediente(null, "Pils", "Malto", "2900");
		ingredientiRicetta3.add(ing7);
		Ingrediente ing8 = Ingrediente.creaIngrediente(null, "Wheat", "Malto", "3700");
		ingredientiRicetta3.add(ing8);
		Ingrediente ing9 = Ingrediente.creaIngrediente(null, "Hattertauer", "Luppolo", "51"); //più della quantita presente
		ingredientiRicetta3.add(ing9);
		Ricetta ricetta3 = r.creaRicetta("Amadeus'beer", "Dirige l'orchestra il maestro Beppe Vessicchio!", ingredientiRicetta3, "30", "23"); 
		
		//Aggiunta ingredienti alla lista della spesa
		QuantitaListaSpesa qt1 = s.aggiungiIngrediente("San Michele", "Luppolo", "500");
		QuantitaListaSpesa qt2 = s.aggiungiIngrediente("Pale", "Malto", "100");
		QuantitaListaSpesa qt3 = s.aggiungiIngrediente("Wheat", "Malto", "200");

	}

}
