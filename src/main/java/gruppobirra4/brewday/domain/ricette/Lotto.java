package gruppobirra4.brewday.domain.ricette; //NOSONAR

import static gruppobirra4.brewday.foundation.utility.InputUtente.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import gruppobirra4.brewday.errori.Notifica;


public class Lotto implements Serializable {
	
	private String id;
	private String data;
	private String noteGusto;
	private String noteProblemi;
	private Ricetta ricetta;
	
	public static final String CAMPO_QUANTITA_BIRRA = "Quantita di birra da produrre";
	
	protected Lotto(String id, String data, String noteGusto, String noteProblemi, Ricetta ricetta) {
		this.id = id;
		this.data = data;
		this.noteGusto = noteGusto;
		this.noteProblemi = noteProblemi;
		this.ricetta = ricetta;
	}
	
	private Lotto(Ricetta ricetta) {
		this.id = UUID.randomUUID().toString();
		setDate();
		this.noteGusto = "";
		this.noteProblemi = "";
		this.ricetta = ricetta;
	}
	
	public static Lotto creaLotto(String quantitaBirra, Ricetta ricetta) {
		if (!validation(quantitaBirra)) {
			return null;
		}
		Ricetta ricettaAggiornata = aggiornaQuantitaRicetta(quantitaBirra, ricetta);
		return new Lotto(ricettaAggiornata);		
	}
	
	//Imposta la quantita di birra da produrre nella ricetta, modificando di conseguenza i quantitivi di ingredienti da utilizzare per la produzione
	private static Ricetta aggiornaQuantitaRicetta(String quantitaBirra, Ricetta ricetta) {
		ricetta.aggiornaQuantita(quantitaBirra);
		return ricetta;
	}

	public String getId() {
		return id;
	}

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
	
	//Modifica le note del lotto
	public void modificaNote(String noteGusto, String noteProblemi) {
		this.noteGusto = noteGusto;
		this.noteProblemi = noteProblemi;		
	}
	
	private void setDate() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY);
		Date today = calendar.getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
		this.data = dateFormat.format(today);
	}	
	
	private static boolean validateQuantitaBirra(String quantitaBirra) {
		boolean valid = !isStringaVuota(quantitaBirra, CAMPO_QUANTITA_BIRRA) &&
				isNumber(quantitaBirra, CAMPO_QUANTITA_BIRRA) && 
				isPositive(quantitaBirra, CAMPO_QUANTITA_BIRRA);
		if(!valid) {
			return false;
		}
		if(Double.doubleToLongBits(convertToNumber(quantitaBirra)) == Double.doubleToLongBits(0.0)) {
			Notifica.getIstanza().addError("Il campo \"" + CAMPO_QUANTITA_BIRRA + "\" deve essere un numero maggiore di zero");	
			return false;
		}
		return true;
	}
	
	//Controlla che la quantita inserita sia valida
	private static boolean validation(String quantitaBirra) {
		return validateQuantitaBirra(quantitaBirra);
	}

}