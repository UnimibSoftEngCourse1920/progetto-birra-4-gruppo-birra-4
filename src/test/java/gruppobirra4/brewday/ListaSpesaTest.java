package gruppobirra4.brewday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ingredienti.ListaSpesa;
import gruppobirra4.brewday.domain.ingredienti.QuantitaListaSpesa;

public class ListaSpesaTest {
	
	@Test
	public void testAggiungiIngrediente() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		QuantitaListaSpesa qt = null;
		Ingrediente ing = null;
		
		//Input corretto, in catalogo
		ing = c.creaIngrediente("San Michele", "Luppolo", "500");
		qt = l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		assertNotNull(qt);
		assertNotNull(qt.getIngrediente());
		assertEquals(1, l.visualizzaListaSpesa().size());
		assertEquals(1, c.getIngredienti().size());
		//assertEquals(ing.getId(), qt.getIngrediente().getId());

		
		//Input corretto, non in catalogo
		qt = l.aggiungiIngrediente("San Giorgio", "Luppolo", "500");
		assertNotNull(qt);
		assertNotNull(qt.getIngrediente());
		assertEquals(2, l.visualizzaListaSpesa().size());
		assertEquals(2, c.getIngredienti().size());
	}
	
	@Test
	public void testRimuoviIngrediente() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		QuantitaListaSpesa qt = null;
		
		//c.creaIngrediente("San Michele", "Luppolo", "500");
		qt = l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		assertNotNull(qt);
		assertEquals(1, l.visualizzaListaSpesa().size());
		assertEquals(1, c.getIngredienti().size());
		
		l.rimuoviIngrediente(qt.getIngrediente().getId());
		assertEquals(1, c.getIngredienti().size());
		assertEquals(0, l.visualizzaListaSpesa().size());
	}
	
	@Test
	public void testSvuotaLista() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		
		l.aggiungiIngrediente("San Giorgio", "Luppolo", "500");
		l.aggiungiIngrediente("Zucchero di Canna", "Zucchero", "500");
		assertEquals(2, l.visualizzaListaSpesa().size());
		assertEquals(2, c.getIngredienti().size());
		
		l.svuotaLista();
		assertEquals(0, l.visualizzaListaSpesa().size());
		assertEquals(2, c.getIngredienti().size());
	}
	 
	@Test
	public void testAcquistaIngrediente() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		
		c.creaIngrediente("San Michele", "Luppolo", "0"); //problema
		QuantitaListaSpesa qt = l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		assertEquals(1, l.visualizzaListaSpesa().size());
		assertEquals(1, c.getIngredienti().size());
		
		Ingrediente ing = qt.getIngrediente();
		assertNotNull(ing);
		
		l.acquistaIngrediente(qt.getIngrediente().getId());
		assertEquals(0, l.visualizzaListaSpesa().size());
		assertEquals(1, c.getIngredienti().size());
	}

	
	@Test
	public void testUnivocita() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		
		QuantitaListaSpesa qt = l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		String idLista = qt.getIngrediente().getId();
		Ingrediente ing = c.getIngredienteById(idLista);
		assertEquals(idLista, ing.getId());
		l.svuotaLista();	
	}
	
	@Test
	public void testAcquistaTutto() {
		CancellazioneDB.eliminaDB();
		
		ListaSpesa l = ListaSpesa.getIstanza();
		
		l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		l.aggiungiIngrediente("San Giorgio", "Luppolo", "500");
		l.aggiungiIngrediente("Zucchero di Canna", "Zucchero", "500");
		assertEquals(3, l.visualizzaListaSpesa().size());
		
		l.acquistaTutto();
		assertEquals(0, l.visualizzaListaSpesa().size());
	}

}