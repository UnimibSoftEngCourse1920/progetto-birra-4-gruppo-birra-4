package gruppobirra4.brewday.domain.ricette; //NOSONAR

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import static gruppobirra4.brewday.domain.InputUtente.*;


public class Lotto implements Serializable {
	
	private String id;
	//private String idRicetta;
	private String data;
	private String noteGusto;
	private String noteProblemi;
	private Ricetta ricetta;
	
	public static final String CAMPO_QUANTITA_BIRRA = "Quantita di birra da produrre";
	
	protected Lotto(String id, String data, String noteGusto, String noteProblemi, Ricetta ricetta) {
		this.id = id;
		//this.idRicetta = idRicetta;
		this.data = data;
		this.noteGusto = noteGusto;
		this.noteProblemi = noteProblemi;
		this.ricetta = ricetta;
	}
	
	private Lotto(Ricetta ricetta) {
		this.id = UUID.randomUUID().toString();
		//this.idRicetta = idRicetta;
		setDate();
		this.noteGusto = "";
		this.noteProblemi = "";
		this.ricetta = ricetta;
	}
	
	public static Lotto creaLotto(String quantitaBirra, Ricetta ricetta) {
		if (!validation(quantitaBirra)) {
			return null;
		}
		ricetta = aggiornaQuantitaRicetta(quantitaBirra, ricetta);
		return new Lotto(ricetta);		
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

	public String getData() {
		return data;
	}

	public String getNomeRicetta() {
		return ricetta.getNome();
	}
	
	public String getNoteGusto() {
		return noteGusto;
	}
	
	public String getNoteProblemi() {
		return noteProblemi;
	}
	
	public Ricetta getRicetta() {
		return ricetta;
	}
	
	private void setDate() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		Date today = calendar.getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		this.data = dateFormat.format(today);
	}
	
	
	
	
	
	


}
