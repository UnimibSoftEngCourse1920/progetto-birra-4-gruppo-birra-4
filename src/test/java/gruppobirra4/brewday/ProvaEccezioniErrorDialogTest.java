package gruppobirra4.brewday;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static org.junit.Assert.*;

import org.junit.Test;

import gruppobirra4.brewday.errori.Notifica;

public class ProvaEccezioniErrorDialogTest {

	@Test
	public void test() {
		try {
			throw new NullPointerException();
		} catch (Exception e) {
			Notifica.getIstanza().notificaEccezione(e);
			return;
		}
	}

}