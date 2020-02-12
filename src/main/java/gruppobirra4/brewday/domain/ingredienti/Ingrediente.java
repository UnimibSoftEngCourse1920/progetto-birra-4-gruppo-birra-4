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
	
	public Ingrediente(String nome, String categoria, String quantita) {
		id = UUID.randomUUID().toString(); 
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}
	
	protected Ingrediente(String id, String nome, String categoria, String quantita) {
		this.id = id;
		setNome(nome);
		setCategoria(categoria);
		setQuantita(quantita);
	}

	//Crea un'ingrediente nel caso i parametri inseriti siano validi, il primo costruttore viene usato per la creazione di un nuovo ingrediente, il secondo per la modifica di uno esistente
	public static Ingrediente creaIngrediente(String id, String nome, String categoria, String quantita) {
		boolean valid = validation(nome, quantita);
		if (!valid)
			return null;
		if(id == null)
			return new Ingrediente(nome, categoria, quantita);
		else
			return new Ingrediente(id, nome, categoria, quantita);
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
		if ((categoria == null) && (other.categoria != null)) { //NOSONAR
				return false;
		} else if (!categoria.equals(other.categoria)) //NOSONAR
			return false;
		if ((nome == null) && (other.nome != null)) { //NOSONAR
				return false;
		} else if (!nome.equals(other.nome)) //NOSONAR
			return false;
		return true;
	}	
	
	public String getCategoria() {
		return categoria;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public double getQuantita() {
		return quantita;
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((categoria == null) ? 0 : categoria.hashCode());
		//result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((nome == null) ? 0 : nome.hashCode());
		/*long temp;
		temp = Double.doubleToLongBits(quantita);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));*/
		return result;
	}
	
	private void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	private void setNome(String nome) {
		String nomeLW = rimuoviWhiteSpaces(nome);
		this.nome = nomeLW;
	}

	public void setQuantita(String quantita) {
		double quantitaD = convertToNumber(quantita);
		this.quantita = quantitaD;
	}
	
	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	protected static boolean validateQuantita(String quantita) {
		return !isStringaVuota(quantita, CAMPO_QUANTITA) && 
				isNumber(quantita, CAMPO_QUANTITA) &&
				isPositive(quantita, CAMPO_QUANTITA);	
	}

	//Controlla che i valori inseriti dall'utente siano validi
	protected static boolean validation(String nome, String quantita) {
		return validateNome(nome) & //NOSONAR
				validateQuantita(quantita);
	}	

}