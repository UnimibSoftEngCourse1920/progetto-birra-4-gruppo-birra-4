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
		ListaLotti l = ListaLotti.getIstanza();
		Ricettario r = Ricettario.getIstanza();
		CatalogoIngredienti c = CatalogoIngredienti.getIstanza();
		
		//Riempimento catalogo
		Ingrediente ing1Catalogo = c.creaIngrediente("i1", "Malto", "3000");
		Ingrediente ing2Catalogo = c.creaIngrediente("i2", "Luppolo", "700");
		
		//Creazione ricetta
		Set<Ingrediente> ingredienti = new HashSet<>();
		Ingrediente ing1Ricetta = Ingrediente.creaIngrediente(null, "i1", "Malto", "200");
		ingredienti.add(ing1Ricetta);
		Ingrediente ing2Ricetta = Ingrediente.creaIngrediente(null, "i2", "Luppolo", "50");
		ingredienti.add(ing2Ricetta);
		Ricetta ric1 = r.creaRicetta("Giovanni's beer", "cioè, si sta ribaltando la situazione", ingredienti, "30", "23");
		
		//Creazione lotto
		String idRicetta1 = ric1.getId();
		Lotto lotto1 = l.creaLotto(idRicetta1, "40"); //Quantita ingr e acqua necessari: i1: 348, i2: 87, acqua: 52
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
		
		
	
	}

}
