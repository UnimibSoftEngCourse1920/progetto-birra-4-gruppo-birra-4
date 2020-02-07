package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import static gruppobirra4.brewday.domain.InputUtente.*;


public class Lotto implements Serializable {
	
	private String id;
	//private String idRicetta;
	private Date data;
	private String noteGusto;
	private String noteProblemi;
	private String quantitaBirra;
	private Ricetta ricetta;
	
	public static final String CAMPO_QUANTITA_BIRRA = "Quantita di birra da produrre";
	
	protected Lotto(String id, Date data, String noteGusto, String noteProblemi, String quantitaBirra, Ricetta ricetta) {
		this.id = id;
		//this.idRicetta = idRicetta;
		this.data = data;
		this.noteGusto = noteGusto;
		this.noteProblemi = noteProblemi;
		this.quantitaBirra = quantitaBirra;
	}
	
	protected Lotto(String quantitaBirra, Ricetta ricetta) {
		this.id = UUID.randomUUID().toString();
		//this.idRicetta = idRicetta;
		this.data = new Date();
		this.noteGusto = "";
		this.noteProblemi = "";
		this.quantitaBirra = quantitaBirra;
		this.ricetta = ricetta;
	}
	
	public static Lotto creaLotto(String quantitaBirra, Ricetta ricetta) {
		if (!validation(quantitaBirra)) {
			return null;
		}
		ricetta = aggiornaQuantitaRicetta(quantitaBirra, ricetta);
		return new Lotto(quantitaBirra, ricetta);		
	}

	private static boolean validation(String quantitaBirra) {
		return validateQuantitaBirra(quantitaBirra);
	}

	private static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, CAMPO_QUANTITA_BIRRA) &&
				isNumber(quantitaBirra, CAMPO_QUANTITA_BIRRA) && 
				isPositive(quantitaBirra, CAMPO_QUANTITA_BIRRA);
	}
	
	private static Ricetta aggiornaQuantitaRicetta(String quantitaBirra, Ricetta ricetta) {
		ricetta.aggiornaQuantita(quantitaBirra);
		return ricetta;
	}

	public String getId() {
		return id;
	}
	
	/*public String getIdRicetta() {
		return idRicetta;
	}*/

	public Date getData() {
		return data;
	}

	public String getNoteGusto() {
		return noteGusto;
	}
	
	public String getNoteProblemi() {
		return noteProblemi;
	}
	
	public String getQuantitaBirra() {
		return quantitaBirra;
	}
	
	public Ricetta getRicetta() {
		return ricetta;
	}
	
	
	
	
	
	


}
