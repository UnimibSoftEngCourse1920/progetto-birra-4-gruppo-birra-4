package gruppobirra4.brewday.domain.ingredienti;

import java.io.Serializable;
import java.util.UUID;

import static gruppobirra4.brewday.domain.InputUtente.*;


public class Ingrediente implements Serializable {
	
	private String id;
	private String nome;
	private String categoria;
	private double quantita;
	
	

	protected Ingrediente(String nome, String categoria, String quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	

	public static Ingrediente creaIngrediente(String nome, String categoria, String quantita) {
		boolean valid = validation(nome, quantita);
		if (!valid)
			return null;
		else
			return new Ingrediente(nome, categoria, quantita);
		
	}
	
	private static boolean validation(String nome, String quantita) {
		return validateNome(nome) &
				validateQuantita(quantita);
	}
	
	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateQuantita(String quantita) {
		return !isStringaVuota(quantita, "Quantita") && isNumber(quantita, "Quantita") && isNotPositive(quantita, "Quantita");	
	}

	public  String getId() {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Double.doubleToLongBits(quantita) != Double.doubleToLongBits(other.quantita))
			return false;
		return true;
	}
	
	
	
	/*public void modificaIngrediente(Ingrediente ingrediente, double nuovaQuantita) {
		ingrediente.setQuantita(nuovaQuantita);
	}

	public void modificaIngrediente(Ingrediente ingrediente, String nuovoNome) {
		ingrediente.setNome(nuovoNome);
	}
	
	public void modificaIngrediente(Ingrediente ingrediente, String nuovaCategoria) {
		ingrediente.setCategoria(nuovaCategoria);
	}*/
	
}
