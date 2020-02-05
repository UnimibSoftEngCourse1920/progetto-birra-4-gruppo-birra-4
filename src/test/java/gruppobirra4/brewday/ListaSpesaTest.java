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
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		QuantitaListaSpesa qt = null;
		
		//Input corretto, non in catalogo
		c.creaIngrediente("San Michele", "Luppolo", "500");
		qt = l.aggiungiIngrediente("San Michele", "Luppolo", "500");
		assertNotNull(qt);
		assertEquals(1, l.visualizzaListaSpesa().size());
		assertEquals(1, c.getIngredienti().size());

		
		//Input corretto, non in catalogo
		qt = l.aggiungiIngrediente("San Giorgio", "Luppolo", "500");
		assertNotNull(qt);
		assertEquals(2, l.visualizzaListaSpesa().size());
		assertEquals(2, c.getIngredienti().size());
	}
	
	@Test
	public void testRimuoviIngrediente() {
		ListaSpesa l = ListaSpesa.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		QuantitaListaSpesa qt = null;
		
		c.creaIngrediente("San Michele", "Luppolo", "500");
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
	
}
