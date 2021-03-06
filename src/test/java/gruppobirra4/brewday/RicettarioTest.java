package gruppobirra4.brewday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import org.junit.Test;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class RicettarioTest {
	@Test
	public void testCreaRicetta() {
		CancellazioneDB.eliminaDB();
		
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

		ric = r.creaRicetta("Aldo's Beer", "Non posso nè scendere nè salire", ingredienti, null, "23");
		assertNotNull(ric);
		assertEquals(2, r.getRicette().size());
		
		//Verifica conversione in valore assoluto (quantita ingr e quantita acqua)
		Ingrediente ingRic1 = ric.getIngredienteFromRicetta(ing1.getId()); //ingrediente in valore normale
		SortedMap<String, Ricetta> ricettario = r.getRicette();
		Ricetta ricDaRicettario = ricettario.get(ric.getId()); //Preleva la ricetta ric dal ricettario
		Ingrediente ingRicdaRicettario = ricDaRicettario.getIngredienteFromRicetta(ing1.getId());  //ingr in val assoluto
		assertEquals(ingRic1.getQuantita()/ric.getQuantitaBirra(), ingRicdaRicettario.getQuantita(), 0.001);
		assertEquals(ric.getQuantitaAcqua()/ric.getQuantitaBirra(), ricDaRicettario.getQuantitaAcqua(), 0.01);
		
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
		CancellazioneDB.eliminaDB();
		
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
		CancellazioneDB.eliminaDB();
		
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