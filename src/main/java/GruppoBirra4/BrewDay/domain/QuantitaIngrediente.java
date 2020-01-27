package GruppoBirra4.BrewDay.domain;

public class QuantitaIngrediente {
	
	private double quantita;
	private Ingrediente ingrediente;
	
	public QuantitaIngrediente(double quantita, Ingrediente ingrediente) {
		setQuantita(quantita); //Solleva eccezione
		setIngrediente(ingrediente);
	}

	public double getQuantita() {
		return quantita;
	}

	private void setQuantita(double quantita) {
		if(quantita <= 0) {
			//Solleva eccezione
		}
		this.quantita = quantita;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	private void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

}
