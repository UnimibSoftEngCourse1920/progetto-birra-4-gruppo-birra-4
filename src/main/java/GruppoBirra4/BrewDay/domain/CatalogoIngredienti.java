package GruppoBirra4.BrewDay.domain;

import java.util.Set;
import java.util.TreeSet;

public class CatalogoIngredienti {
	private Set<Ingrediente> ingredienti;
	private static CatalogoIngredienti istanza;
	
	private CatalogoIngredienti() {
		this.ingredienti = new TreeSet<>();
	}
	
	public static synchronized CatalogoIngredienti getIstanza() {
		if (istanza == null){
			istanza = new CatalogoIngredienti();	
		}
		return istanza;
	}

}
