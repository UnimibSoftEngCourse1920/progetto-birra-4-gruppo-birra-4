package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

public class QuantitaListaSpesa {
	
	private Ingrediente ingrediente;
	private double quantitaDaAcquistare;
	
	public QuantitaListaSpesa(Ingrediente ingrediente, double quantitaDaAcquistare) {
		super();
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
	
	public String getIdIngrediente() {
		return ingrediente.getId();
	}
}
