package GruppoBirra4.BrewDay.errori;

import java.util.LinkedList;
import java.util.List;

import GruppoBirra4.BrewDay.application.GestoreRicette;

public class Notifica {

	private static class Error {
		String message;
		Exception cause;

		private Error(String message, Exception cause) {
			this.message = message;
			this.cause = cause;
		}

		private Error(String message) {
			this.message = message;
			this.cause = null;
		}
	}
	
	private static Notifica istanza;
	private List<Error> errors;
	private Error exception;
	
	private Notifica() {
		this.errors = new LinkedList<Error>();
	}
		
	public static synchronized Notifica getIstanza() {
		if (istanza == null){
			istanza = new Notifica();	
		}
		return istanza;
	}
	
	public void addError(String message) {
		errors.add(new Error(message));
	}

	/*public void addError(String message, Exception e) {
		errors.add(new Error(message, e));
	}*/
	
	public void addException(String message, Exception e) {
		exception = new Error(message, e);
	}
	
	/*public String errorMessage() {
		return errors.stream()
				.map(e -> e.message)
				.collect(Collectors.joining(", "));
	}*/

	public boolean hasErrors() {
		return ! errors.isEmpty();
	}
	
	public boolean hasException() {
		return exception != null;
	}
	
	public void svuotaNotificheErrori() {
		errors.clear();
	}

	public void notificaErrori() {
		// TODO Auto-generated method stub
	}
	
	public void notificaEccezione() {
		// TODO Auto-generated method stub
	}


}
