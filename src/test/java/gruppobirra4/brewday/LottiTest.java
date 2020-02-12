package gruppobirra4.brewday;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import gruppobirra4.brewday.domain.ingredienti.CatalogoIngredienti;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.domain.ricette.ListaLotti;
import gruppobirra4.brewday.domain.ricette.Lotto;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.domain.ricette.Ricettario;

public class LottiTest {

	@Test
	public void testCreaLotto() {
		CancellazioneDB.eliminaDB();
		
		ListaLotti l = ListaLotti.getIstanza();
		Ricettario r = Ricettario.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		
		//Riempimento catalogo
		Ingrediente ing1Catalogo = c.creaIngrediente("i1", "Malto", "3000");
		Ingrediente ing2Catalogo = c.creaIngrediente("i2", "Luppolo", "700");
		Ingrediente ing3Catalogo = c.creaIngrediente("i3", "Lievito", "10");
		
		//Creazione ricetta ric1
		Set<Ingrediente> ingredienti = new HashSet<>();
		Ingrediente ing1Ricetta = Ingrediente.creaIngrediente(null, "i1", "Malto", "200");
		ingredienti.add(ing1Ricetta);
		Ingrediente ing2Ricetta = Ingrediente.creaIngrediente(null, "i2", "Luppolo", "50");
		ingredienti.add(ing2Ricetta);
		Ricetta ric1 = r.creaRicetta("Giovanni's beer", "cioè, si sta ribaltando la situazione", ingredienti, "30", "23");
		
		//Creazione lotto1 
		Lotto lotto1 = l.creaLotto(ric1.getNome(), "40"); //Quantita ingr e acqua necessari: i1: 348, i2: 87, acqua: 52
		assertNotNull(lotto1);
		Collection<Lotto> listaLotti = l.visualizzaListaLotti();
		assertEquals(1, listaLotti.size());
		ing1Catalogo = c.getIngredienteById(ing1Catalogo.getId()); 
		ing2Catalogo = c.getIngredienteById(ing2Catalogo.getId());
		
		//Controllo aggiornamento catalogo
		Ingrediente ing1LottoIngrediente = lotto1.getRicetta().getIngredienteFromRicetta(ing1Ricetta.getId());
		Ingrediente ing2LottoIngrediente = lotto1.getRicetta().getIngredienteFromRicetta(ing2Ricetta.getId());
		assertEquals(3000-348, ing1Catalogo.getQuantita(), 0.01);
		assertEquals(700-87, ing2Catalogo.getQuantita(), 0.01);	
		
		//Creazione ricetta ric2
		ingredienti = new HashSet<>();
		Ingrediente ing3Ricetta = Ingrediente.creaIngrediente(null, "i3", "Lievito", "20");
		ingredienti.add(ing3Ricetta);
		Ricetta ric2 = r.creaRicetta("Amadeus's beer", "dirige l'orchestra il Maestro Beppe Vessichio!!", ingredienti, null, "23");
		
		//Creazione lotto2 - Ingredienti in catalogo non sufficienti
		Lotto lotto2 = l.creaLotto(ric2.getNome(), "40"); //Quantita ingr e acqua necessari: i3: 35
		assertNull(lotto2);
		assertEquals(1, listaLotti.size());
		
		//Creazione ricetta ric3
		ingredienti = new HashSet<>();
		Ingrediente ing4Ricetta = Ingrediente.creaIngrediente(null, "i4", "Zucchero", "8");
		ingredienti.add(ing4Ricetta);
		Ricetta ric3 = r.creaRicetta("Giangilberto's beer", "ciao", ingredienti, null, "23");
		
		//Creazione lotto3 - Ingredienti in catalogo non presenti
		Lotto lotto3 = l.creaLotto(ric3.getNome(), "40"); //Quantita ingr e acqua necessari: i4: 14
		assertNull(lotto3);
		assertEquals(1, listaLotti.size());				
	}

}