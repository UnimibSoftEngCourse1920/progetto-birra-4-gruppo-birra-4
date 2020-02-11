package gruppobirra4.brewday.errori; //NOSONAR

import java.util.LinkedList;
import java.util.List;

public class Notifica {

	private static Notifica istanza;
	private List<Error> errori;
	private String tipoErrori;
	
	private Notifica() {
		this.errori = new LinkedList<>();
		this.tipoErrori = null;
	}
		
	public static synchronized Notifica getIstanza() {
		if (istanza == null){
			istanza = new Notifica();	
		}
		return istanza;
	}
	
	//Aggiunge un errore alla lista errori
	public void addError(String message) {
		errori.add(new Error(message));
	}
	
	//Controlla se sono presenti degli errori nella lista errori
	public boolean hasErrors() {
		return ! errori.isEmpty();
	}
	
	public String getTipoErrori() {
		return tipoErrori;
	}
	
	public void notificaEccezione(Exception e) {
		ErrorDialog.getIstanza().notificaEccezione(new Error(e));
	}
	
	//Notifica all'utente tutti gli errori presenti nella lista errori
	public void notificaErrori() {
		ErrorDialog.getIstanza().notificaErrori(errori, tipoErrori);
	}
	
	public void setNullTipoErrori() {
		this.tipoErrori = null;		
	}	
	
	public void setTipoErrori(String tipoErrori) {
		this.tipoErrori = tipoErrori;
	}
	
	//Svuota la lista errori
	public void svuotaNotificheErrori() {
		if (!errori.isEmpty()) {
			errori.clear();
		}
	}


}
