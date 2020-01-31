package gruppobirra4.brewday;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import gruppobirra4.brewday.database.Database;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;

public class CatalogoTest { 

	@Test
	public void testCreaIngrediente() {
		File dbFile = new File("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db");
		dbFile.delete();
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
	
	/*@Test
	public void testRimuoviIngrediente() {
		File dbFile = new File("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db");
		dbFile.delete();
		
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		Ingrediente ingr = c.creaIngrediente("San Michele", "Luppolo", "500");
		c.rimuoviIngrediente(ingr.getId());
		assertEquals(0, c.getIngredienti().size());
	}
	
	@Test
	public void testModificaIngrediente() {
		File dbFile = new File("src\\test\\java\\gruppobirra4\\brewday\\DatabaseTest.db");
		dbFile.delete();
		
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		Ingrediente ingr = c.creaIngrediente("San Michele", "Luppolo", "500");
		Ingrediente ingrModificato = c.modificaIngrediente(ingr.getId(), "San Pippo", "ciao", "100");
		ingrModificato = c.getIngredienti().get(ingr.getId());
		assertEquals(ingrModificato, c.getIngredienti().get(ingr.getId()));
	}*/
	

}
