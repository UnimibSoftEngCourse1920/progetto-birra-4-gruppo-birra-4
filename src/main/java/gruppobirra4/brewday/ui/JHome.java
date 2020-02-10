package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.EventQueue;

import javax.swing.JFrame;

import gruppobirra4.brewday.errori.Notifica;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;

public class JHome extends FrameVisibile {

	private JFrame frmHome;
	
	public JHome() {
		frmHome = new JFrame();
		menu = JMenu.getIstanza();
		initialize();
		menu.setFrameVisible(frmHome);
	}
	
	public static void main(String[] args) {
		esegui();
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
					JHome window = new JHome();
					window.frmHome.setVisible(true);
				} catch (Exception e) {
					Notifica.getIstanza().svuotaNotificheErrori();
					Notifica.getIstanza().notificaEccezione(e);
				}
			}
		});
	}
	
	@Override
	protected void initialize() {
		frmHome.setTitle("Home - Brew Day!");
		frmHome.setBounds(100, 100, 968, 611);
		frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHome.getContentPane().setLayout(null);

		inserisciIntestazione();
		inserisciBtnCatalogoIngredienti();
		inserisciBtnRicettario();
		inserisciBtnListaSpesa();
		inserisciBtnListaLotti();
		inserisciBtnBirraDelGiorno();
		
	}
	
	private void inserisciIntestazione() {
		JLabel lblBrewday = new JLabel("BREW DAY!");
		lblBrewday.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblBrewday.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrewday.setBounds(15, 51, 916, 32);
		frmHome.getContentPane().add(lblBrewday);
		
		JLabel lblBenvenuto = new JLabel("Benvenuto");
		lblBenvenuto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBenvenuto.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvenuto.setBounds(15, 99, 916, 20);
		frmHome.getContentPane().add(lblBenvenuto);
	}
	
	private void inserisciBtnCatalogoIngredienti() {
		JButton btnCatalogoIngredienti = new JButton("Catalogo ingredienti");
		btnCatalogoIngredienti.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCatalogoIngredienti.setBounds(217, 150, 239, 89);
		btnCatalogoIngredienti.addActionListener(event -> {
			frmHome.dispose();
			JCatalogo.esegui();
		});
		frmHome.getContentPane().add(btnCatalogoIngredienti);
	}
	
	private void inserisciBtnRicettario() {
		JButton btnRicettario = new JButton("Ricettario");
		btnRicettario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnRicettario.setBounds(492, 150, 239, 89);
		btnRicettario.addActionListener(event -> {
			frmHome.dispose();
			JRicettario.esegui();
		});
		frmHome.getContentPane().add(btnRicettario);
	}
	
	private void inserisciBtnListaSpesa() {
		JButton btnListaSpesa = new JButton("Lista della Spesa");
		btnListaSpesa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnListaSpesa.setBounds(217, 250, 239, 89);
		btnListaSpesa.addActionListener(event -> {
			frmHome.dispose();
			JListaSpesa.esegui();
		});
		frmHome.getContentPane().add(btnListaSpesa);
	}
	
	private void inserisciBtnListaLotti() {
		JButton btnListaLotti = new JButton("Lista lotti");
		btnListaLotti.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnListaLotti.setBounds(492, 250, 239, 89);
		btnListaLotti.addActionListener(event -> {
			frmHome.dispose();
			JListaLotti.esegui();
		});
		frmHome.getContentPane().add(btnListaLotti);
	}
	
	private void inserisciBtnBirraDelGiorno() {
		JButton btnBirraDelGiorno = new JButton("Cosa birriamo oggi?");
		btnBirraDelGiorno.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBirraDelGiorno.setBounds(217, 355, 514, 89);
		btnBirraDelGiorno.addActionListener(event -> {
			frmHome.dispose();
			JBirraDelGiorno.esegui();
		});
		frmHome.getContentPane().add(btnBirraDelGiorno);
	}
	
}
	
