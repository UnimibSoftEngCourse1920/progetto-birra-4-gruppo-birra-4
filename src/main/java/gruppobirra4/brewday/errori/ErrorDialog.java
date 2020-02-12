package gruppobirra4.brewday.errori; //NOSONAR

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorDialog {

	private static ErrorDialog istanza;
	
	private ErrorDialog() {
		super();
	}
	
	public static synchronized ErrorDialog getIstanza() {
		if (istanza == null){
			istanza = new ErrorDialog();	
		}
		return istanza;
	}

	//Notifica all'utente un errore
	public void notificaErrori(List<Error> errori, String tipoErrori) {
		String message = toStringError(errori, tipoErrori);
		JOptionPane.showMessageDialog(new JFrame(), message, "Errore", JOptionPane.WARNING_MESSAGE);
	}
	
	//Notifica all'utente un'eccezione
	public void notificaEccezione(Error e) {
		String message = e.getMessage();
		JOptionPane.showMessageDialog(new JFrame(), message, "Exception", JOptionPane.ERROR_MESSAGE);
	}
	
	//Crea il messaggio di errore da restituire all'utente
	private String toStringError(List<Error> errori, String tipoErrori) {
		StringBuilder e = new StringBuilder();
		if (tipoErrori != null) {
			e.append(tipoErrori + ":\n");
		}
		for (Error err: errori) {
			e.append("- " + err.getMessage() + "\n");
		}
		return e.toString();
	}

}