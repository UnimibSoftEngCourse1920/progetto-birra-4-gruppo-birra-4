package gruppobirra4.brewday.errori;

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

	public void notificaErrori(List<Error> errori) {
		String message = toStringError(errori);
		JOptionPane.showMessageDialog(new JFrame(), message, "Errore", JOptionPane.WARNING_MESSAGE);
	}
	
	public void notificaEccezione(Error e) {
		String message = e.getMessage();
		JOptionPane.showMessageDialog(new JFrame(), message, "Exception", JOptionPane.ERROR_MESSAGE);
	}
	
	private String toStringError(List<Error> errori) {
		String e = "";
		for (Error err: errori) {
			e = e + "- " + err.getMessage() + "\n";
		}
		return (String) e;
	}

}
