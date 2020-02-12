package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

public class QuantitaListaSpesa {
	
	private Ingrediente ingrediente;
	private double quantitaDaAcquistare;
	
	//Crea un oggetto da restituire all'interfaccia contenente l'ingrediente da acquistare e la quantita' da acquistare
	public QuantitaListaSpesa(Ingrediente ingrediente, double quantitaDaAcquistare) {
		setIngrediente(ingrediente);
		setQuantitaDaAcquistare(quantitaDaAcquistare);
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	private void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public double getQuantitaDaAcquistare() {
		return quantitaDaAcquistare;
	}

	private void setQuantitaDaAcquistare(double quantitaDaAcquistare) {
		this.quantitaDaAcquistare = quantitaDaAcquistare;
	}

}