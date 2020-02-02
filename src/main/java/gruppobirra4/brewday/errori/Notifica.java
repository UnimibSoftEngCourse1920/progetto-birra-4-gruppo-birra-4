package gruppobirra4.brewday.errori; //NOSONAR

import java.util.LinkedList;
import java.util.List;

public class Notifica {

	private static Notifica istanza;
	private List<Error> errori;
	
	private Notifica() {
		this.errori = new LinkedList<>();
	}
		
	public static synchronized Notifica getIstanza() {
		if (istanza == null){
			istanza = new Notifica();	
		}
		return istanza;
	}
	
	public void addError(String message) {
		errori.add(new Error(message));
	}
	
	/*public void addException(String message, Exception e) {
		exception = new Error(message, e);
	}*/
	
	public boolean hasErrors() {
		return ! errori.isEmpty();
	}
	
	/*public boolean hasException() {
		return exception != null;
	}*/
	
	public void svuotaNotificheErrori() {
		if (!errori.isEmpty()) {
			errori.clear();
		}
	}

	public void notificaErrori() {
		ErrorDialog.getIstanza().notificaErrori(errori);
	}
	
	public void notificaEccezione(Exception e) {
		ErrorDialog.getIstanza().notificaEccezione(new Error(e));
	}
	

}
