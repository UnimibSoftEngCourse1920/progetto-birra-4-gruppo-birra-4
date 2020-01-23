package GruppoBirra4.BrewDay.domain;

import java.util.Set;
import java.util.TreeSet;

public class CatalogoIngredienti {
	private Set<Ingrediente> ingredienti = new TreeSet<Ingrediente>();
	private CatalogoIngredienti istanza;
	
	private CatalogoIngredienti(Set<Ingrediente> ingredienti) {
		super();
		this.ingredienti = ingredienti;
	}
	
	public CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti(ingredienti);	
		}
		return istanza;
	}

}
