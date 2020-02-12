package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import gruppobirra4.brewday.domain.DecimalUtils;
import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;
import static gruppobirra4.brewday.domain.InputUtente.*;

public class Ricetta implements Serializable {
	
	private String id; 
	private String nome;
	private String descrizione;
	private Set<Ingrediente> ingredienti;
	private double quantitaAcqua;
	private double quantitaBirra;
	
	private Ricetta(String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		this.id = UUID.randomUUID().toString();
		setNome(nome);
		setDescrizione(descrizione); 
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
		setIngredienti(ingredienti);
	}
	
	protected Ricetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		this.id = id;
		setNome(nome);
		setDescrizione(descrizione); 
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
		setIngredienti(ingredienti);
	}	
	
	public void aggiornaQuantita(String quantitaBirra) {
		setQuantitaBirra(quantitaBirra);
		convertiRicettaInValoreNormale();
	}
	
	//Crea la ricetta e converte le quantita degli ingredienti in valore assoluto
	protected static Ricetta creaRicetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		boolean valid = validation(nome, ingredienti, quantitaAcqua, quantitaBirra);
		if (!valid) {
			return null;
		}
		if(id == null)
			return new Ricetta(nome, descrizione, convertiQuantitaIngrInValoreAssoluto(ingredienti, quantitaBirra), 
								convertiQuantitaAcquaInValoreAssoluto(quantitaAcqua, quantitaBirra), quantitaBirra);
		else
			return new Ricetta(id, nome, descrizione, convertiQuantitaIngrInValoreAssoluto(ingredienti, quantitaBirra),
								convertiQuantitaAcquaInValoreAssoluto(quantitaAcqua, quantitaBirra), quantitaBirra);
	}
	
	//Controlla che i valori inseriti dall'utente siano validi
	protected static boolean validation(String nome, Set<Ingrediente> ingredienti, String quantitaAcqua, String quantitaBirra) {
		return validateNome(nome) & //NOSONAR
				validateIngredienti(ingredienti) & //NOSONAR
				validateQuantitaAcqua(quantitaAcqua) & //NOSONAR
				validateQuantitaBirra(quantitaBirra); //&&
				//validateQuantita(quantitaBirra, quantitaAcqua);
	}

	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}
	
	private static boolean validateIngredienti(Set<Ingrediente> ingredienti) {
		if (ingredienti.isEmpty()) {
			Notifica.getIstanza().addError("Inserire degli ingredienti");
			return false;
		}
		return true;
	}

	private static boolean validateQuantitaAcqua(String quantitaAcqua) {
		if (quantitaAcqua == null || quantitaAcqua.isEmpty()) {
			return true;
		}		
		return isNumber(quantitaAcqua, "Quantita' acqua") 
				&& isPositive(quantitaAcqua, "Quantita' acqua");
	}
	
	public static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, "Quantita birra")
				&& isNumber(quantitaBirra, "Quantita' birra") 
				&& isPositive(quantitaBirra, "Quantita' birra");
	}
	
	//Converte le quantita degli ingredienti in valore assoluto (grammi/litri)
	private static Set<Ingrediente> convertiQuantitaIngrInValoreAssoluto(Set<Ingrediente> ingredienti, String quantitaBirra) {
		for(Ingrediente ingr: ingredienti) {
			convertiQuantitaInValoreAssoluto(ingr, convertToNumber(quantitaBirra));
		}
		return ingredienti;
	}
	
	
	private static void convertiQuantitaInValoreAssoluto(Ingrediente ingr, double quantitaBirra) {
		ingr.setQuantita(Double.toString(ingr.getQuantita()/quantitaBirra));
	}
	
	public static String convertiQuantitaAcquaInValoreAssoluto(String quantitaAcqua, String quantitaBirra) {
		if (quantitaAcqua == null || quantitaAcqua.isEmpty()) {
			return null;
		}
		double qAcqua = convertToNumber(quantitaAcqua);
		double qBirra = convertToNumber(quantitaBirra);
		return (Double.toString(qAcqua / qBirra));
	}
	
	/*public Ricetta convertiRicettaInValoreAssoluto() {
		for(Ingrediente ingr: ingredienti) {
			convertiQuantitaIngrInValoreAssoluto(ingr);
		}
		return this;
	}
	
	
	private void convertiQuantitaIngrInValoreAssoluto(Ingrediente ingr) {
		ingr.setQuantita(Double.toString(ingr.getQuantita()/quantitaBirra));
	}*/
	
	//Converte le quantita degli ingredienti in valore "normale"
	public void convertiRicettaInValoreNormale() {
		for (Ingrediente ingr: ingredienti) {
			convertiQuantitaInValoreNormale(ingr);
		}
		if (Double.doubleToLongBits(quantitaAcqua) != Double.doubleToLongBits(0.0)) {
			quantitaAcqua = DecimalUtils.round(quantitaAcqua * quantitaBirra, 1);
		}
	}
	
	private void convertiQuantitaInValoreNormale(Ingrediente ingr) {
		double quantitaIngr = Math.round(ingr.getQuantita() * quantitaBirra);
		ingr.setQuantita(Double.toString(quantitaIngr));		
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
	
	public String getDescrizione() {
		return descrizione;
	}

	protected void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	private void setIngredienti(Set<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public double getQuantitaAcqua() {
		return quantitaAcqua;
	}

	private void setQuantitaAcqua(String quantitaAcqua) {
		if (quantitaAcqua == null || quantitaAcqua.isEmpty()) {
			this.quantitaAcqua = 0;
			return;
		}
		double quantitaA = convertToNumber(quantitaAcqua);
		this.quantitaAcqua = quantitaA;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	private void setQuantitaBirra(String quantitaBirra) {
		double quantitaB = convertToNumber(quantitaBirra);
		this.quantitaBirra = quantitaB;
	}
	
	
	/*private boolean checkIngredienti(String nome, String categoria) {
		if (ingredienti.isEmpty()) {
			Notifica.getIstanza().addError("Inserire degli ingredienti");
			return false;
		}
		for (Ingrediente i : ingredienti) {
			if((i.getNome().equals(nome)) && (i.getCategoria().equals(categoria)))
				return true;
		}
		Notifica.getIstanza().addError("Sono presenti degli ingredienti con lo stesso nome e categoria");
		return false;
	}*/
	
	public Ingrediente getIngredienteFromRicetta(String idIngrediente) {
		for (Ingrediente ingr: ingredienti) {
			if (ingr.getId().equals(idIngrediente)) {
				return ingr;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((ingredienti == null) ? 0 : ingredienti.hashCode());
		result = PRIME * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantitaAcqua);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quantitaBirra);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) { //NOSONAR
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ricetta other = (Ricetta) obj;
		if ((descrizione == null) && (other.descrizione != null)) { //NOSONAR
				return false;
		} else if (!descrizione.equals(other.descrizione)) //NOSONAR
			return false;
		if ((id == null) && (other.id != null)){
				return false;
		} else if (!id.equals(other.id)) //NOSONAR
			return false;
		if ((ingredienti == null) && (other.ingredienti != null)) { //NOSONAR
				return false;
		} else if (!ingredienti.equals(other.ingredienti)) //NOSONAR
			return false;
		if ((nome == null) && (other.nome != null)) { //NOSONAR
				return false;
		} else if (!nome.equals(other.nome)) //NOSONAR
			return false;
		return (Double.doubleToLongBits(quantitaAcqua) == Double.doubleToLongBits(other.quantitaAcqua))
		&& (Double.doubleToLongBits(quantitaBirra) == Double.doubleToLongBits(other.quantitaBirra));
	}

}