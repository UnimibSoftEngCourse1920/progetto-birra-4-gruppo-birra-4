package GruppoBirra4.BrewDay.domain;

import java.util.Set;
import java.util.TreeSet;

public class CatalogoIngredienti {
	private Set<Ingrediente> ingredienti = new TreeSet<>();
	private static CatalogoIngredienti istanza = null;
	
	private CatalogoIngredienti(Set<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti(ingredienti);	
		}
		return istanza;
	}

}