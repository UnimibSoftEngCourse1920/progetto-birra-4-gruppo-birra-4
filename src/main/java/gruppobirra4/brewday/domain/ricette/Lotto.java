package gruppobirra4.brewday.domain.ricette;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import static gruppobirra4.brewday.domain.InputUtente.*;


public class Lotto implements Serializable {
	
	private String id;
	private String idRicetta;
	private Date data;
	private String noteGusto;
	private String noteProblemi;
	private String quantitaBirra;
	
	public static final String CAMPO_QUANTITA_BIRRA = "Quantita di birra da produrre";
	
	protected Lotto(String id, String idRicetta, Date data, String noteGusto, String noteProblemi, String quantitaBirra) {
		this.id = id;
		this.idRicetta = idRicetta;
		this.data = data;
		this.noteGusto = noteGusto;
		this.noteProblemi = noteProblemi;
		this.quantitaBirra = quantitaBirra;
	}
	
	protected Lotto(String idRicetta, String quantitaBirra) {
		this.id = UUID.randomUUID().toString();
		this.idRicetta = idRicetta;
		this.data = new Date();
		this.noteGusto = "";
		this.noteProblemi = "";
		this.quantitaBirra = quantitaBirra;
	}
	
	public static Lotto creaLotto(String idRicetta, String quantitaBirra) {
		if (!validation(quantitaBirra)) {
			return null;
		}
		return new Lotto(idRicetta, quantitaBirra);		
	}


	private static boolean validation(String quantitaBirra) {
		validateQuantitaBirra(quantitaBirra);
		return false;
	}

	private static boolean validateQuantitaBirra(String quantitaBirra) {
		return !isStringaVuota(quantitaBirra, CAMPO_QUANTITA_BIRRA) &&
				isNumber(quantitaBirra, CAMPO_QUANTITA_BIRRA) && 
				isNotPositive(quantitaBirra, CAMPO_QUANTITA_BIRRA);
	}

	public String getId() {
		return id;
	}
	
	public String getIdRicetta() {
		return idRicetta;
	}

	public Date getData() {
		return data;
	}

	public String getNoteGusto() {
		return noteGusto;
	}
	
	public String getNoteProblemi() {
		return noteProblemi;
	}
	
	
	
	
	
	


}
