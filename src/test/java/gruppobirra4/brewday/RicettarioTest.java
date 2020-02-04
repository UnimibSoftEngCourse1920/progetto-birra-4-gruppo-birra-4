package gruppobirra4.brewday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;
import kotlin.collections.EmptySet;

public class RicettarioTest {
	@Test
	public void testCreaRicetta() {
		Ricettario r = Ricettario.getIstanza();
		Ricetta ric = null;
		
		Set<Ingrediente> ingredienti = new HashSet<>();
		Ingrediente ing1 = Ingrediente.creaIngrediente(null, "Pilsner Malz", "MALTO", "2200");
		ingredienti.add(ing1);
		Ingrediente ing2 = Ingrediente.creaIngrediente(null, "Munich Crisp", "MALTO", "2000");
		ingredienti.add(ing2);
		assertNotEquals(0, ingredienti.size());
		ric = r.creaRicetta("Giacomino's Beer", "E cosi' domani ti sposi", ingredienti, "5", "23");
		assertNotNull(ric);
		assertEquals(1, r.getRicette().size());
		
		ric = r.creaRicetta("Aldo's Beer", "Mariiiiiia", ingredienti, null, "23");
		assertNotNull(ric);
		assertEquals(2, r.getRicette().size());
		
		//Ricetta già inserita
		ric = r.creaRicetta("Giacomino's Beer", "Si', ma nulla di serio", ingredienti,"7", "11");
		assertNull(ric);
		assertEquals(2, r.getRicette().size());
		
		//Input sbagliato
		ric = r.creaRicetta("", "E Giacomino si sposa", ingredienti,"13", "11");
		assertNull(ric);
		assertEquals(2, r.getRicette().size());
		
		ric = r.creaRicetta("Duff Beer", "", ingredienti, "-13", "-23");
		assertNull(ric);
		assertEquals(2, r.getRicette().size());
	}
	
	@Test
	public void testModificaRicetta() {
		Ricettario r = Ricettario.getIstanza();
		Ricetta ric = null;
		
		Set<Ingrediente> ingredienti = new HashSet<>();
		Ingrediente ing1 = Ingrediente.creaIngrediente(null, "Pilsner Malz", "MALTO", "2200");
		ingredienti.add(ing1);
		Ingrediente ing2 = Ingrediente.creaIngrediente(null, "Munich Crisp", "MALTO", "2000");
		ingredienti.add(ing2);
		assertNotEquals(0, ingredienti.size());
		ric = r.creaRicetta("Pdor", "Colui che era colui che è, e colui che sempre sara'", ingredienti, "5", "23");
		assertNotNull(ric);
		
		//Modifica corretta
		Ricetta ricettaModificata = r.modificaRicetta(ric.getId(), ric.getNome(), "Ciucia chi e ciucia la", ric.getIngredienti(), "13", "23");
		assertNotNull(ricettaModificata);
		Ricetta ricettaNellaLista = r.getRicette().get(ric.getId());
		assertNotNull(ricettaNellaLista);
		assertEquals(ricettaModificata, ricettaNellaLista);
		
		//Modifica scorretta
		ric = ricettaModificata;
		ricettaModificata = r.modificaRicetta(ric.getId(), ric.getNome(), ric.getDescrizione(), ric.getIngredienti(), "33", null);
		assertNull(ricettaModificata);
	}
	
	@Test
	public void testRimuoviRicetta() {
		Ricettario r = Ricettario.getIstanza();
		Ricetta ric = null;
		
		Set<Ingrediente> ingredienti = new HashSet<>();
		Ingrediente ing1 = Ingrediente.creaIngrediente(null, "Pilsner Malz", "MALTO", "2200");
		ingredienti.add(ing1);
		Ingrediente ing2 = Ingrediente.creaIngrediente(null, "Munich Crisp", "MALTO", "2000");
		ingredienti.add(ing2);
		assertNotEquals(0, ingredienti.size());
		ric = r.creaRicetta("Giacomino's Beer", "E cosi' domani ti sposi", ingredienti, "5", "23");
		assertNotNull(ric);
		assertEquals(1, r.getRicette().size());
		r.rimuoviRicetta(ric.getId());
		assertEquals(0, r.getRicette().size());
	}

}
