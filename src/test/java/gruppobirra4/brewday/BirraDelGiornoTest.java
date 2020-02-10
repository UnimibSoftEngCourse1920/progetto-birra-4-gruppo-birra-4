package gruppobirra4.brewday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import gruppobirra4.brewday.domain.BirraDelGiorno;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class BirraDelGiornoTest {

	
	@Test
	public void testCalcolaBirraDelGiorno() {
		CancellazioneDB.eliminaDB();
		
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		Ricettario r = Ricettario.getIstanza();
		
		Set<Ingrediente> ingredientiRicetta1 = new HashSet<>();
		Ingrediente ing1 = Ingrediente.creaIngrediente(null, "Pale", "MALTO", "3800");
		ingredientiRicetta1.add(ing1);
		Ingrediente ing2 = Ingrediente.creaIngrediente(null, "Crystal", "MALTO", "200");
		ingredientiRicetta1.add(ing2);
		Ingrediente ing3 = Ingrediente.creaIngrediente(null, "Columbus", "LUPPOLO", "45");
		ingredientiRicetta1.add(ing3);
		Ricetta ricetta1 = r.creaRicetta("American Pale Ale", "bla bla bla", ingredientiRicetta1, "17", "18");
		assertNotNull(ricetta1);
		Ricetta risultato = BirraDelGiorno.calcolaBirraDelGiorno("18");
		assertNull(risultato);
		
		
		Set<Ingrediente> ingredientiRicetta2 = new HashSet<>();
		Ingrediente ing4 = Ingrediente.creaIngrediente(null, "Pils", "MALTO", "2100");
		ingredientiRicetta2.add(ing4);
		Ingrediente ing5 = Ingrediente.creaIngrediente(null, "Wheat", "MALTO", "2100");
		ingredientiRicetta2.add(ing5);
		Ingrediente ing6 = Ingrediente.creaIngrediente(null, "Hattertauer", "LUPPOLO", "17");
		ingredientiRicetta2.add(ing6);
		Ricetta ricetta2 = r.creaRicetta("Hefe Weinzen", "blu blu blu", ingredientiRicetta2, "15", "18");
		assertNotNull(ricetta2);
		
		Set<Ingrediente> ingredientiRicetta3 = new HashSet<>();
		Ingrediente ing7 = Ingrediente.creaIngrediente(null, "Pils", "MALTO", "2900");
		ingredientiRicetta3.add(ing7);
		Ingrediente ing8 = Ingrediente.creaIngrediente(null, "Wheat", "MALTO", "3700");
		ingredientiRicetta3.add(ing8);
		Ingrediente ing9 = Ingrediente.creaIngrediente(null, "Hattertauer", "LUPPOLO", "51"); //più della quantita presente
		ingredientiRicetta3.add(ing9);
		Ricetta ricetta3 = r.creaRicetta("Mille bolle", "ble ble ble", ingredientiRicetta2, "13", "18");
		assertNotNull(ricetta3);
		
		Set<Ingrediente> ingredientiRicetta4 = new HashSet<>();
		Ingrediente ing10 = Ingrediente.creaIngrediente(null, "Pale", "MALTO", "3800");
		ingredientiRicetta1.add(ing10);
		Ingrediente ing11 = Ingrediente.creaIngrediente(null, "Crystal", "MALTO", "200");
		ingredientiRicetta1.add(ing11);
		Ingrediente ing12 = Ingrediente.creaIngrediente(null, "Astrubale", "LUPPOLO", "45"); //non presente in catalogo
		ingredientiRicetta1.add(ing12);
		Ricetta ricetta4 = r.creaRicetta("Zaraba", "blo blo blo", ingredientiRicetta1, "17", "18");
		assertNotNull(ricetta4);
		
		
		c.creaIngrediente("Pale", "MALTO", "5000");
		c.creaIngrediente("Crystal", "MALTO", "1000");
		c.creaIngrediente("Columbus", "LUPPOLO", "500");
		c.creaIngrediente("Pils", "MALTO", "3000");
		c.creaIngrediente("Wheat", "MALTO", "4500");
		c.creaIngrediente("Hattertauer", "LUPPOLO", "50");
		
		risultato = BirraDelGiorno.calcolaBirraDelGiorno("18");
		assertNotNull(risultato);
		assertEquals(ricetta1.getId(), risultato.getId());
		
	}

}
