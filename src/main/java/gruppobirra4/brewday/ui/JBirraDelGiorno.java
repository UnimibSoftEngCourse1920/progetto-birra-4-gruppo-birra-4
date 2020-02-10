package gruppobirra4.brewday.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import gruppobirra4.brewday.application.gestori.GestoreBirraDelGiorno;
import gruppobirra4.brewday.domain.ricette.Ricetta;
import gruppobirra4.brewday.errori.Notifica;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class JBirraDelGiorno extends FrameVisibile {
	
	private JFrame frmBirraDelGiorno;
	private JTextField textField;

	public JBirraDelGiorno() {
		frmBirraDelGiorno = new JFrame();
		menu = JMenu.getIstanza();
		initialize();
		menu.setFrameVisible(frmBirraDelGiorno);
	}
	
	public static void esegui() {
		/*EventQueue.invokeLater(() -> {
			try {
				JRicettario window = new JRicettario();
				window.frmRicettario.setVisible(true);
			} catch (Exception e) {
				Notifica.getIstanza().svuotaNotificheErrori();
				Notifica.getIstanza().notificaEccezione(e);
			}
		});*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JBirraDelGiorno window = new JBirraDelGiorno();
					window.frmBirraDelGiorno.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}

	@Override
	protected void initialize() {
		frmBirraDelGiorno.setTitle("Cosa birriamo oggi? - Brew Day!");
		frmBirraDelGiorno.setBounds(100, 100, 968, 611);
		frmBirraDelGiorno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBirraDelGiorno.getContentPane().setLayout(null);
		
		inserisciMenu();
		inserisciNome();
		inserisciDescrizione();
		inserisciTextField();
		inserisciBottoneCalcola();
		inserisciMenu();	
	}
	
	private void inserisciMenu() {
		frmBirraDelGiorno.getContentPane().add(menu.getMenuBar());	
	}
	
	private void inserisciNome() {
		JLabel lblLaBirraDel = new JLabel("Cosa birriamo oggi?");
		lblLaBirraDel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLaBirraDel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLaBirraDel.setBounds(15, 80, 916, 71);
		frmBirraDelGiorno.getContentPane().add(lblLaBirraDel);
	}
	
	private void inserisciDescrizione() {
		JLabel lblQuestaFunzioneRestituisce = new JLabel("Questa funzione restituisce la ricetta che massimizza l'utilizzo di ingredienti presenti nel Catalogo Ingredienti");
		lblQuestaFunzioneRestituisce.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestaFunzioneRestituisce.setBounds(15, 156, 916, 20);
		frmBirraDelGiorno.getContentPane().add(lblQuestaFunzioneRestituisce);
	}
	
	private void inserisciTextField() {
		textField = new JTextField();
		textField.setBounds(402, 228, 146, 57);
		frmBirraDelGiorno.getContentPane().add(textField);
		textField.setColumns(10);
		JLabel lblQuantitDiBirra = new JLabel("Quantità di birra da produrre (litri)");
		lblQuantitDiBirra.setBounds(149, 246, 296, 20);
		frmBirraDelGiorno.getContentPane().add(lblQuantitDiBirra);
	}
	
	private void inserisciBottoneCalcola() {
		JButton btnCalcola = new JButton("Calcola");
		btnCalcola.setBounds(386, 316, 179, 78);
		btnCalcola.addActionListener(event -> {
			String quantitaBirra = textField.getText();
			Ricetta risultato = GestoreBirraDelGiorno.getIstanza().calcolaBirraDelGiorno(quantitaBirra);
			frmBirraDelGiorno.dispose();
			JRicetta.esegui(risultato.getId(), risultato.getNome());
		});
		frmBirraDelGiorno.getContentPane().add(btnCalcola);
	}
	
}
