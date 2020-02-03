package gruppobirra4.brewday;

import java.util.Set;

import org.junit.Test;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class RicettarioTest {

	@Test
	public void testCreaIngrediente() {
		//File dbFile = new File("src\\main\\java\\gruppobirra4\\brewday\\database\\Database.db");
		//dbFile.delete();
		Ricettario r = Ricettario.getIstanza();
		Ricetta ric = null;
		
		Set<Ingrediente> ingredienti;
		Ingrediente ing1 = creaIngrediente("Zucchero di canna", "ZUCCHERO", "250");
	}

}
