package gruppobirra4.brewday;

import org.mapdb.DB;
import org.mapdb.HTreeMap;

import gruppobirra4.brewday.application.database.Database;
import gruppobirra4.brewday.application.gestori.GestoreIngredienti;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.application.database.Database;

public class CatalogoIngredientiTest {
	
	public static void main(String[] args) {
		GestoreIngredienti.getIstanza().creaIngrediente("Zucchero di e","ZUCCHERO", 17);
		CatalogoIngredienti.getIstanza().ingredienti = (HTreeMap<String, Ingrediente>) Database.getIstanza().getDb()
				.hashMap("CatalogoIngredienti").createOrOpen();
		System.out.print(CatalogoIngredienti.getIstanza().ingredienti.getSize());
		CatalogoIngredienti.getIstanza().ingredienti.close();
		
		
	}
}
