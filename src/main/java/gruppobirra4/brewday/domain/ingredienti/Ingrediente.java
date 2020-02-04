package gruppobirra4.brewday.domain.ingredienti; //NOSONAR

import java.io.Serializable;
import java.util.UUID;

import static gruppobirra4.brewday.domain.InputUtente.*;


public class Ingrediente implements Serializable {
	
	private String id;
	private String nome;
	private String categoria;
	private double quantita;
	
	private static final String CAMPO_QUANTITA = "Quantita";
	

	private Ingrediente(String nome, String categoria, String quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	
	public Ingrediente(String id, String nome, String categoria, String quantita) {
		this.id = id;
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}

	public static Ingrediente creaIngrediente(String id, String nome, String categoria, String quantita) {
		boolean valid = validation(nome, quantita);
		if (!valid)
			return null;
		if(id == null)
			return new Ingrediente(nome, categoria, quantita);
		else
			return new Ingrediente(id, nome, categoria, quantita);
	}
	
	
	protected static boolean validation(String nome, String quantita) {
		return validateNome(nome) & //NOSONAR
				validateQuantita(quantita);
	}
	
	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateQuantita(String quantita) {
		return !isStringaVuota(quantita, CAMPO_QUANTITA) && 
				isNumber(quantita, CAMPO_QUANTITA) &&
				isNotPositive(quantita, CAMPO_QUANTITA);	
	}

	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	private void setNome(String nome) {
		String nomeLW = rimuoviWhiteSpaces(nome);
		this.nome = nomeLW;
	}
	
	public String getCategoria() {
		return categoria;
	}

	private void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getQuantita() {
		return quantita;
	}

	private void setQuantita(String quantita) {
		double quantitaD = convertToNumber(quantita);
		this.quantita = quantitaD;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantita);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Ingrediente other = (Ingrediente) obj;
		if ((categoria == null) && (other.categoria != null)) {
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if ((id == null) && (other.id != null)) {
				return false;
		} else if (!id.equals(other.id))
			return false;
		if ((nome == null) && (other.nome != null)) {
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return (Double.doubleToLongBits(quantita) == Double.doubleToLongBits(other.quantita));
	}
	
}
