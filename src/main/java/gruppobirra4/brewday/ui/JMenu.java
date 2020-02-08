package gruppobirra4.brewday.ui; //NOSONAR

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class JMenu {
	
	private static JMenu istanza;
	private JMenuBar menuBar;
	private JFrame frameVisibile; 
	
	private JButton btnHome;
	private JButton btnCatalogo;
	private JButton btnSpesa;
	private JButton btnRicettario;
	private JButton btnLotti;
	
	private JMenu() {
		this.menuBar = new JMenuBar();
		this.frameVisibile = null;
		inserisciMenu();
	}
	
	public static synchronized JMenu getIstanza() {
		if (istanza == null){
			istanza = new JMenu();	
		}
		return istanza;
	}
	
	protected void setFrameVisible(JFrame frm) {
		frameVisibile = frm;
	}
	
	private void inserisciMenu() {
		menuBar.setBounds(0, 0, 590, 25);
		
		btnHome = new JButton("Home");
		btnHome.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnHome);
		
		btnCatalogo = new JButton("Catalogo ingredienti");
		btnCatalogo.addActionListener(event -> {
			frameVisibile.dispose();
			JCatalogo.esegui();
		});
		btnCatalogo.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnCatalogo);
		
		btnSpesa = new JButton("Lista della spesa");
		btnSpesa.addActionListener(event -> {
			frameVisibile.dispose();
			JListaSpesa.esegui();
		});
		btnSpesa.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnSpesa);
		
		btnRicettario = new JButton("Ricettario");
		btnRicettario.addActionListener(event -> {
			frameVisibile.dispose();
			JRicettario.esegui();
		});
		btnRicettario.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnRicettario);
		
		btnLotti = new JButton("Produzioni precedenti");
		btnLotti.addActionListener(event -> {
			frameVisibile.dispose();
			JListaLotti.esegui();
		});
		btnLotti.setBackground(Color.LIGHT_GRAY);
		menuBar.add(btnLotti);
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/*public JButton getBtnHome() {
		return btnHome;
	}

	public JButton getBtnCatalogo() {
		return btnCatalogo;
	}

	public JButton getBtnSpesa() {
		return btnSpesa;
	}

	public JButton getBtnRicettario() {
		return btnRicettario;
	}

	public JButton getBtnLotti() {
		return btnLotti;
	}*/
	
	
	
}
