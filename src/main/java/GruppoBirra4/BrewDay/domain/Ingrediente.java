package GruppoBirra4.BrewDay.domain;

import java.util.UUID;

public class Ingrediente {
	
	public enum Categoria {
		MALTO,
		LUPPOLO,
		LIEVITO,
		ZUCCHERO,
		ADDITIVO
	}
	
	private String id;
	private String nome;
	private Categoria categoria;
	private double quantitaDisponibile;
	
	
	public Ingrediente(String nome, Categoria categoria, double quantitaDisponibile) {
		this.id = UUID.randomUUID().toString(); 
		this.nome = nome;
		this.categoria = categoria;
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public  String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	public double getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	private void setQuantitaDisponibile(double quantitaDisponibile) {
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	private void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		if(quantitaDisponibile<0)
			throw new IllegalArgumentException();
		ingrediente.setQuantitaDisponibile(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		if((nuovoNome.isEmpty()) || (nuovoNome.matches("[ -]_*")))
			throw new IllegalArgumentException();
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, Categoria categoria) {
		ingrediente.setCategoria(categoria);
	}
	
}
