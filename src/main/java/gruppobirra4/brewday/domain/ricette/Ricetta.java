	package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import org.mapdb.Serializer;

import gruppobirra4.brewday.domain.ingredienti.Ingrediente;
import gruppobirra4.brewday.errori.Notifica;
import static gruppobirra4.brewday.domain.InputUtente.*;

public class Ricetta implements Serializable{
	
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
		setIngredienti(ingredienti);
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
	}
	
	protected Ricetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
			String quantitaAcqua, String quantitaBirra) {
		this.id = id;
		setNome(nome);
		setDescrizione(descrizione); 
		setIngredienti(ingredienti);
		setQuantitaAcqua(quantitaAcqua);
		setQuantitaBirra(quantitaBirra); 
	}	
	
	protected static Ricetta creaRicetta(String id, String nome, String descrizione, Set<Ingrediente> ingredienti, 
					String quantitaAcqua, String quantitaBirra) {
		boolean valid = validation(nome, descrizione, quantitaAcqua, quantitaBirra);
		if (!valid) {
			return null;
		}
		if(id == null)
			return new Ricetta(nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
		else
			return new Ricetta(id, nome, descrizione, ingredienti, quantitaAcqua, quantitaBirra);
	}
	
	protected static boolean validation(String nome, String descrizione, String quantitaAcqua, 
										String quantitaBirra) {
		return validateNome(nome) & //NOSONAR
				validateQuantitaAcqua(quantitaAcqua) & //NOSONAR
				validateQuantitaBirra(quantitaBirra) &&
				validateQuantita(quantitaBirra, quantitaAcqua);
	}

	private static boolean validateNome(String nome) {
		return !isStringaVuota(nome, "Nome");
	}

	private static boolean validateQuantitaAcqua(String quantitaAcqua) {
		return !isStringaVuota(quantitaAcqua, "Quantita acqua")
				&& isNumber(quantitaAcqua, "Quantita' acqua") 
				&& isNotPositive(quantitaAcqua, "Quantita' acqua");
	}
	
	private static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, "Quantita birra")
				&& isNumber(quantitaBirra, "Quantita' birra") 
				&& isNotPositive(quantitaBirra, "Quantita' birra");
	}
	
	private static boolean validateQuantita(String quantitaBirra, String quantitaAcqua) {
		if (convertToNumber(quantitaAcqua) >= convertToNumber(quantitaBirra)) {
			Notifica.getIstanza().addError("La quantità di acqua inserita deve essere maggiore della quantita di birra");
			return false;
		}
		return true;
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

	private void setDescrizione(String descrizione) {
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
	
	private boolean checkIngredienti(String nome, String categoria) {
		if (ingredienti.isEmpty()) {
			return false;
		}
		for (Ingrediente i : ingredienti) {
			if((i.getNome().equals(nome)) && (i.getCategoria().equals(categoria)))
				return true;
		}
		return false;
	}

	protected boolean aggiungiIngrediente(String nomeIng, String categoriaIng, String quantitaIng) {
		Ingrediente nuovoIngrediente = Ingrediente.creaIngrediente(null, nomeIng, categoriaIng, quantitaIng);
		if(nuovoIngrediente == null) {
			return false;
		}
		if(!(checkIngredienti(nuovoIngrediente.getNome(), nuovoIngrediente.getCategoria()))) {
			ingredienti.add(nuovoIngrediente);
			return true;
		}
		Notifica.getIstanza().addError("L'ingrediente è già presente nella ricetta");
		return false;
	}
	
	private Ingrediente getIngredienteById(String id) {
		for (Ingrediente i : ingredienti) {
			if((i.getId().equals(id)))
				return i;
		}
		return null;
	}

	protected boolean modificaIngrediente(String idIng, String nomeIng, String categoriaIng, String quantitaIng) {
		Ingrediente nuovoIngrediente = Ingrediente.creaIngrediente(idIng, nomeIng, categoriaIng, quantitaIng);
		if(nuovoIngrediente == null)
			return false;
		Ingrediente vecchioIngrediente = getIngredienteById(idIng);
		ingredienti.remove(vecchioIngrediente);
		ingredienti.add(nuovoIngrediente);
		return true;
	}

	public boolean rimuoviIngrediente(String idIng) {
		Ingrediente ingrediente = getIngredienteById(idIng);
		ingredienti.remove(ingrediente);
		return true;
	}
	
}
