package gruppobirra4.brewday.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import gruppobirra4.brewday.errori.Notifica;

public class JLotto extends FrameVisibile{
	
	private JFrame frmLotto;
	private String idLotto;
	private String nomeRicetta;
	
	public JLotto(String idRicetta, String nomeRicetta) {
		frmLotto = new JFrame();
		menu = JMenu.getIstanza();
		this.idLotto = idRicetta;
		this.nomeRicetta = nomeRicetta;
		//pannelloIngr = new PannelloIngredienti();
		initialize();
		menu.setFrameVisible(frmLotto);
	}
	
	public static void esegui(String idLotto, String nomeRicetta) {
		/*EventQueue.invokeLater(() -> {
			try {
				JRicetta window = new JRicetta(idRicetta, nomeRicetta);
				window.frmRicetta.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLotto window = new JLotto(idLotto, nomeRicetta);
					window.frmLotto.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}

	@Override
	protected void initialize() {
		
		
	}
}
