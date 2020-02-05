package gruppobirra4.brewday.domain.ricette;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Lotto implements Serializable {
	
	private String id;
	private Date data;
	private String noteProblemi;
	private String noteGusto;
	private String idRicetta;
	
	protected Lotto(String id, Date data, String noteProblemi, String noteGusto, String idRicetta) {
		this.id = id;
		this.data = data;
		this.noteProblemi = noteProblemi;
		this.noteGusto = noteGusto;
		this.idRicetta = idRicetta;
	}
	
	protected Lotto(String idRicetta) {
		this.id = UUID.randomUUID().toString();
		this.data = new Date();
		this.noteProblemi = "";
		this.noteGusto = "";
		this.idRicetta = idRicetta;
	}


	public String getId() {
		return id;
	}

	public Date getData() {
		return data;
	}

	public String getNoteProblemi() {
		return noteProblemi;
	}

	public String getNoteGusto() {
		return noteGusto;
	}
	
	public String getIdRicetta() {
		return idRicetta;
	}
	
	
	
	


}
