package gruppobirra4.brewday;

import static org.junit.Assert.*;

import org.junit.Test;

import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

public class CatalogoTest {

	@Test
	public void testCreaIngrediente() {
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		Ingrediente ingr = null;
		
		//Input corretto
		ingr = c.creaIngrediente("San Michele", "Luppolo", "500");
		assertNotNull(ingr);
		assertEquals(1, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo1   ", "Malto", "   500    ");
		assertNotNull(ingr);
		assertEquals(2, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo2", "Malto", "500.5985");
		assertNotNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		//Input sbagliato
		ingr = c.creaIngrediente("  ", "Luppolo", "500");
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente(null, "Luppolo", "500");
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo3", "Malto", "");
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo3", "Malto", null);
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo3", "Malto", "ciao");
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("Tipo3", "Malto", "-500.56");
		assertNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		//Ingrediente già presente
		ingr = c.creaIngrediente("San Michele", "Luppolo", "500");
		assertNotNull(ingr);
		assertEquals(3, c.getIngredienti().size());
		
		ingr = c.creaIngrediente("   San       Michele     ", "Luppolo", "   500  ");
		assertNotNull(ingr);
		assertEquals(3, c.getIngredienti().size());
	}

}
