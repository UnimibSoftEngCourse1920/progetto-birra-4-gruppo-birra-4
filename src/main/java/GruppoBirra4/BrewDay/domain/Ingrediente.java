package GruppoBirra4.BrewDay.domain;

public class Ingrediente {
	
	public enum Categoria {
		MALTO,
		LUPPOLO,
		LIEVITO,
		ZUCCHERO,
		ADDITIVO
	}
	
	private String nome;
	private Categoria categoria;
	private double quantitaDisponibile;
	
	
	public Ingrediente(String nome, Categoria categoria, double quantitaDisponibile) {
		this.nome = nome;
		this.categoria = categoria;
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if((nome.isEmpty()) || (nome.matches("[ -]_*")))
			throw new IllegalArgumentException();
		this.nome = nome;
	}

	public double getQuantitaDisponibile() {
		return quantitaDisponibile;
	}

	public void setQuantitaDisponibile(double quantitaDisponibile) {
		if(quantitaDisponibile<0)
			throw new IllegalArgumentException();
		this.quantitaDisponibile = quantitaDisponibile;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
